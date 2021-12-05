package com.example.darts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.darts.database.entities.*

@Database(entities = [Player::class, Game::class, Toss::class, AppSettings::class], version = 1)
abstract class DartsDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
    abstract fun tossDao(): TossDao
    abstract  fun appSettingsDao(): AppSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: DartsDatabase? = null

        fun getInstance(context: Context): DartsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DartsDatabase::class.java,
                        "darts_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}