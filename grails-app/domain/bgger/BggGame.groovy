package bgger

class BggGame {

    Integer bggId
    String name
    Date dateCreated
    Date lastUpdated

    static mapping = {
        cache: 'read-only'
    }

    static hasMany = [geekListItem: GeekListItem]


    static constraints = {

        bggId unique: true, nullable: false
        name nullable: false
    }

    String getUrl() {
        return "https://boardgamegeek.com/boardgame/${bggId}"
    }


}
