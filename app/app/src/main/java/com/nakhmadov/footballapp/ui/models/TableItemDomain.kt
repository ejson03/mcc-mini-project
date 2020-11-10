package com.nakhmadov.footballapp.ui.models

data class TableItemDomain(
    val teamId: Int = 0,
    val teamCode: String? = "",
    val teamName: String = "",
    val teamEmblemUrl: String? = "",
    val teamIsFavorite: Boolean = false,
    val position: Int? = 0,
    val playedGames: Int? = 0,
    val won: Int? = 0,
    val draw: Int? = 0,
    val lost: Int? = 0,
    val points: Int? = 0,
    val goalsFor: Int? = 0,
    val goalsAgainst: Int? = 0,
    val goalDifference: Int? = 0
)