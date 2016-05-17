package bgger

import grails.plugin.springsecurity.annotation.Secured

/**
 * Created by mikeh on 2/9/16.
 */
@Secured(['ROLE_ADMIN'])
class PlayController {

    def index() {

    }

    def game(BggGame game) {

        def plays = Play.findAllByGame(game, ["max": 50])

        render view: "game", model: [game: game, plays: plays]
    }
}
