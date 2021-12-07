package com.example.darts.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.darts.database.entities.AppSettings

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM darts_app_settings_table")
    fun getSettings(): LiveData<AppSettings>

    @Query("SELECT * FROM darts_app_settings_table")
    fun getSettingsSync(): AppSettings?

    @Insert
    fun insertSettings(appSettings: AppSettings)

    @Update
    fun updateAppSettings(vararg settings: AppSettings)
}