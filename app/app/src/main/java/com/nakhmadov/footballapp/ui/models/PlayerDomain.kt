package com.nakhmadov.footballapp.ui.models

data class PlayerDomain(
    val playerId: Int = 0,
    var teamEmblemUrl: String? = "",
    var teamCode: String = "",
    val name: String = "",
    val numberOfGoals: Int? = 0
)