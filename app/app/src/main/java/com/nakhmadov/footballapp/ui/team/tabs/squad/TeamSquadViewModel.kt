package com.nakhmadov.footballapp.ui.team.tabs.squad

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamSquadViewModel(private val teamId: Int, private val teamRepository: TeamRepository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val squad = teamRepository.teamSquad

    init {
        uiScope.launch {
            teamRepository.fetchAndSaveTeamSquad(teamId)
        }
        teamRepository.getTeamSquad(teamId)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
