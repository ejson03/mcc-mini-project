package com.nakhmadov.footballapp.ui.competitions_list

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CompetitionsListViewModel(private val competitionRepository: CompetitionRepository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val competitions = competitionRepository.competitions
    val isInternetConnected = competitionRepository.isInternetConnected

    init {
        uiScope.launch {
            competitionRepository.checkValidCountOfCompetitions()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
