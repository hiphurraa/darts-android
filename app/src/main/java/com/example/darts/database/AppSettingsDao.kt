package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.darts.database.entities.AppSettings

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM darts_app_settings_table WHERE id = 1")
    fun getSettings(): AppSettings

    @Query("INSERT INTO darts_app_settings_table(language, points_speed_entry) VALUES (:language, :speedEntry)")
    fun insertSettings(language: String, speedEntry: Boolean)

    @Update
    fun updateAppSettings(vararg settings: AppSettings)
}