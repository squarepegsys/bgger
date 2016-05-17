package bgger

import bgger.security.User

/**
 * Created by mikeh on 11/29/15.
 */
class Play {

    Integer bggId
    User user
    BggGame game
    Integer quantity
    String comments
    Date playedOnDate
    Date dateCreated

    static constraints = {
        comments nullable: true

    }
    static mapping = {
        comments type: 'text'
        sort playedOnDate: "desc"
    }
}
