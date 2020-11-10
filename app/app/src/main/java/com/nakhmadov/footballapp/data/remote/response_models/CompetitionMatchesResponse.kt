package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Match
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompetitionMatchesResponse(
    val count: Int = 0,
    val competition: CompetitionDetailResponse = CompetitionDetailResponse(),
    val matches: List<MatchResponse> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class MatchResponse(
    val id: Int = 0,
    val season: CurrentSeasonDetailResponse? = CurrentSeasonDetailResponse(),
    val utcDate: String = "",
    val status: String = "",
    val matchday: Int? = 0,
    val score: ScoreResponse = ScoreResponse(),
    val homeTeam: TeamShortResponse = TeamShortResponse(),
    val awayTeam: TeamShortResponse = TeamShortResponse(),
    val competition: CompetitionDetailResponse? = CompetitionDetailResponse()
)

@JsonClass(generateAdapter = true)
data class ScoreResponse(
    val winner: String? = "",
    val fullTime: TimeResultResponse? = TimeResultResponse(),
    val halfTime: TimeResultResponse? = TimeResultResponse()
)

@JsonClass(generateAdapter = true)
data class TimeResultResponse(
    val homeTeam: Int? = 0,
    val awayTeam: Int? = 0
)

data class TeamShortResponse(val id: Int = 0, val name: String? = "")


fun CompetitionMatchesResponse.asDatabaseModel(): List<Match> {
    val resultList = mutableListOf<Match>()

    this.matches.forEach {
        val match = Match(
            matchId = it.id,
            competitionId = this.competition.id,
            date = it.utcDate,
            status = it.status,
            matchday = it.matchday,
            homeTeamScore = it.score.fullTime?.homeTeam,
            awayTeamScore = it.score.fullTime?.awayTeam,
            winner = it.score.winner,
            homeTeamId = it.homeTeam.id,
            awayTeamId = it.awayTeam.id
        )
        resultList.add(match)
    }

    return resultList
}

