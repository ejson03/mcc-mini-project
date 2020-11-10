package com.nakhmadov.footballapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.nakhmadov.footballapp.data.db.models.Competition
import com.nakhmadov.footballapp.data.db.models.Team
import com.nakhmadov.footballapp.ui.models.FavoriteDomain

@Dao
interface CompetitionDao {

    @Query("select count(competitionId) from competition")
    fun count(): Int

    @Query("select * from competition")
    fun competitions(): LiveData<List<Competition>>

    @Query("select * from competition where competitionId = :competitionId")
    fun competitionById(competitionId: Int): LiveData<Competition>

    @Insert(onConflict = REPLACE)
    fun insert(competition: Competition)

    @Query("update competition set isFavorite = :favoriteStatus where competitionId = :competitionId")
    fun updateFavoriteStatus(competitionId: Int, favoriteStatus: Boolean)

    @Query("select competition.competitionId as id, competition.title as name, competition.emblemUrl as emblemUrl, 1 as isCompetition from competition where competition.isFavorite = 1 union all select team.teamId as id, team.name as name, team.emblemUrl as emblemUrl, null as isCompetition from team where team.isFavorite = 1")
    fun favoriteList(): LiveData<List<FavoriteDomain>>

    @Query("select * from competition where isFavorite = 1")
    fun favoriteCompetitions() : LiveData<List<Competition>>

    @Query("select * from team where isFavorite = 1")
    fun favoriteTeams() : LiveData<List<Team>>
}