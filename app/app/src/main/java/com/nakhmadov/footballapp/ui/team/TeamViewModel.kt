package com.nakhmadov.footballapp.ui.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamViewModel(
    private val teamId: Int,
    private val teamIsFavorite: Boolean,
    private val teamRepository: TeamRepository
) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _isFavoriteTeam = MutableLiveData<Boolean>(teamIsFavorite)
    val isFavoriteTeam: LiveData<Boolean> = _isFavoriteTeam

    val team = teamRepository.team

    init {
        teamRepository.getTeam(teamId)
    }

    fun favoriteStatusChanged() {
        _isFavoriteTeam.value?.let {
            _isFavoriteTeam.value = !_isFavoriteTeam.value!!

            uiScope.launch {
                _isFavoriteTeam.value?.let {
                    Log.d("myLogs", "CHANGING FAVORITE STATUS")
                    teamRepository.changeFavoriteStatus(teamId, it)
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
