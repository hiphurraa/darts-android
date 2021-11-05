package com.example.darts.database

import androidx.room.Dao
import androidx.room.Query
import com.example.darts.database.entities.Throw

@Dao
interface ThrowDao {
    @Query("SELECT * FROM darts_throws_table")
    fun getAll(): List<Throw>
}