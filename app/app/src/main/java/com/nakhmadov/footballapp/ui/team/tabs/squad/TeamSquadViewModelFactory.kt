package com.nakhmadov.footballapp.ui.team.tabs.squad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.TeamRepository

class TeamSquadViewModelFactory(
    private val teamId: Int,
    private val teamRepository: TeamRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamSquadViewModel::class.java))
            return TeamSquadViewModel(teamId, teamRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}