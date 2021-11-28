package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="darts_app_settings_table")
data class AppSettings(
    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo(name = "language")
    val language: String = "FI",

    @ColumnInfo(name = "points_speed_entry")
    val speedEntryEnabled: Boolean = false
)