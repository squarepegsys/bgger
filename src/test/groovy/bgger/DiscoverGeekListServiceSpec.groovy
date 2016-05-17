package bgger

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DiscoverGeekListService)
class DiscoverGeekListServiceSpec extends Specification {

    DiscoverGeekListService discoverGeekList

    def setup() {


    }

    def cleanup() {
    }

    void "find bggId"() {

        given:
        discoverGeekList = new DiscoverGeekListService()
        expect: "get IDs"

        def bggIds = discoverGeekList.findBggIds(new File("samples/geeklist.rss").text)

        assert "196401" in bggIds

        assert "194940" in bggIds
        assert !("42" in bggIds)

    }
}
