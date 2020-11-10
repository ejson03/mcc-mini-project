package com.nakhmadov.footballapp.ui.competition_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nakhmadov.football_data.util.Event
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CompetitionDetailViewModel(
    private val competitionId: Int,
    private val isFavorite: Boolean,
    private val competitionRepository: CompetitionRepository,
    private val matchRepository: MatchRepository,
    private val statRepository: StatRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _isFavoriteCompetition = MutableLiveData<Boolean>(isFavorite)
    val isFavoriteCompetition: LiveData<Boolean> = _isFavoriteCompetition

    val competition = competitionRepository.competition

    private val _updateLastResult = MutableLiveData<Int>()
    val lastResultAsDomain = Transformations.switchMap(_updateLastResult) {
        it?.let {
            val data = matchRepository.lastResultAsDomain
            data
        }
    }

    private val _updateFirstTablePosition = MutableLiveData<Int>()
    val firstTablePositionAsDomain = Transformations.switchMap(_updateFirstTablePosition) {
        it?.let {
            val data = statRepository.firstTablePositionAsDomain
            data
        }
    }

    private val _countOfDone = MutableLiveData<Int>()
    val countOfDone: LiveData<Int> = _countOfDone

    private val _navigateFixturesEvent = MutableLiveData<Event<Boolean>>()
    val navigateFixturesEvent: LiveData<Event<Boolean>> = _navigateFixturesEvent

    private val _navigateResultsEvent = MutableLiveData<Event<Boolean>>()
    val navigateResultsEvent: LiveData<Event<Boolean>> = _navigateResultsEvent

    private val _navigateTableEvent = MutableLiveData<Event<Boolean>>()
    val navigateTableEvent: LiveData<Event<Boolean>> = _navigateTableEvent

    private val _navigateTeamsEvent = MutableLiveData<Event<Boolean>>()
    val navigateTeamsEvent: LiveData<Event<Boolean>> = _navigateTeamsEvent

    private val _navigateStatsEvent = MutableLiveData<Event<Boolean>>()
    val navigateStatsEvent: LiveData<Event<Boolean>> = _navigateStatsEvent

    init {
        uiScope.launch {
            competitionRepository.fetchAndSaveCompetitionStats(competitionId)
            competitionRepository.fetchAndSaveCompetitionTable(competitionId)
            competitionRepository.fetchAndSaveCompetitionTeams(competitionId)
            competitionRepository.fetchAndSaveCompetitionMatches(competitionId)
        }
        competitionRepository.getCompetition(competitionId)
        matchRepository.getLastResult(competitionId)
        statRepository.getFirstTablePosition(competitionId)
    }

    fun favoriteStatusChanged() {
        _isFavoriteCompetition.value?.let {
            _isFavoriteCompetition.value = !_isFavoriteCompetition.value!!

            uiScope.launch {
                _isFavoriteCompetition.value?.let {
                    Log.d("myLogs", "CHANGING FAVORITE STATUS")
                    competitionRepository.changeFavoriteStatus(competitionId, it)
                }
            }
        }
    }


    fun getLastCompetitionResult(competitionId: Int) {
        _updateLastResult.value = competitionId
    }

    fun getFirstTablePosition(competitionId: Int) {
        _updateFirstTablePosition.value = competitionId
    }

    fun checkCountOfDone(
        lastResult: Int,
        tableFirstPositionTeam: Int
    ) {
        _countOfDone.value = (lastResult + tableFirstPositionTeam)
    }


    fun navigateFixtures() {
        _navigateFixturesEvent.value = Event(true)
    }

    fun navigateResults() {
        _navigateResultsEvent.value = Event(true)
    }

    fun navigateTable() {
        _navigateTableEvent.value = Event(true)
    }

    fun navigateTeams() {
        _navigateTeamsEvent.value = Event(true)
    }

    fun navigateStats() {
        _navigateStatsEvent.value = Event(true)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}
