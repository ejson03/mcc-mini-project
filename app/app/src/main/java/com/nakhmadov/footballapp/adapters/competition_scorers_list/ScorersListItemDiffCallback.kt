package com.nakhmadov.footballapp.adapters.competition_scorers_list

import androidx.recyclerview.widget.DiffUtil

class ScorersListItemDiffCallback : DiffUtil.ItemCallback<ScorersListItem>() {


    override fun areItemsTheSame(oldItem: ScorersListItem, newItem: ScorersListItem): Boolean {
        if (oldItem is ScorersListItem.DataItem && newItem !is ScorersListItem.DataItem)
            return false
        if (oldItem is ScorersListItem.HeaderItem && newItem !is ScorersListItem.HeaderItem)
            return false
        if (oldItem is ScorersListItem.HeaderItem)
            return true
        return (oldItem as ScorersListItem.DataItem).playerDomain.playerId == (newItem as ScorersListItem.DataItem).playerDomain.playerId
    }

    override fun areContentsTheSame(oldItem: ScorersListItem, newItem: ScorersListItem): Boolean {
        if (oldItem is ScorersListItem.DataItem && newItem !is ScorersListItem.DataItem)
            return false
        if (oldItem is ScorersListItem.HeaderItem && newItem !is ScorersListItem.HeaderItem)
            return false
        if (oldItem is ScorersListItem.HeaderItem)
            return true
        return (oldItem as ScorersListItem.DataItem) == (newItem as ScorersListItem.DataItem)
    }

}