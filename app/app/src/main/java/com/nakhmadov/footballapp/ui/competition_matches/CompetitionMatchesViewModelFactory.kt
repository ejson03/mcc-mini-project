package com.nakhmadov.footballapp.ui.competition_matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.MatchRepository

class CompetitionMatchesViewModelFactory(
    private val competitionId: Int,
    private val statusType: Int,
    private val matchRepository: MatchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionMatchesViewModel::class.java))
            return CompetitionMatchesViewModel(
                competitionId = competitionId,
                status = statusType,
                matchRepository = matchRepository
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}