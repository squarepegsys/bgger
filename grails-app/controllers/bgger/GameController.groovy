package bgger

import grails.plugin.springsecurity.annotation.Secured

/**
 * Created by req86346 on 11/03/2015.
 */
class GameController {

    @Secured(['ROLE_ADMIN'])
    def index(BggGame game) {

        def geekListItems = GeekListItem.findAllByBggGame(game)

        def playCount = Play.countByGame(game)

        render view: 'index', model: [game: game, geekListItems: geekListItems, playCount: playCount]
    }
}
