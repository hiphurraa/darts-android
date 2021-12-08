package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.darts.database.entities.AppSettings
import com.example.darts.database.entities.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM darts_players_table")
    fun getAll(): List<Player>

    @Query("INSERT INTO darts_players_table(name, defaultSelected) VALUES (:name, :defaultSelected) ")
    fun insertPlayer(name: String, defaultSelected: Boolean)

    @Query("UPDATE darts_players_table SET defaultSelected = :defaultSelected WHERE id = :id")
    fun ChangeDefaultSelected(id: Long, defaultSelected: Boolean)

    @Query("SELECT DISTINCT darts_players_table.name FROM darts_players_table INNER JOIN darts_toss_table ON darts_toss_table.playerId == darts_players_table.id WHERE darts_toss_table.gameId = :gameId ")
    fun getPlayersInGame(gameId: Long): List<String>

}