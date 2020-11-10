package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Match
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchesResponse(
    val count: Int = 0,
    val matches: List<MatchResponse> = mutableListOf()
)

fun MatchesResponse.asDatabaseModel(): List<Match> {
    val resultList = mutableListOf<Match>()

    this.matches.forEach {
        val match = Match(
            matchId = it.id,
            competitionId = it.competition?.id,
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