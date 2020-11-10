package com.nakhmadov.footballapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.footballapp.data.db.models.CompetitionTableItem
import com.nakhmadov.footballapp.ui.models.TableItemDomain

@Dao
interface CompetitionTableDao {

    @Insert(onConflict = REPLACE)
    fun insertTable(table: List<CompetitionTableItem>)

    @Query(
        "select competition_table.teamId, competition_table.position, competition_table.points, team.emblemUrl as teamEmblemUrl, team.name as teamName, team.isFavorite as teamIsFavorite " +
                "from competition_table " +
                "join team on team.teamId = competition_table.teamId " +
                "where competition_table.competitionId = :competitionId and competition_table.position = 1"
    )
    fun firstPositionAsDomain(competitionId: Int): LiveData<TableItemDomain>


    @Query(
        "select competition_table.teamId, competition_table.position, competition_table.playedGames, competition_table.won, competition_table.draw, competition_table.lost, competition_table.points, competition_table.goalsFor, competition_table.goalsAgainst, competition_table.goalDifference, team.code as teamCode, team.name as teamName, team.emblemUrl as teamEmblemUrl, team.isFavorite as teamIsFavorite " +
                "from competition_table " +
                "join team on competition_table.teamId = team.teamId " +
                "where competition_table.competitionId = :competitionId order by competition_table.position"
    )
    fun competitionTable(competitionId: Int): LiveData<List<TableItemDomain>>

}