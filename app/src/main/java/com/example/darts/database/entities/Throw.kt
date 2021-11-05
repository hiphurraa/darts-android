package com.example.darts.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["playerId", "gameId"], tableName = "darts_throws_table")
data class Throw (
    val playerId: Long,
    val gameId: Long,
    val points: Int?,
    val multiplier: Int?,
    val time: Long = System.currentTimeMillis()
)

