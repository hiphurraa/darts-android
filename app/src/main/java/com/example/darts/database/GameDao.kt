package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.darts.database.entities.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM darts_games_table")
    fun getAll(): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game): Long
}