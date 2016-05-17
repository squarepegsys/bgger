package bgger

import grails.transaction.Transactional

@Transactional
class SearchService {

    def searchByName(def gameName) {

        def games = BggGame.findAllByNameIlike("%${gameName}%")
        def retval = []
        games.each { game ->

            def total = GeekListItem.countByBggGame(game)

            retval.add([game: game, total: total])
        }

        retval.sort { a, b ->
            b.total <=> a.total
        }*.game

        return retval
    }
}
