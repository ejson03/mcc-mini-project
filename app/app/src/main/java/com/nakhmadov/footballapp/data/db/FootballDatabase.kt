package com.nakhmadov.footballapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nakhmadov.footballapp.data.db.daos.*
import com.nakhmadov.footballapp.data.db.models.*

@Database(
    entities = [Competition::class, Team::class,
        Match::class, CompetitionTableItem::class, Player::class],
    version = 1
)
abstract class FootballDatabase : RoomDatabase() {
    abstract val competitionDao: CompetitionDao
    abstract val teamDao: TeamDao
    abstract val matchDao: MatchDao
    abstract val competitionTableDao: CompetitionTableDao
    abstract val playerDao: PlayerDao
}

private lateinit var INSTANCE: FootballDatabase

fun getDatabase(context: Context): FootballDatabase {

    synchronized(FootballDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FootballDatabase::class.java,
                "FootballDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}