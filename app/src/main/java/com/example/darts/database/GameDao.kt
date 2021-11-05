package com.example.darts.database

import androidx.room.Dao
import androidx.room.Query
import com.example.darts.database.entities.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM darts_games_table")
    fun getAll(): List<Game>
}