package com.nakhmadov.footballapp.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
data class Team(
    @PrimaryKey
    val teamId: Int = 0,
    val competitionId: Int = 0,
    val name: String = "",
    val shortName: String = "",
    val code: String = "",
    val emblemUrl: String? = "",
    val website: String? = "",
    val founded: Int? = 0,
    val clubColors: String? = "",
    val stadium: String? = "",
    var isFavorite: Boolean = false
)