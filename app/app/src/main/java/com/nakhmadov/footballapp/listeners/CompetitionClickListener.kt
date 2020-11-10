package com.nakhmadov.footballapp.listeners

import com.nakhmadov.footballapp.data.db.models.Competition

class CompetitionClickListener(val listener: (competitionId: Int, competitionName: String, isFavorite: Boolean) -> Unit) {
    fun onCompetitionClick(competition: Competition) =
        listener(competition.competitionId, competition.title, competition.isFavorite)
}