package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.darts.database.entities.Game
import com.example.darts.database.relations.GameWithPlayers

@Dao
interface GameDao {
    @Query("SELECT * FROM darts_games_table")
    fun getAll(): LiveData<List<Game>>

//    @Transaction
//    @Query("SELECT * FROM darts_games_table")
//    fun getAllWithPlayers(): LiveData<List<GameWithPlayers>>
}