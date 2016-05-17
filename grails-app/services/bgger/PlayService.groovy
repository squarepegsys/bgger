package bgger

import bgger.security.User
import groovy.sql.Sql
import groovy.time.TimeCategory

import javax.sql.DataSource
import java.text.SimpleDateFormat

/**
 * Created by mikeh on 12/31/15.
 */
class PlayService {

    SimpleDateFormat dateFormat = new SimpleDateFormat('YYYY-MM-dd HH:mm')
    DataSource dataSource


    String findHindex(User user) {

        def allPlays = Play.findAllByUser(user)

        def gamePlays = [:].withDefault { key ->
            return 0
        }

        allPlays.each { play ->
            gamePlays[play.game] += play.quantity

        }

        Map sorted = gamePlays.sort { a, b -> b.value <=> a.value }

        def playedGames = sorted.keySet()

        def hindexGame = null

        for (int idx = 0; idx < playedGames.size(); idx++) {
            int listPos = idx + 1
            def game = playedGames[idx]

            log.debug("${game.name} has ${gamePlays[game]} plays")

            if (gamePlays[game] && gamePlays[game] <= listPos) {
                hindexGame = game
                break
            }

        }


        return playedGames.findIndexOf { it == hindexGame } + 1

    }
    Map yearlyStats(User user) {

        def  now = Calendar.instance

        log.debug("Now is ${now.time}")
        log.debug("Current Year: ${now.get(Calendar.YEAR)}")
        Date begYear= dateFormat.parse("${now.get(Calendar.YEAR)}-1-1 00:00")

        def sql = Sql.newInstance(dataSource)

        def totalPlays = sql.firstRow("select sum(quantity) from play where user_id=? and " +
                "played_on_date between ? and  ? ", [user.id, now.time, begYear])

        log.debug("Total plays this year are: ${totalPlays}")

        def daysOfYear=0

        use(TimeCategory) {
            def duration = now.time- begYear
            daysOfYear=duration.days
        }

        log.debug("Days so far this year: ${daysOfYear}")

        return ["totalPlays": totalPlays] //, "playsPerDay": totalPlays/daysOfYear]
    }
}
