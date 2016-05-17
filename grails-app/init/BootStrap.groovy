import bgger.GetGeekListsJob
import bgger.GetPlaysJob
import bgger.security.Role
import bgger.security.User
import bgger.security.UserRole
import org.h2.tools.Server

class BootStrap {

    def fetchPlayService

    def init = { servletContext ->

        User mike = User.findByUsername("squarepegsys")
        if (!mike) {

            mike = new User("squarepegsys", "fred")

            mike.save(failOnError:true)

        }

        Role admin = Role.findByAuthority("ROLE_ADMIN")

        if (!admin) {

            admin = new Role("ROLE_ADMIN");
            admin.save(failOnError: true)

            UserRole.create mike, admin, true


        }


        environments {
            development{
                Server.createTcpServer().start()

                // 5-minutes
                GetGeekListsJob.schedule(15*60*1000, -1)

                // 3 minutes
                GetPlaysJob.schedule(17*60*1000, -1)
            }
            test {
                // nothing
            }
            production {
                // hourly
                GetGeekListsJob.schedule(60*60*1000, -1)

                // at midnight
                GetPlaysJob.schedule('0 0 0 * * ?')
            }

        }
    }
    def destroy = {
    }
}
