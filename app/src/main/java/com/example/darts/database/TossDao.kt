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
}