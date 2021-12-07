package com.example.darts.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="darts_app_settings_table")
data class AppSettings(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "language")
    var language: String? = "FI",

    @ColumnInfo(name = "points_speed_entry")
    var speedEntryEnabled: Boolean? = false
)