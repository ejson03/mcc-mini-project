package com.nakhmadov.footballapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.nakhmadov.footballapp.data.db.models.Match
import com.nakhmadov.footballapp.ui.models.MatchDomain

@Dao
interface MatchDao {

    @Query("select * from match_game where competitionId = :competitionId and status = \"FINISHED\" order by date desc limit 1")
    fun lastResult(competitionId: Int): LiveData<Match>

    @Insert(onConflict = REPLACE)
    fun insertAll(list: List<Match>)

    @Insert(onConflict = IGNORE)
    fun insertTeamMatches(list: List<Match>)

    @Query(
        "select match_game.matchId, match_game.homeTeamId, match_game.awayTeamId, match_game.homeTeamScore, match_game.awayTeamScore, match_game.date as matchDate, homeTeam.code as homeTeamCode, homeTeam.emblemUrl as homeTeamEmblemUrl, awayTeam.code as awayTeamCode, awayTeam.emblemUrl as awayTeamEmblemUrl, competition.code as competitionCode, competition.emblemUrl as competitionEmblemUrl " +
                "from match_game " +
                "join team as homeTeam on match_game.homeTeamId = homeTeam.teamId " +
                "join team as awayTeam on match_game.awayTeamId = awayTeam.teamId " +
                "join competition on match_game.competitionId = competition.competitionId " +
                "where awayTeamId = :teamId or homeTeamId = :teamId"
    )
    fun teamMatchesByTeamIdAsDomain(teamId: Int): LiveData<List<MatchDomain>>

    @Query(
        "select match_game.matchId, match_game.homeTeamId, match_game.awayTeamId, match_game.homeTeamScore, match_game.awayTeamScore, match_game.date as matchDate, homeTeam.code as homeTeamCode, homeTeam.emblemUrl as homeTeamEmblemUrl, awayTeam.code as awayTeamCode, awayTeam.emblemUrl as awayTeamEmblemUrl " +
                "from match_game " +
                "join team as homeTeam on match_game.homeTeamId = homeTeam.teamId " +
                "join team as awayTeam on match_game.awayTeamId = awayTeam.teamId " +
                "where match_game.competitionId = :competitionId and match_game.status = \"FINISHED\" order by date desc limit 1"
    )
    fun lastResultAsDomain(competitionId: Int): LiveData<MatchDomain>

    @Query(
        "select match_game.matchId, match_game.homeTeamId, match_game.awayTeamId, match_game.homeTeamScore, match_game.awayTeamScore, match_game.date as matchDate, homeTeam.code as homeTeamCode, homeTeam.emblemUrl as homeTeamEmblemUrl, awayTeam.code as awayTeamCode, awayTeam.emblemUrl as awayTeamEmblemUrl, homeTeam.isFavorite as homeTeamIsFavorite, awayTeam.isFavorite as awayTeamIsFavorite, homeTeam.name as homeTeamName, awayTeam.name as awayTeamName " +
                "from match_game " +
                "join team as homeTeam on match_game.homeTeamId = homeTeam.teamId " +
                "join team as awayTeam on match_game.awayTeamId = awayTeam.teamId " +
                "where match_game.competitionId = :competitionId and  match_game.status = \"FINISHED\""
    )
    fun competitionResults(competitionId: Int): LiveData<List<MatchDomain>>

    @Query(
        "select match_game.matchId, match_game.homeTeamId, match_game.awayTeamId, match_game.homeTeamScore, match_game.awayTeamScore, match_game.date as matchDate, homeTeam.code as homeTeamCode, homeTeam.emblemUrl as homeTeamEmblemUrl, awayTeam.code as awayTeamCode, awayTeam.emblemUrl as awayTeamEmblemUrl, homeTeam.isFavorite as homeTeamIsFavorite, awayTeam.isFavorite as awayTeamIsFavorite, homeTeam.name as homeTeamName, awayTeam.name as awayTeamName " +
                "from match_game " +
                "join team as homeTeam on match_game.homeTeamId = homeTeam.teamId " +
                "join team as awayTeam on match_game.awayTeamId = awayTeam.teamId " +
                "where match_game.competitionId = :competitionId and  match_game.status != \"FINISHED\""
    )
    fun competitionFixtures(competitionId: Int): LiveData<List<MatchDomain>>

    @Query(
        "select match_game.matchId, match_game.homeTeamId, match_game.awayTeamId, match_game.homeTeamScore, match_game.awayTeamScore, match_game.date as matchDate, homeTeam.code as homeTeamCode, homeTeam.emblemUrl as homeTeamEmblemUrl, awayTeam.code as awayTeamCode, awayTeam.emblemUrl as awayTeamEmblemUrl, competition.code as competitionCode, competition.emblemUrl as competitionEmblemUrl, homeTeam.isFavorite as homeTeamIsFavorite, awayTeam.isFavorite as awayTeamIsFavorite, homeTeam.name as homeTeamName, awayTeam.name as awayTeamName " +
                "from match_game " +
                "join team as homeTeam on match_game.homeTeamId = homeTeam.teamId " +
                "join team as awayTeam on match_game.awayTeamId = awayTeam.teamId " +
                "join competition on match_game.competitionId = competition.competitionId " +
                "where match_game.date like :date"
    )
    fun matchesOnTheDate(date: String): LiveData<List<MatchDomain>>

}