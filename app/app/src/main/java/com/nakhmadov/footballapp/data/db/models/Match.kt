package com.nakhmadov.footballapp.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_game")
data class Match(
    @PrimaryKey
    val matchId: Int = 0,
    val competitionId: Int? = 0,
    val date: String = "",
    val matchday: Int? = 0,
    val winner: String? = "",
    val homeTeamScore: Int? = 0,
    val awayTeamScore: Int? = 0,
    val status: String = "",
    val homeTeamId: Int = 0,
    val awayTeamId: Int = 0
)