package com.nakhmadov.footballapp.ui.competition_stats

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CompetitionStatsViewModel(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val scorers = competitionRepository.scorers

    init {
        competitionRepository.getCompetitionScorers(competitionId = competitionId)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
