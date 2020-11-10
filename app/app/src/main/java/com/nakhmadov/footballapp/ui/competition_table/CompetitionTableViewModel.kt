package com.nakhmadov.footballapp.ui.competition_table

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CompetitionTableViewModel(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val table = competitionRepository.table

    init {
        competitionRepository.getCompetitionTable(competitionId)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
