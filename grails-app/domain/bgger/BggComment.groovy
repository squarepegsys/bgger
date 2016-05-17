package bgger
class BggComment {

    String username
    Date postDate
    Date editDate

    static constraints = {

        postDate nullable: false
        editDate nullable: false
    }
}
