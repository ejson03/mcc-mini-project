package com.nakhmadov.footballapp.adapters.matches_list

import androidx.recyclerview.widget.DiffUtil

class MatchesListItemDiffCallback : DiffUtil.ItemCallback<MatchesListItem>() {
    override fun areItemsTheSame(
        oldItem: MatchesListItem,
        newItem: MatchesListItem
    ): Boolean {
        if (oldItem is MatchesListItem.HeaderItem && newItem !is MatchesListItem.HeaderItem)
            return false
        if (oldItem is MatchesListItem.MatchItem && newItem !is MatchesListItem.MatchItem)
            return false
        if (oldItem is MatchesListItem.CompetitionMatchItem && newItem !is MatchesListItem.CompetitionMatchItem)
            return false
        if (oldItem is MatchesListItem.TeamMatchItem && newItem !is MatchesListItem.TeamMatchItem)
            return false
        if (oldItem is MatchesListItem.HeaderItem)
            return oldItem.text == (newItem as MatchesListItem.HeaderItem).text
        if (oldItem is MatchesListItem.MatchItem)
            return oldItem.matchDomain.matchId == (newItem as MatchesListItem.MatchItem).matchDomain.matchId
        if (oldItem is MatchesListItem.CompetitionMatchItem)
            return oldItem.matchDomain.matchId == (newItem as MatchesListItem.CompetitionMatchItem).matchDomain.matchId
        return (oldItem as MatchesListItem.TeamMatchItem).matchDomain.matchId == (newItem as MatchesListItem.TeamMatchItem).matchDomain.matchId

    }

    override fun areContentsTheSame(
        oldItem: MatchesListItem,
        newItem: MatchesListItem
    ): Boolean {
        if (oldItem is MatchesListItem.HeaderItem && newItem !is MatchesListItem.HeaderItem)
            return false
        if (oldItem is MatchesListItem.MatchItem && newItem !is MatchesListItem.MatchItem)
            return false
        if (oldItem is MatchesListItem.CompetitionMatchItem && newItem !is MatchesListItem.CompetitionMatchItem)
            return false
        if (oldItem is MatchesListItem.TeamMatchItem && newItem !is MatchesListItem.TeamMatchItem)
            return false
        if (oldItem is MatchesListItem.HeaderItem)
            return oldItem.text == (newItem as MatchesListItem.HeaderItem).text
        if (oldItem is MatchesListItem.MatchItem)
            return (oldItem as MatchesListItem.MatchItem) == (newItem as MatchesListItem.MatchItem)
        if (oldItem is MatchesListItem.CompetitionMatchItem)
            return (oldItem as MatchesListItem.CompetitionMatchItem) == (newItem as MatchesListItem.CompetitionMatchItem)
        return (oldItem as MatchesListItem.TeamMatchItem) == (newItem as MatchesListItem.TeamMatchItem)

    }

}