package com.nakhmadov.footballapp.adapters.matches_list

import com.nakhmadov.footballapp.ui.models.MatchDomain

sealed class MatchesListItem {
    data class HeaderItem(val text: String) : MatchesListItem()
    data class MatchItem(val matchDomain: MatchDomain) : MatchesListItem()
    data class CompetitionMatchItem(val matchDomain: MatchDomain) : MatchesListItem()
    data class TeamMatchItem(val matchDomain: MatchDomain) : MatchesListItem()
}