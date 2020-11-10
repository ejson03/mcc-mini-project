package com.nakhmadov.footballapp.ui.competition_teams

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CompetitionTeamsViewModel(
    private val competitionId: Int,
    private val competitionRepository: CompetitionRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val teams = competitionRepository.teams

    init {
        competitionRepository.getCompetitionTeams(competitionId)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
