package bgger

class GeekListItem {


    Integer bggId
    String username
    Date postDate
    Date editDate
    Date dateCreated
    Date lastUpdated



    static belongsTo=[geeklist: GeekList]

    static hasMany = [comments: GeekListItemComment]
    static hasOne = [bggGame: BggGame]

    static mapping = {
        cache: 'nonstrict-read-write'
    }

    static constraints = {

        bggId unique: true, nullable: false

        postDate nullable: false
        editDate nullable: false
    }

    String getUrl() {


        if (geeklist && geeklist.bggId && bggId) {
            return "http://www.boardgamegeek.com/geeklist/${geeklist.bggId}/item/${bggId}#item${bggId}"
        }

        ""
    }

    Date getCommentDate() {
        def commentDates = comments.collect {
            it.postDate
        }

        if (!commentDates) {
            return postDate
        }



        return commentDates.sort()[-1]

    }
}
