package com.nakhmadov.footballapp.listeners

import com.nakhmadov.footballapp.data.db.models.Team
import com.nakhmadov.footballapp.ui.models.MatchDomain
import com.nakhmadov.footballapp.ui.models.TableItemDomain

class TeamClickListener(val itemListener: (teamId: Int, teamName: String, isFavorite: Boolean) -> Unit) {
    fun onTableTeamClick(tableItem: TableItemDomain) =
        itemListener(tableItem.teamId, tableItem.teamName, tableItem.teamIsFavorite)

    fun onTeamClick(team: Team) = itemListener(team.teamId, team.name, team.isFavorite)

    fun onHomeTeamClickListener(matchDomain: MatchDomain) = itemListener(
        matchDomain.homeTeamId,
        matchDomain.homeTeamName!!,
        matchDomain.homeTeamIsFavorite!!
    )

    fun onAwayTeamClickListener(matchDomain: MatchDomain) = itemListener(
        matchDomain.awayTeamId,
        matchDomain.awayTeamName!!,
        matchDomain.awayTeamIsFavorite!!
    )
}