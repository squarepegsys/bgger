package bgger

import bgger.security.User

import java.text.SimpleDateFormat

/**
 * Created by mikeh on 11/29/15.
 */
class FetchPlayService {

    XmlSlurper xmlSlurper = new XmlSlurper()
    SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd')

    def getUsersPlays(String username, int page=1) {

        log.debug("Loading ${username} and page ${page}")
        def user = User.findByUsername(username)

        if (!user){
            return;
        }


        def url = getUrl(username, page)

        String xmlText = new URL(url).text

        def plays = xmlSlurper.parseText(xmlText)

        def total = plays.@total.text() as Integer

        log.info("total is ${total}")
        if (Play.count()>=total) {
            return
        }

        if (!plays.play) {
            return
        }


        if (page>9) {
            return
        }

        def allNewPlays=true

        plays.play.each { singlePlay->



            def bggId = singlePlay.@id.text() as Integer

            log.trace("got play ${bggId}")

            if (Play.findAllByBggId(bggId)) {
                allNewPlays = true
            }else {

                def gameBggId = singlePlay.item.@objectid

                def bggGame = BggGame.findByBggId(gameBggId)

                if (!bggGame) {
                    bggGame = new BggGame(bggId: gameBggId, name: singlePlay.item.@name.text())
                    bggGame.save(failOnError: true)
                }

                def comments=""

                if (singlePlay.comments) {
                    comments=singlePlay.comments.text()
                }

                def newPlay = new Play(bggId: bggId,
                        game: bggGame,
                        quantity: singlePlay.@quantity.text(),
                        playedOnDate: new Date().parse('yyyy-MM-dd', singlePlay.@date.text()),
                        user: user,
                        comments: comments)

                newPlay.save(failOnError: true)
            }
        }

        if (allNewPlays && plays.play) {
            log.info("on page ${page} and loading the next")
            getUsersPlays(username,page+1)
        }

    }

    def getUrl(String username, Integer pageNum) {

        if (!pageNum) {
            pageNum = 1
        }
        "https://boardgamegeek.com/xmlapi2/plays?username=${username}&page=${pageNum}"
    }
}
