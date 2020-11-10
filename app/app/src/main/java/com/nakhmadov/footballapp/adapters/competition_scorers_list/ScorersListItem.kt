package com.nakhmadov.footballapp.adapters.competition_scorers_list

import com.nakhmadov.footballapp.ui.models.PlayerDomain

sealed class ScorersListItem {

    data class DataItem(val playerDomain: PlayerDomain) : ScorersListItem()
    object HeaderItem : ScorersListItem()
}