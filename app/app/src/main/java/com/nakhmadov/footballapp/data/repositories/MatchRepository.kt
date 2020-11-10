package com.nakhmadov.footballapp.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nakhmadov.football_data.util.STATUS_MATCH_FINISHED
import com.nakhmadov.footballapp.data.db.FootballDatabase
import com.nakhmadov.footballapp.data.remote.ApiInterface
import com.nakhmadov.footballapp.data.remote.response_models.asDatabaseModel
import com.nakhmadov.footballapp.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatchRepository private constructor(
    private val database: FootballDatabase,
    private val network: ApiInterface,
    private val application: Application
) {
    private val _updateLastResult = MutableLiveData<Int>()
    private val _updateCompetitionMatches = MutableLiveData<Pair<Int, Int>>()
    private val _updateMatchesOnTheDate = MutableLiveData<String>()

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean> = _isInternetConnected


    val lastResultAsDomain = Transformations.switchMap(_updateLastResult) {
        it?.let {
            database.matchDao.lastResultAsDomain(it)
        }
    }

    val competitionMatches = Transformations.switchMap(_updateCompetitionMatches) {
        it?.let {
            if (it.second == STATUS_MATCH_FINISHED)
                return@switchMap database.matchDao.competitionResults(it.first)

            return@switchMap database.matchDao.competitionFixtures(it.first)
        }
    }

    val matchesOnTheDate = Transformations.switchMap(_updateMatchesOnTheDate) {
        it?.let {
            val date = "%$it%"
            database.matchDao.matchesOnTheDate(date = date)
        }
    }

    suspend fun fetchAndSaveMatchesOnTheDate(date: String) {
        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchDateMatchesAsync(date).await()
                    val matches = networkResult.asDatabaseModel()

                    database.matchDao.insertAll(matches)

                    Log.d("myLogs", "FETCH AND SAVE DATE MATCHES RESULT: $matches")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(
                        "myLogs",
                        "FETCH AND SAVE DATE MATCHES ERROR: ${e.localizedMessage}"
                    )
                }
            }
        } else
            _isInternetConnected.value = false
    }

    fun getMatchesOnTheDate(date: String) {
        _updateMatchesOnTheDate.value = date
    }

    fun getLastResult(competitionId: Int) {
        _updateLastResult.value = competitionId
    }

    fun getCompetitionMatches(competitionId: Int, status: Int) {
        _updateCompetitionMatches.value = Pair(competitionId, status)
    }

    companion object {
        private var INSTANCE: MatchRepository? = null
        fun getRepository(
            database: FootballDatabase,
            network: ApiInterface,
            application: Application
        ): MatchRepository {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = MatchRepository(database, network, application)
                }
                return INSTANCE as MatchRepository
            }
        }
    }
}