package com.nakhmadov.footballapp.ui.team.tabs.matches

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamMatchesViewModel(private val teamId: Int, private val teamRepository: TeamRepository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val matches = teamRepository.teamMatches

    init {
        uiScope.launch {
            teamRepository.fetchAndSaveTeamMatches(teamId)
        }
        teamRepository.getTeamMatches(teamId)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
