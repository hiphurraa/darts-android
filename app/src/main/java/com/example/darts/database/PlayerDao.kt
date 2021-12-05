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

}