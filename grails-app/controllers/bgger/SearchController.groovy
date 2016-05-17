package bgger

import grails.plugin.springsecurity.annotation.Secured

/**
 * Created by mikeh on 11/2/15.
 */


class SearchController {

    def searchService

    @Secured(['ROLE_ADMIN'])
    def search() {

        def result = searchService.searchByName(params.searchTerm)

        render view: "result", model: [results: result]


    }
}
