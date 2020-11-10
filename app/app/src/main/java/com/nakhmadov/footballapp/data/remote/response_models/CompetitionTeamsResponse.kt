package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Team
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompetitionTeamsResponse(
    val count: Int = 0,
    val competition: CompetitionDetailResponse = CompetitionDetailResponse(),
    val teams: List<TeamResponse> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class TeamResponse(
    val id: Int = 0,
    val area: AreaDetailResponse = AreaDetailResponse(),
    val name: String = "",
    val shortName: String = "",
    val tla: String = "",
    val crestUrl: String? = "",
    val website: String? = "",
    val founded: Int? = 0,
    val clubColors: String? = "",
    val venue: String? = ""
)

fun CompetitionTeamsResponse.asDatabaseModel(): List<Team> {
    val resultList = mutableListOf<Team>()

    this.teams.forEach {
        val team = Team(
            teamId = it.id,
            competitionId = this.competition.id,
            name = it.name,
            shortName = it.shortName,
            code = it.tla,
            emblemUrl = it.crestUrl,
            website = it.website,
            founded = it.founded,
            clubColors = it.clubColors,
            stadium = it.venue
        )
        resultList.add(team)
    }

    return resultList
}