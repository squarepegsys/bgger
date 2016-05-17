package bgger

import grails.plugin.springsecurity.annotation.Secured

/**
 * Created by req86346 on 11/09/2015.
 */
class GeekListController {


    @Secured(['ROLE_ADMIN'])
    def index(GeekList geekList) {

        render view: "show", model: [geekList: geekList]

    }
}
