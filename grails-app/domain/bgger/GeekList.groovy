package bgger

class GeekList {


    Integer bggId
    String name
    String username
    Date postDate
    Date editDate
    Date dateCreated
    Date lastUpdated


    static mapping = {
        cache: 'nonstrict-read-write'
    }

    static hasMany = [items: GeekListItem, comments: GeekListComment]

    static constraints = {
        bggId unique: true, nullable: false
        postDate nullable: false
        editDate nullable: false

    }

    String getUrl() {

        if (bggId) {
            return "http://www.boardgamegeek.com/geeklist/$bggId"
        }else {
            return ""
        }
    }


}
