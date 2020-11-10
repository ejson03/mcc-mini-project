package com.nakhmadov.footballapp.adapters.favorite_list

import com.nakhmadov.footballapp.data.db.models.Competition
import com.nakhmadov.footballapp.data.db.models.Team

sealed class FavoriteListItem {
    data class HeaderItem(val headerText: String) : FavoriteListItem()
    data class CompetitionItem(val competition: Competition) : FavoriteListItem()
    data class TeamItem(val team: Team) : FavoriteListItem()
}