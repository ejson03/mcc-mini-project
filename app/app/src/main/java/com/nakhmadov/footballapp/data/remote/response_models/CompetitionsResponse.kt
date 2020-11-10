package com.nakhmadov.footballapp.data.remote.response_models

import com.nakhmadov.footballapp.data.db.models.Competition
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompetitionListResponse(
    val count: Int = 0,
    val competitions: List<CompetitionDetailResponse>? = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class CompetitionDetailResponse(
    val id: Int = 0,
    val area: AreaDetailResponse = AreaDetailResponse(),
    val name: String = "",
    val code: String? = "",
    val emblemUrl: String? = "",
    val currentSeason: CurrentSeasonDetailResponse? = CurrentSeasonDetailResponse()
)

@JsonClass(generateAdapter = true)
data class AreaDetailResponse(
    val id: Int = 0,
    val name: String = ""
)

@JsonClass(generateAdapter = true)
data class CurrentSeasonDetailResponse(
    val id: Int = 0,
    val startDate: String? = "",
    val endDate: String? = "",
    val currentMatchday: Int? = 0
)

fun CompetitionListResponse.asDatabaseModel(): List<Competition> {
    val result = mutableListOf<Competition>()

    this.competitions?.forEach {
        val competition = Competition(
            competitionId = it.id,
            areaId = it.area.id,
            area = it.area.name,
            title = it.name,
            emblemUrl = it.emblemUrl,
            code = it.code,
            startDate = it.currentSeason?.startDate,
            endDate = it.currentSeason?.endDate,
            currentMatchday = it.currentSeason?.currentMatchday
        )
        result.add(competition)
    }

    return result
}