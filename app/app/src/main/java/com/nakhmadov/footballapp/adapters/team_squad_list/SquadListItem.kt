package com.nakhmadov.footballapp.adapters.team_squad_list

import com.nakhmadov.footballapp.data.db.models.Player

sealed class SquadListItem {

    data class DataItem(val player: Player) : SquadListItem()
    data class HeaderItem(val header: String) : SquadListItem()
}