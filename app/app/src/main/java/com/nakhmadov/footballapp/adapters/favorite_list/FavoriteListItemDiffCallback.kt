package com.nakhmadov.footballapp.adapters.favorite_list

import androidx.recyclerview.widget.DiffUtil

class FavoriteListItemDiffCallback : DiffUtil.ItemCallback<FavoriteListItem>() {

    override fun areItemsTheSame(oldItem: FavoriteListItem, newItem: FavoriteListItem): Boolean {
        if (oldItem is FavoriteListItem.HeaderItem && newItem !is FavoriteListItem.HeaderItem)
            return false

        if (oldItem is FavoriteListItem.CompetitionItem && newItem !is FavoriteListItem.CompetitionItem)
            return false

        if (oldItem is FavoriteListItem.TeamItem && newItem !is FavoriteListItem.TeamItem)
            return false

        if (oldItem is FavoriteListItem.HeaderItem)
            return oldItem.headerText == (newItem as FavoriteListItem.HeaderItem).headerText

        if (oldItem is FavoriteListItem.CompetitionItem)
            return oldItem.competition.competitionId == (newItem as FavoriteListItem.CompetitionItem).competition.competitionId

        return (oldItem as FavoriteListItem.TeamItem).team.teamId == (newItem as FavoriteListItem.TeamItem).team.teamId

    }

    override fun areContentsTheSame(oldItem: FavoriteListItem, newItem: FavoriteListItem): Boolean {

        if (oldItem is FavoriteListItem.HeaderItem && newItem !is FavoriteListItem.HeaderItem)
            return false

        if (oldItem is FavoriteListItem.CompetitionItem && newItem !is FavoriteListItem.CompetitionItem)
            return false

        if (oldItem is FavoriteListItem.TeamItem && newItem !is FavoriteListItem.TeamItem)
            return false

        if (oldItem is FavoriteListItem.HeaderItem)
            return oldItem.headerText == (newItem as FavoriteListItem.HeaderItem).headerText

        if (oldItem is FavoriteListItem.CompetitionItem)
            return (oldItem as FavoriteListItem.CompetitionItem) == (newItem as FavoriteListItem.CompetitionItem)

        return (oldItem as FavoriteListItem.TeamItem) == (newItem as FavoriteListItem.TeamItem)

    }

}