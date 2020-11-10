package com.nakhmadov.footballapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.footballapp.data.db.models.Team

@Dao
interface TeamDao {

    @Insert(onConflict = IGNORE)
    fun insertTeams(teams: List<Team>)

    @Query("select * from team where teamId = :teamId")
    fun teamById(teamId: Int): LiveData<Team>

    @Query("select * from team where competitionId = :competitionId")
    fun competitionTeams(competitionId: Int): LiveData<List<Team>>

    @Query("update team set isFavorite = :favoriteStatus where teamId = :teamId")
    fun updateFavoriteStatus(teamId: Int, favoriteStatus: Boolean)
}