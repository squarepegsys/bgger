import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import grails.util.Environment
import org.springframework.boot.ApplicationPid

import java.nio.charset.Charset

def PATTERN = "%d %level %logger - %msg%n"
//def targetDir = System.getProperty('APP_LOG_PATH',BuildSettings.TARGET_DIR.canonicalPath)
def targetDir = System.getProperty('APP_LOG_PATH',".")

// Get PID for Grails application.
// We use it in the logging output.
if (!System.getProperty("PID")) {
    System.setProperty("PID", (new ApplicationPid()).toString())
}


// Mimic Spring Boot logging configuration.
conversionRule 'clr', org.springframework.boot.logging.logback.ColorConverter
conversionRule 'wex', org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {


        charset = Charset.forName('UTF-8')

// Define pattern with clr converter to get colors.
        pattern = '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                '%clr(%5p) ' + // Log level
                '%clr(%-40.40logger{39}){cyan} %clr(:){faint} ' + // Logger
                '%m%n%wex' // Message
    }

}

if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = PATTERN
        }
    }

    root(ERROR, ['STDOUT'])
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)

    logger("grails.app.services.bgger", DEBUG, ['STDOUT'], false)
    logger("grails.app.controllers.bgger", DEBUG, ['STDOUT'], false)
    logger("grails.app.jobs.bgger", DEBUG, ['STDOUT'], false)

}  else if (targetDir ) {


    appender('applog', FileAppender) {
        file = "${targetDir}/bgger.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = PATTERN
        }

    }

    root(ERROR, ['applog'])
    logger("bgger", INFO, ['applog'], false)

}
