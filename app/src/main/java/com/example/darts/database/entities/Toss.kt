package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "darts_toss_table")
data class Toss (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name="gameId")
    var gameId: Long?,

    @ColumnInfo(name="playerId")
    var playerId: Long?,

    @ColumnInfo(name="points")
    var points: Int?,

    @ColumnInfo(name="orderNumber")
    var orderNumber: Int?
)