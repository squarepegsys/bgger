package bgger


class DiscoverGeekListService {


    def BGGID_REG = 'geeklist.([0-9]+)'


    def parseAndUpdateLists(String url) {

        String xmlText = new URL(url).text

        findBggIds(xmlText).each {
            bggId ->
                log.info("processing ID ${bggId}")
                notify("geeklist.find", bggId)

        }
    }
    Set findBggIds(String rssString) {
        XmlSlurper xmlSlurper = new XmlSlurper()
        def rss = xmlSlurper.parseText(rssString)

        def bggIds = rss.channel.item.collect { item ->

            log.debug("found URL: ${item.guid}")

            def itemInfo = item.guid.text().find(BGGID_REG)

            if (itemInfo) {
                def lists = "${itemInfo}".split("/")

                if (lists.length > 1) {
                    return lists[1]
                }
            }

            return null
        }.findAll {
            if (it) {
                return it
            }

        } as Set


        return bggIds
    }



}
