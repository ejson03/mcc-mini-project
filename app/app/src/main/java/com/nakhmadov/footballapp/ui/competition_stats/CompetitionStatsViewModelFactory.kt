package com.nakhmadov.footballapp.ui.competition_stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository

class CompetitionStatsViewModelFactory(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionStatsViewModel::class.java))
            return CompetitionStatsViewModel(
                competitionId = competitionId,
                competitionRepository = competitionRepository
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}