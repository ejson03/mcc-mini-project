package com.nakhmadov.footballapp.adapters.team_squad_list

import androidx.recyclerview.widget.DiffUtil

class SquadListItemDiffCallback : DiffUtil.ItemCallback<SquadListItem>() {


    override fun areItemsTheSame(oldItem: SquadListItem, newItem: SquadListItem): Boolean {
        if (oldItem is SquadListItem.DataItem && newItem !is SquadListItem.DataItem)
            return false
        if (oldItem is SquadListItem.HeaderItem && newItem !is SquadListItem.HeaderItem)
            return false
        if (oldItem is SquadListItem.HeaderItem)
            return oldItem.header == (newItem as SquadListItem.HeaderItem).header
        return (oldItem as SquadListItem.DataItem).player.playerId == (newItem as SquadListItem.DataItem).player.playerId
    }

    override fun areContentsTheSame(oldItem: SquadListItem, newItem: SquadListItem): Boolean {
        if (oldItem is SquadListItem.DataItem && newItem !is SquadListItem.DataItem)
            return false
        if (oldItem is SquadListItem.HeaderItem && newItem !is SquadListItem.HeaderItem)
            return false
        if (oldItem is SquadListItem.HeaderItem)
            return oldItem.header == (newItem as SquadListItem.HeaderItem).header
        return (oldItem as SquadListItem.DataItem) == (newItem as SquadListItem.DataItem)
    }

}