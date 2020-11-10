package com.nakhmadov.footballapp.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nakhmadov.football_data.util.REQUIRED_COMPETITION_COUNT
import com.nakhmadov.footballapp.data.db.FootballDatabase
import com.nakhmadov.footballapp.data.db.models.Competition
import com.nakhmadov.footballapp.data.remote.ApiInterface
import com.nakhmadov.footballapp.data.remote.response_models.asDatabaseModel
import com.nakhmadov.footballapp.util.isConnectedInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CompetitionRepository private constructor(
    private val database: FootballDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    val competitions = database.competitionDao.competitions()
    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean> = _isInternetConnected

    private val _updateCompetition = MutableLiveData<Int>()
    val competition = Transformations.switchMap(_updateCompetition) {
        it?.let {
            database.competitionDao.competitionById(it)
        }
    }

    private val _updateScorersList = MutableLiveData<Int>()
    val scorers = Transformations.switchMap(_updateScorersList) {
        it?.let {
            database.playerDao.scorersAsDomain(it)
        }
    }

    private val _updateCompetitionTable = MutableLiveData<Int>()
    val table = Transformations.switchMap(_updateCompetitionTable) {
        it?.let {
            database.competitionTableDao.competitionTable(competitionId = it)
        }
    }

    private val _updateCompetitionTeams = MutableLiveData<Int>()
    val teams = Transformations.switchMap(_updateCompetitionTeams) {
        it?.let {
            database.teamDao.competitionTeams(it)
        }
    }

    private val _updateFavorites = MutableLiveData<Boolean>()
    val favorites = Transformations.switchMap(_updateFavorites) {
        it?.let {
             Log.d("myLogs", "UPDATING FAVORITES")
            database.competitionDao.favoriteList()
        }
    }

    fun getCompetition(competitionId: Int) {
        _updateCompetition.value = competitionId
    }

    fun getCompetitionScorers(competitionId: Int) {
        _updateScorersList.value = competitionId
    }

    fun getCompetitionTable(competitionId: Int) {
        _updateCompetitionTable.value = competitionId
    }

    fun getCompetitionTeams(competitionId: Int) {
        _updateCompetitionTeams.value = competitionId
    }

    fun getFavorites() {
        _updateFavorites.value = true
    }

    suspend fun changeFavoriteStatus(competitionId: Int, favoriteStatus: Boolean) {
        withContext(Dispatchers.IO) {
            database.competitionDao.updateFavoriteStatus(competitionId, favoriteStatus)
        }
    }

    suspend fun checkValidCountOfCompetitions() {
        if (getCount() != REQUIRED_COMPETITION_COUNT) {
            if (isConnectedInternet(application)) {
                fetchAndSaveCompetitions()
                _isInternetConnected.value = true
            } else
                _isInternetConnected.value = false
        }
    }

    suspend fun fetchAndSaveCompetitionMatches(competitionId: Int) {

        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchCompetitionMatchesAsync(competitionId).await()
                    val matches = networkResult.asDatabaseModel()

                    database.matchDao.insertAll(matches)

                    Log.d("myLogs", "FETCH AND SAVE COMPETITION MATCHES RESULT: $matches")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(
                        "myLogs",
                        "FETCH AND SAVE COMPETITION MATCHES ERROR: ${e.localizedMessage}"
                    )
                }
            }
        } else
            _isInternetConnected.value = false
    }

    suspend fun fetchAndSaveCompetitionTeams(competitionId: Int) {
        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchCompetitionTeamsAsync(competitionId).await()
                    val teams = networkResult.asDatabaseModel()

                    database.teamDao.insertTeams(teams)

                    Log.d("myLogs", "FETCH AND SAVE COMPETITION TEAMS RESULT: $teams")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("myLogs", "FETCH AND SAVE COMPETITION TEAMS ERROR: ${e.localizedMessage}")
                }
            }
        } else
            _isInternetConnected.value = false
    }

    suspend fun fetchAndSaveCompetitionTable(competitionId: Int) {
        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchCompetitionTableAsync(competitionId).await()
                    val table = networkResult.asDatabaseModel()

                    database.competitionTableDao.insertTable(table)

                    Log.d("myLogs", "FETCH AND SAVE COMPETITION TABLE RESULT: $table")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("myLogs", "FETCH AND SAVE COMPETITION TABLE ERROR: ${e.localizedMessage}")
                }
            }
        } else
            _isInternetConnected.value = false
    }

    suspend fun fetchAndSaveCompetitionStats(competitionId: Int) {
        if (isConnectedInternet(application)) {
            _isInternetConnected.value = true
            withContext(Dispatchers.IO) {
                try {
                    val networkResult = network.fetchCompetitionScorersAsync(competitionId).await()
                    val scorers = networkResult.asDatabaseModel()

                    database.playerDao.insertAll(scorers)

                    Log.d("myLogs", "FETCH AND SAVE COMPETITION SCORERS RESULT: $scorers")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(
                        "myLogs",
                        "FETCH AND SAVE COMPETITION SCORERS ERROR: ${e.localizedMessage}"
                    )
                }
            }
        } else
            _isInternetConnected.value = false
    }

    private suspend fun fetchAndSaveCompetitions() {
        withContext(Dispatchers.IO) {
            try {
                val competitionsNetworkResult = network.fetchCompetitionsAsync().await()
                val competitionsList = competitionsNetworkResult.asDatabaseModel()

                chooseNeededCompetitionAndSave(competitionsList)
                Log.d("myLogs", "COMPETITIONS $competitionsList")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("myLogs", "COMPETITIONS ERROR:  ${e.localizedMessage}")
            }
        }
    }

    private fun chooseNeededCompetitionAndSave(list: List<Competition>) {
        list.forEach {
            when (it.code) {
                "PL" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f2/Premier_League_Logo.svg/640px-Premier_League_Logo.svg.png"
                    database.competitionDao.insert(it)
                }
                "PD" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/LaLiga.svg/546px-LaLiga.svg.png"
                    database.competitionDao.insert(it)
                }
                "SA" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/en/thumb/e/e1/Serie_A_logo_%282019%29.svg/436px-Serie_A_logo_%282019%29.svg.png"
                    database.competitionDao.insert(it)
                }
                "BL1" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/en/thumb/d/df/Bundesliga_logo_%282017%29.svg/600px-Bundesliga_logo_%282017%29.svg.png"
                    database.competitionDao.insert(it)
                }
                "FL1" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/fr/thumb/1/14/Ligue_1_Conforama.svg/557px-Ligue_1_Conforama.svg.png"
                    database.competitionDao.insert(it)
                }
                "PPL" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Liga_NOS_logo.png/573px-Liga_NOS_logo.png"
                    database.competitionDao.insert(it)
                }
                "DED" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/Eredivisie_nieuw_logo_2017-.svg/640px-Eredivisie_nieuw_logo_2017-.svg.png"
                    database.competitionDao.insert(it)
                }
                "ELC" -> {
                    it.emblemUrl =
                        "https://upload.wikimedia.org/wikipedia/ru/thumb/2/2e/Football_League_Championship.svg/640px-Football_League_Championship.svg.png"
                    database.competitionDao.insert(it)
                }
            }
        }
    }

    private suspend fun getCount(): Int = withContext(Dispatchers.IO) {
        database.competitionDao.count()
    }

    companion object {
        private var INSTANCE: CompetitionRepository? = null
        fun getRepository(
            database: FootballDatabase,
            network: ApiInterface,
            application: Application
        ): CompetitionRepository {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = CompetitionRepository(database, network, application)
                }
                return INSTANCE as CompetitionRepository
            }
        }
    }
}