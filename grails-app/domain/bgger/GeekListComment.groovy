package bgger

class GeekListComment extends BggComment{

    static belongsTo = [geeklist: GeekList]

}
