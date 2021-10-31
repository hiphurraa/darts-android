package com.example.darts.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["player_id", "game_id"])
data class Throw (
    val playerId: Long,
    val gameId: Long,
    val points: Int?,
    val multiplier: Int?,
    val time: Long = System.currentTimeMillis()
)

