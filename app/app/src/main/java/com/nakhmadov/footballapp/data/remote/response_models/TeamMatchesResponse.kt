package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Match
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamMatchesResponse(
    val count: Int = 0,
    val matches: List<MatchResponse> = mutableListOf()
)

fun TeamMatchesResponse.asDatabaseModel(): List<Match> {
    val resultList = mutableListOf<Match>()

    this.matches.forEach {
        val match = Match(
            matchId = it.id,
            competitionId = it.competition?.id,
            date = it.utcDate,
            matchday = it.matchday,
            winner = it.score.winner,
            homeTeamScore = it.score.fullTime?.homeTeam,
            awayTeamScore = it.score.fullTime?.awayTeam,
            homeTeamId = it.homeTeam.id,
            awayTeamId = it.awayTeam.id,
            status = it.status
        )

        resultList.add(match)
    }

    return resultList
}