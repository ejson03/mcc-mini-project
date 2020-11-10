package com.nakhmadov.footballapp.adapters.competition_table

import androidx.recyclerview.widget.DiffUtil

class CompetitionTableItemDiffCallback : DiffUtil.ItemCallback<CompetitionTableItem>() {
    override fun areItemsTheSame(

        oldItem: CompetitionTableItem,
        newItem: CompetitionTableItem
    ): Boolean {
        if (oldItem is CompetitionTableItem.DataItem && newItem !is CompetitionTableItem.DataItem)
            return false
        if (oldItem is CompetitionTableItem.HeaderItem && newItem !is CompetitionTableItem.HeaderItem)
            return false
        if (oldItem is CompetitionTableItem.HeaderItem)
            return true
        return (oldItem as CompetitionTableItem.DataItem).tableItem.teamId == (newItem as CompetitionTableItem.DataItem).tableItem.teamId
    }

    override fun areContentsTheSame(
        oldItem: CompetitionTableItem,
        newItem: CompetitionTableItem
    ): Boolean {

        if (oldItem is CompetitionTableItem.DataItem && newItem !is CompetitionTableItem.DataItem)
            return false
        if (oldItem is CompetitionTableItem.HeaderItem && newItem !is CompetitionTableItem.HeaderItem)
            return false
        if (oldItem is CompetitionTableItem.HeaderItem)
            return true
        return (oldItem as CompetitionTableItem.DataItem) == (newItem as CompetitionTableItem.DataItem)
    }


}