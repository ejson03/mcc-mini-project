package com.nakhmadov.footballapp.data.db.models

import androidx.room.Entity

@Entity(tableName = "competition_table", primaryKeys = ["position", "competitionId"])
data class CompetitionTableItem(
    val competitionId: Int = 0,
    val position: Int = 0,
    val teamId: Int = 0,
    val playedGames: Int = 0,
    val won: Int = 0,
    val draw: Int = 0,
    val lost: Int = 0,
    val points: Int = 0,
    val goalsFor: Int = 0,
    val goalsAgainst: Int = 0,
    val goalDifference: Int = 0
)