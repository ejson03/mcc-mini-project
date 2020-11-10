package com.nakhmadov.footballapp.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.TeamRepository

class TeamViewModelFactory(
    private val teamId: Int,
    private val teamIsFavorite: Boolean,
    private val teamRepository: TeamRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java))
            return TeamViewModel(teamId, teamIsFavorite, teamRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}