package com.nakhmadov.footballapp.ui.competition_matches

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CompetitionMatchesViewModel(
    private val competitionId: Int,
    private val status: Int,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val matches = matchRepository.competitionMatches

    init {
        matchRepository.getCompetitionMatches(competitionId, status)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
