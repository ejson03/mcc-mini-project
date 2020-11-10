package com.nakhmadov.footballapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.footballapp.data.db.models.Player
import com.nakhmadov.footballapp.ui.models.PlayerDomain

@Dao
interface PlayerDao {

    @Insert(onConflict = REPLACE)
    fun insertAll(list: List<Player>)

    @Insert(onConflict = IGNORE)
    fun insertSquad(list: List<Player>)

    @Query(
        "select player.playerId, player.name, player.numberOfGoals, team.code as teamCode, team.emblemUrl as teamEmblemUrl " +
                "from player join team on player.teamId = team.teamId " +
                "where player.competitionId = :competitionId and player.numberOfGoals > 0 order by player.numberOfGoals desc"
    )
    fun scorersAsDomain(competitionId: Int): LiveData<List<PlayerDomain>>

    @Query("select * from player where teamId = :teamId")
    fun squadByTeamId(teamId: Int): LiveData<List<Player>>

}