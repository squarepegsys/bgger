import bgger.FetchPlayService
import bgger.Play
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
/**
 * Created by mikeh on 11/29/15.
 */
@Integration
@Rollback
class FetchPlayServiceSpec extends Specification {

    @Autowired
    FetchPlayService fetchPlayService

    def "GetUrl"() {

        given:
        def username = "squarepegsys"

        expect:

        def url = fetchPlayService.getUrl(username, page)

        assert myUrl == url

        where:
        page | myUrl
        1    | "https://boardgamegeek.com/xmlapi2/plays?username=squarepegsys&page=1"
        null | "https://boardgamegeek.com/xmlapi2/plays?username=squarepegsys&page=1"
        2    | "https://boardgamegeek.com/xmlapi2/plays?username=squarepegsys&page=2"
    }

    def "Get Plays" () {
        given:

        fetchPlayService.getUsersPlays("squarepegsys")


        expect:

        Play.count()>200

        Play.findAllByCommentsLike("%and%").size()>0


        def plays = Play.findAll()


        plays[0].playedOnDate > new Date().parse('yyyy-MM-dd', '2016-01-01')


    }
}
