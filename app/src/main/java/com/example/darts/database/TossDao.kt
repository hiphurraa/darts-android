package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.darts.database.entities.Toss

@Dao
interface TossDao {
    @Query("SELECT * FROM darts_toss_table")
    fun getAll(): LiveData<List<Toss>>

    @Insert
    fun insertToss(toss: Toss): Long

    @Query("DELETE FROM darts_toss_table WHERE gameId = :gameId AND orderNumber = :orderNumber;")
    fun deleteToss(gameId: Long, orderNumber: Int)

    @Query("SELECT points FROM darts_toss_table WHERE gameId = :gameId AND orderNumber = :orderNumber;")
    fun getPoints(gameId: Long, orderNumber: Int): Int

    @Query("SELECT * FROM darts_toss_table WHERE gameId = :gameId AND playerId = :playerId")
    fun getTossesFromPlayerInGame(playerId: Long, gameId: Long): List<Toss>
}