package bgger

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SearchService)
@Mock([BggGame,GeekListItem])
class SearchServiceSpec extends Specification {



    def cleanup() {
    }

    @Unroll
    void "find #gameName with GL total #total"() {


        setup:

        def game = new BggGame(name: gameName)

        BggGame.metaClass.static.findAllByNameIlike = {
            name ->
                if (name.contains(searchString)) {
                    return [game] as List
                } else {
                    return null
                }
        }

        GeekListItem.metaClass.static.countByBggGame = { return total }

        when:

        List result = service.searchByName(searchString)

        then:

        if (total > 0) {
            result.size() == 1
        } else {
            result.size() == 0
        }

        if (total == 0) {
            !result.isEmpty()
        } else {
            result[0].game == game
            result[0].total == total

        }


        where:

        gameName    | searchString | total
        "Meet Fred" | "Fred"       | 5
        "Barney"    | "Fred"       | 0


    }
}

class Faker{

    int value

    Faker(int value) {
        this.value=value
    }
    long count() { return value}
}