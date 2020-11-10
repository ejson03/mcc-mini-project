package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Player
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamSquadResponse(
    val id: Int = 0,
    val squad: List<PlayerResponse> = mutableListOf()
)

fun TeamSquadResponse.asDatabaseModel(): List<Player> {
    val resultList = mutableListOf<Player>()

    this.squad.forEach {
        val player = Player(
            playerId = it.id,
            teamId = this.id,
            name = it.name,
            firstName = it.firstName,
            lastName = it.lastName,
            dateOfBirth = it.dateOfBirth,
            countryOfBirth = it.countryOfBirth,
            nationality = it.nationality,
            position = it.position,
            shirtNumber = it.shirtNumber
        )
        resultList.add(player)
    }

    return resultList
}