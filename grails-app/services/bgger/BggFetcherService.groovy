package bgger

import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector

import java.text.ParseException
import java.text.SimpleDateFormat

@Consumer
class BggFetcherService {



    static String xmlDateFormat = 'EEE, d MMM yyyy HH:mm:ss Z'
    static def formatter = new SimpleDateFormat(xmlDateFormat)


    @Selector("geeklist.find")
    GeekList findGeekList(String bggId) {


        log.debug("Getting URL: ${findUrl(bggId)}")
        String xmlText = new URL(findUrl(bggId)).text

        try {
            return parseXml(xmlText, bggId)
        }catch (Exception e) {
            log.error("Error parsing GL ${bggId}. Error ${e.class.name} with ${e.message}")

        }

        return null


    }
    String findUrl(String glBggId) {

        "http://boardgamegeek.com/xmlapi/geeklist/${glBggId}?comments=1"
    }
    

    GeekList parseXml(def unknownList, String bggIdStr = "", attempt = 0) {
        def xmlGeekList =unknownList


        XmlSlurper xmlSlurper = new XmlSlurper()

        if (unknownList instanceof String) {
            xmlGeekList = xmlSlurper.parseText(unknownList)
        }


        Integer bggId
        if (bggIdStr) {
            bggId = bggIdStr as Integer
        }else {

            try {
                bggId = xmlGeekList.@id.text() as Integer
            }catch (NumberFormatException e) {
                if (attempt < 10) {
                    log.warn "Couldn't parse Geeklist ID from XML. Probably queued -- will try again"
                    sleep(1000)
                    return parseXml(unknownList, bggIdSt, attempt++)
                } else {
                    log.warn "Couldn't parse Geeklist ID from XML after ${attempt} attempts. Giving up..."
                    return null
                }


            }

        }


        def geekList = GeekList.findOrCreateByBggId(bggId)



        def username = xmlGeekList.username[0].text()
        if (!username) {
            log.warn("The GL ${bggId} is probably queued -- waiting a bit and trying again")
            sleep(10000)
            return findGeekList(bggIdStr)

        }

        geekList.username= username




        def name = xmlGeekList.title[0].text()
        if (name) {
            geekList.name= name
        }


        log.debug("processing geeklist with bggId: ${geekList.bggId} with name '${geekList.name}'")

        String timestamp = xmlGeekList.postdate[0].text()

        try {

            geekList.postDate = formatter.parse(timestamp)

            timestamp = xmlGeekList.editdate[0].text()

            geekList.editDate = formatter.parse(timestamp)
        } catch (ParseException p) {
            log.warn "Can't parse a date in ${bggId} -- setting it to 'now'"

        }

        geekList = saveGeekList(geekList)

        addGLComments(xmlGeekList.comment, geekList)

        log.debug("added GL Comments")

        addItems(xmlGeekList.item, geekList)

        log.debug("added GL Items")


        return saveGeekList(geekList)


    }


    def GeekList saveGeekList(GeekList geekList) {
        if (!geekList.save(failOnError: true)){
            geekList.errors.each {
                log.error(it)
            }
        }
        log.debug("saved geeklist ${geekList.bggId} with db id ${geekList.id}")

        return geekList
    }

    def addGLComments(def xmlComments, GeekList geekList) {

        xmlComments.collect {
            xmlComment->

                def postDate = formatter.parse(xmlComment.@postdate.text())

                GeekListComment comment = GeekListComment.findByPostDate(postDate)

                if(!comment){
                    comment = new GeekListComment()
                }


                parseComment(comment,xmlComment)

                geekList.addToComments(comment)
                comment.save(failOnError: true)

        }

    }

    def addItems(def xmlGlItems, GeekList geekList) {


        xmlGlItems.collect {
            xmlItem->

                def bggId = xmlItem.@id.text() as Integer
                def gameId = xmlItem.@objectid.text() as Integer

                log.debug "got game id ${gameId}"

                BggGame game = BggGame.findOrCreateByBggId(gameId)

                if (!game.id) {
                    game.name=xmlItem.@objectname.text()
                    game.save(failOnError:true )
                }

                GeekListItem item = GeekListItem.findOrCreateByBggIdAndBggGame(bggId,game)
                log.debug "got item with id ${bggId}"

                item.username=xmlItem.@username.text()

                item.postDate=formatter.parse(xmlItem.@postdate.text())
                item.editDate=formatter.parse(xmlItem.@editdate.text())


                addGLItemComments(xmlItem.comment,item).each {

                    item.addToComments(it)
                }


                if (!item.geeklist) {
                    geekList.addToItems(item)
                }

                return item


        }
    }

    def addGLItemComments(def xmlComments,GeekListItem geekListItem) {

        xmlComments.collect {

            xmlComment ->

                log.debug ("## trying to get comments for ${geekListItem.bggId}")
                def postDate = formatter.parse(xmlComment.@postdate.text())

                GeekListItemComment comment = GeekListItemComment.findByPostDate(postDate)

                if (!comment || comment.geekListItem!=geekListItem) {
                    comment = new GeekListItemComment()
                }

                parseComment(comment,xmlComment)
                comment.geekListItem=geekListItem
                //comment.save(failOnError: true)

                log.debug ("## seems that we saved comment ${comment?.id} attached to ${geekListItem.bggId}")

                if (!comment.geekListItem) {
                    geekListItem.addToComments(comment)
                }

                return comment


        }

    }

    def parseComment(BggComment bggComment, def xmlComment) {

        bggComment.username=xmlComment.@username
        bggComment.postDate=formatter.parse(xmlComment.@postdate.text())
        bggComment.editDate=formatter.parse(xmlComment.@editdate.text())

        return bggComment
    }


}
