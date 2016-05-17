import bgger.BggFetcherService
import bgger.BggGame
import bgger.GeekList
import bgger.GeekListItem
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore
import spock.lang.Specification

import java.text.SimpleDateFormat
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Integration
@Rollback
class BggFetcherServiceSpec extends Specification {

    @Autowired
    BggFetcherService bggService

    static String xmlDateFormat = 'EEE, d MMM yyyy HH:mm:ss Z'

    static def formatter = new SimpleDateFormat(xmlDateFormat)

    def setup() {

        bggService = new BggFetcherService()
    }

    def cleanup() {
    }

    void "generate correct url"() {

        when:

        String glUrl = bggService.findUrl("186592")

        then:

        glUrl=="http://boardgamegeek.com/xmlapi/geeklist/186592?comments=1"


    }

    @Ignore
    void "test xml parsing"() {

        given:
           String sampleXml = new File("samples/sgfot.xml").text

        when:
            GeekList geekList = bggService.parseXml(sampleXml)

        then:

        geekList.bggId==186592
        geekList.postDate== formatter.parse("Sun, 01 Feb 2015 04:21:15 +0000")
        geekList.editDate==formatter.parse("Sat, 07 Feb 2015 21:49:44 +0000")
        geekList.name == 'Solitaire Games On Your Table - February 2015'
        geekList.username=='Woelf'

        def items = geekList.items

        items != null
        items.size()==398

        items[0].bggId == 3710246
        items[0].postDate == formatter.parse("Sat, 31 Jan 2015 22:56:06 +0000")
        items[0].editDate == formatter.parse("Sun, 01 Feb 2015 14:58:33 +0000")
        items[0].bggGame.bggId == 108667
        items[0].bggGame.name=="Power Grid: The Robots"
        items[0].username == "Woelf"


        items[0].geeklist==geekList


        items[0].geeklist.comments.size()>0





    }


    void "update geeklist if exists"() {

        given:
        String sampleXml = new File("samples/sgfot.xml").text
        GeekList geekList =  new GeekList()
        geekList.bggId=186592
        geekList.postDate= new Date()
        geekList.editDate= new Date()
        geekList.name = 'Solitaire Games On Your Table - February 2015'
        geekList.username='Woelf'

        geekList.save()


        when:
        bggService.parseXml(sampleXml)

        then:

        def glists =GeekList.findAllByBggId(186592)

        glists.size()==1

        def glist = glists[0]
        glist.editDate== formatter.parse("Sat, 07 Feb 2015 21:49:44 +0000")//new Date(1423345784)




    }

    void "Here is a problem child"() {


        given:
        String sampleXml = new File("samples/bad-list.xml").text

        when:
        bggService.parseXml(sampleXml)

        then:

        def glists =GeekList.findAllByBggId(198923)

        glists.size()==1

        def glist = glists[0]
        glist.editDate== formatter.parse("Sat, 31 Oct 2015 16:27:03 +0000")//new Date(1423345784)


    }

    @Ignore
    void "My own zulu on the ramparts"() {

        when:
        bggService.findGeekList("202271")

        then:
        def bggGame = BggGame.findByName("Zulus on the Ramparts!")

        bggGame!=null
        def glItems = GeekListItem.findAllByBggGameAndUsername(bggGame,"squarepegsys")

        glItems.size()==1

        glItems[0].bggId==4412501

    }

    /*   void "only grab geeklist made in the last 30 days"() {

           given:
           String sampleXml = new File("samples/sgfot.xml").text

           when:
           GeekList geekList = bggService.findGeekList("194940")

           then:

           assert !geekList
       }
   */

}
