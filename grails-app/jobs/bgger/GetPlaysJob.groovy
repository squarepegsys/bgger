package bgger

class GetPlaysJob {

    FetchPlayService fetchPlayService

    def execute() {
        fetchPlayService.getUsersPlays('squarepegsys')
    }
}
