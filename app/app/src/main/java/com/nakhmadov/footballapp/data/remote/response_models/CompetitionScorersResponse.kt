package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Player
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompetitionScorersResponse(
    val count: Int = 0,
    val competition: CompetitionDetailResponse = CompetitionDetailResponse(),
    val scorers: List<ScorerResponse> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class ScorerResponse(
    val player: PlayerResponse = PlayerResponse(),
    val team: TeamShortResponse = TeamShortResponse(),
    val numberOfGoals: Int? = 0
)

@JsonClass(generateAdapter = true)
data class PlayerResponse(
    val id: Int = 0,
    val name: String = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val dateOfBirth: String? = "",
    val countryOfBirth: String? = "",
    val nationality: String? = "",
    val position: String? = "",
    val shirtNumber: Int? = 0
)

fun CompetitionScorersResponse.asDatabaseModel(): List<Player> {
    val resultList = mutableListOf<Player>()

    this.scorers.forEach {
        val player = Player(
            playerId = it.player.id,
            teamId = it.team.id,
            name = it.player.name,
            firstName = it.player.firstName,
            lastName = it.player.lastName,
            dateOfBirth = it.player.dateOfBirth,
            countryOfBirth = it.player.countryOfBirth,
            nationality = it.player.nationality,
            position = it.player.position,
            shirtNumber = it.player.shirtNumber,
            numberOfGoals = it.numberOfGoals,
            competitionId = this.competition.id
        )
        resultList.add(player)
    }

    return resultList
}