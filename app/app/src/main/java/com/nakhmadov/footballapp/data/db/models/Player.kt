package com.nakhmadov.footballapp.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey
    val playerId: Int = 0,
    val teamId: Int = 0,
    val name: String = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val dateOfBirth: String? = "",
    val countryOfBirth: String? = "",
    val nationality: String? = "",
    val position: String? = "",
    val shirtNumber: Int? = 0,
    val numberOfGoals: Int? = 0,
    val competitionId: Int? = 0
)