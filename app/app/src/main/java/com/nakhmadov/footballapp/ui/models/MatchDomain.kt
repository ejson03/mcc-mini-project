package com.nakhmadov.footballapp.ui.models

data class MatchDomain (
    val matchId: Int = 0,
    val homeTeamId: Int = 0,
    val awayTeamId: Int = 0,
    val homeTeamScore: Int? = 0,
    val awayTeamScore: Int? = 0,
    val homeTeamEmblemUrl: String? = "",
    val awayTeamEmblemUrl: String? = "",
    val homeTeamCode: String? = "",
    val awayTeamCode: String? = "",
    val matchDate: String? = "",
    val competitionCode: String? = "",
    val competitionEmblemUrl: String? = "",
    val homeTeamIsFavorite: Boolean? = false,
    val awayTeamIsFavorite: Boolean? = false,
    val homeTeamName: String? = "",
    val awayTeamName: String? = ""

)