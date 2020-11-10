package com.nakhmadov.footballapp.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nakhmadov.footballapp.data.db.FootballDatabase

class StatRepository private constructor(
    private val database: FootballDatabase
) {

    private val _updateFirstTablePosition = MutableLiveData<Int>()


    val firstTablePositionAsDomain = Transformations.switchMap(_updateFirstTablePosition) {
        it?.let {
            database.competitionTableDao.firstPositionAsDomain(it)
        }
    }

    fun getFirstTablePosition(competitionId: Int) {
        _updateFirstTablePosition.value = competitionId
    }

    companion object {
        private var INSTANCE: StatRepository? = null
        fun getRepository(
            database: FootballDatabase
        ): StatRepository {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = StatRepository(database)
                }
                return INSTANCE as StatRepository
            }
        }
    }
}