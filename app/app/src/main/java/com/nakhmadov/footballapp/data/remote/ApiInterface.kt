package com.nakhmadov.footballapp.data.remote

import com.nakhmadov.footballapp.data.remote.response_models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("competitions")
    fun fetchCompetitionsAsync(): Deferred<CompetitionListResponse>

    @GET("competitions/{competitionId}/matches")
    fun fetchCompetitionMatchesAsync(@Path("competitionId") competitionId: Int): Deferred<CompetitionMatchesResponse>

    @GET("competitions/{competitionId}/teams")
    fun fetchCompetitionTeamsAsync(@Path("competitionId") competitionId: Int): Deferred<CompetitionTeamsResponse>

    @GET("competitions/{competitionId}/standings")
    fun fetchCompetitionTableAsync(@Path("competitionId") competitionId: Int): Deferred<CompetitionTableResponse>

    @GET("competitions/{competitionId}/scorers")
    fun fetchCompetitionScorersAsync(
        @Path("competitionId") competitionId: Int,
        @Query("limit") limit: Int = 1000
    ): Deferred<CompetitionScorersResponse>

    @GET("teams/{teamId}")
    fun fetchTeamSquadAsync(@Path("teamId") teamId: Int): Deferred<TeamSquadResponse>

    @GET("teams/{teamId}/matches")
    fun fetchTeamMatchesAsync(
        @Path("teamId") teamId: Int,
        @Query("dateFrom") dateFrom: String = "2019-08-01",
        @Query("dateTo") dateTo: String = "2020-07-30",
        @Query("limit") limit: Int = 1000
    ): Deferred<TeamMatchesResponse>

    @GET("matches")
    fun fetchDateMatchesAsync(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String = dateFrom
    ): Deferred<MatchesResponse>

}