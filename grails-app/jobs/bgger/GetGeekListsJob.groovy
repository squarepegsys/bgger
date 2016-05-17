package bgger
/**
 * Created by mikeh on 9/15/15.
 */
class GetGeekListsJob {
    static triggers = {
        cron name: 'GetGeekListTrigger',
                cronExpression: "0 0 * * * ?"
    }

    def discoverGeekListService

    def execute() {

        discoverGeekListService.parseAndUpdateLists("https://boardgamegeek.com/rss/subscriptions/userid/361910/4355a8f194ff5d733cc6a35d7ed5aad5/group/geeklist")


    }
}
