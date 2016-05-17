package bgger

import grails.plugin.springsecurity.annotation.Secured
import groovy.time.TimeCategory


class MainController {

    def springSecurityService

    @Secured(['ROLE_ADMIN'])
    def index() {
        def currentUser = springSecurityService.currentUser

        if (!currentUser) {
            redirect url: '/login'
            return
        }

        Date weekAgo = new Date()

        use(TimeCategory) {
            weekAgo = weekAgo - 2.week
        }

        log.debug("Getting updates from ${weekAgo.toLocaleString()}")

        def items = GeekListItem.findAllByEditDateGreaterThan(weekAgo, )



        render view: "index", model: [items: items, currentUsername: currentUser.username]
    }
}
