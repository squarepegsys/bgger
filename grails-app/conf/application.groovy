
spring.boot.admin.url='/'

app.logfolder = 'logs'
// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'bgger.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'bgger.security.UserRole'
grails.plugin.springsecurity.authority.className = 'bgger.security.Role'
grails.plugin.springsecurity.logout.postOnly = false

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/dbconsole/*'  :    ['permitAll'],
	'/':               ['permitAll'],
    '/metrics'      :   ["permitAll"],
    '/info'         :   ['permitAll'],
    '/health'       :   ['permitAll'],
	'/error':           ['permitAll'],
	'/index':           ['permitAll'],
	'/index.gsp':       ['permitAll'],
	'/shutdown':        ['permitAll'],
	'/assets/**':       ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/**/favicon.ico'      : ['permitAll'],
	'/actuator/dashboard/*': ['permitAll'],
	'/actuator/*'          : ['permitAll']
]


