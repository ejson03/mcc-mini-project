package com.nakhmadov.footballapp.listeners

import com.nakhmadov.footballapp.data.db.models.Player
import com.nakhmadov.footballapp.ui.models.PlayerDomain

class PlayerClickListener(val itemListener: (playerId: Int) -> Unit) {
    fun onPlayerDomainClickListener(playerDomain: PlayerDomain) = itemListener(playerDomain.playerId)
    fun onPlayerClickListener(player: Player) = itemListener(player.playerId)
}