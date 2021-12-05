package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "darts_games_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "start_time")
    var startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "starting_points")
    var startingPoints: Int,

    @ColumnInfo(name = "name")
    var name: String
)