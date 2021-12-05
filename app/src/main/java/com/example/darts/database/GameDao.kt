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

//    @Query("INSERT INTO darts_games_table(starting_points, name, start_time) VALUES (:startingPoints, :name, :startTime);")
//    fun insertGame(startingPoints: Int, name: String, startTime: Long): Long

//    @Transaction
//    @Query("SELECT * FROM darts_games_table")
//    fun getAllWithPlayers(): LiveData<List<GameWithPlayers>>
}