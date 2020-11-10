package com.nakhmadov.footballapp.ui.competition_teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository

class CompetitionTeamsViewModelFactory(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionTeamsViewModel::class.java))
            return CompetitionTeamsViewModel(competitionId, competitionRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}