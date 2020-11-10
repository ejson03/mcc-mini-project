package com.nakhmadov.footballapp.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nakhmadov.footballapp.data.db.FootballDatabase
import com.nakhmadov.footballapp.data.remote.ApiInterface
import com.nakhmadov.footballapp.data.remote.response_models.asDatabaseModel
import com.nakhmadov.footballapp.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepository private constructor(
    private val database: FootballDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    private val _updateTeam = MutableLiveData<Int>()
    private val _updateTeamSquad = MutableLiveData<Int>()
    private val _updateTeamMatches = MutableLiveData<Int>()

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean> = _isInternetConnected

    val team = Transformations.switchMap(_updateTeam) {
        it?.let {
            database.teamDao.teamById(it)
        }
    }

    val teamSquad = Transformations.switchMap(_updateTeamSquad) {
        it?.let {
            database.playerDao.squadByTeamId(it)
        }
    }

    val teamMatches = Transformations.switchMap(_updateTeamMatches) {
        it?.let {
            database.matchDao.teamMatchesByTeamIdAsDomain(it)
        }
    }

    fun getTeam(teamId: Int) {
        _updateTeam.value = teamId
    }

    fun getTeamSquad(teamId: Int) {
        _updateTeamSquad.value = teamId
    }

    fun getTeamMatches(teamId: Int) {
        _updateTeamMatches.value = teamId
    }

    suspend fun fetchAndSaveTeamSquad(teamId: Int) {

        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchTeamSquadAsync(teamId).await()
                    val squad = networkResult.asDatabaseModel()

                    database.playerDao.insertSquad(squad)

                    Log.d("myLogs", "FETCH AND SAVE TEAM SQUAD $squad")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("myLogs", "FETCH AND SAVE TEAM SQUAD: ${e.localizedMessage}")
                }
            }
        } else
            _isInternetConnected.value = false
    }

    suspend fun fetchAndSaveTeamMatches(teamId: Int) {

        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchTeamMatchesAsync(teamId = teamId).await()
                    val matches = networkResult.asDatabaseModel()

                    database.matchDao.insertTeamMatches(matches)


                    Log.d("myLogs", "FETCH AND SAVE TEAM MATCHES $matches")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("myLogs", "FETCH AND SAVE TEAM MATCHES: ${e.localizedMessage}")
                }
            }
        } else
            _isInternetConnected.value = false
    }

    suspend fun changeFavoriteStatus(teamId: Int, favoriteStatus: Boolean) {
        withContext(Dispatchers.IO) {
            database.teamDao.updateFavoriteStatus(teamId, favoriteStatus)
        }
    }

    companion object {
        private var INSTANCE: TeamRepository? = null
        fun getRepository(
            database: FootballDatabase,
            network: ApiInterface,
            application: Application
        ): TeamRepository {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = TeamRepository(database, network, application)
                }
                return INSTANCE as TeamRepository
            }
        }
    }
}