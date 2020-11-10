package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.CompetitionTableItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompetitionTableResponse(
    val competition: CompetitionDetailResponse = CompetitionDetailResponse(),
    val standings: List<StandingDetailResponse> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class StandingDetailResponse(
    val stage: String = "",
    val type: String = "",
    val table: List<TableDetailResponse> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class TableDetailResponse(
    val position: Int = 0,
    val team: TeamShortResponse = TeamShortResponse(),
    val playedGames: Int = 0,
    val won: Int = 0,
    val draw: Int = 0,
    val lost: Int = 0,
    val points: Int = 0,
    val goalsFor: Int = 0,
    val goalsAgainst: Int = 0,
    val goalDifference: Int = 0
)

fun CompetitionTableResponse.asDatabaseModel(): List<CompetitionTableItem> {
    val resultList = mutableListOf<CompetitionTableItem>()

    this.standings[0].table.forEach {
        val table = CompetitionTableItem(
            competitionId = this.competition.id,
            position = it.position,
            teamId = it.team.id,
            playedGames = it.playedGames,
            won = it.won,
            draw = it.draw,
            lost = it.lost,
            points = it.points,
            goalsFor = it.goalsFor,
            goalsAgainst = it.goalsAgainst,
            goalDifference = it.goalDifference
        )
        resultList.add(table)
    }
    return resultList
}