package com.nakhmadov.footballapp.ui.competition_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository

class CompetitionTableViewModelFactory(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionTableViewModel::class.java))
            return CompetitionTableViewModel(competitionId, competitionRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}