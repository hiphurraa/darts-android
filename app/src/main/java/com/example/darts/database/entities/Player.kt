package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "darts_players_table")
data class Player (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "default")
    val default: Boolean = false
    )