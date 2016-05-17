package bgger

import grails.plugin.springsecurity.annotation.Secured

/**
 * Created by req86346 on 09/17/2015.
 */
class UserItemController {

    def springSecurityService

    def playService

    @Secured(['ROLE_ADMIN'])
    def index() {


        def currentUser = springSecurityService.currentUser

        if (!currentUser) {
            redirect url: '/login'
            return
        }

        def username = currentUser.username

        def myItems = GeekListItem.findAllByUsername(username,[sort: "editDate", order: "desc", max: 100])

        def myComments = GeekListItemComment.findByUsername(username,[sort: "editDate", order: "desc", max: 100])

        myItems.addAll(
                myComments.collect { it.geekListItem}
        )

        def hindex = playService.findHindex(currentUser)

        //def yearlyStats = playService.yearlyStats(currentUser)

        //log.info("Stats: ${yearlyStats}")
        myItems.sort{ a,b -> a.commentDate<=>b.commentDate}

        render view: "index", model: [items: myItems, hindex: hindex]

    }


}
