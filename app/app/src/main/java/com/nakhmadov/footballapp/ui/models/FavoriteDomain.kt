package com.nakhmadov.footballapp.ui.models

data class FavoriteDomain(
    val id: Int? = 0,
    val name: String? = "",
    val emblemUrl: String = "",
    val isCompetition: Boolean? = false
)