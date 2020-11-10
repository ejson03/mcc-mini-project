package com.nakhmadov.footballapp.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition")
data class Competition(
    @PrimaryKey
    val competitionId: Int = 0,
    val areaId: Int? = 0,
    var area: String? = "",
    val title: String = "",
    var emblemUrl: String? = "",
    val code: String? = "",
    var startDate: String? = "",
    var endDate: String? = "",
    var currentMatchday: Int? = 0,
    var isFavorite: Boolean = false
)