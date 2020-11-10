package com.nakhmadov.footballapp.ui.team.tabs.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.TeamRepository

class TeamMatchesViewModelFactory(
    private val teamId: Int,
    private val teamRepository: TeamRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamMatchesViewModel::class.java))
            return TeamMatchesViewModel(teamId, teamRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}