package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "darts_games_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "start_time")
    val startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "starting_points")
    val startingPoints: Int?,

    @ColumnInfo(name = "name")
    val name: String?
)