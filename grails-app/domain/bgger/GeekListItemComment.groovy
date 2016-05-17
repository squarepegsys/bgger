package bgger

import groovy.transform.ToString

@ToString(includeSuper = true)
class GeekListItemComment extends BggComment{

    static belongsTo = [geekListItem: GeekListItem]

    static constraints = {
    }
}
