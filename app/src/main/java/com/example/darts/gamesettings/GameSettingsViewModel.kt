package com.example.darts.gamesettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.entities.AppSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameSettingsViewModel(
    dataSource: AppSettingsDao) : ViewModel() {

        val database = dataSource

        private val appSettings: LiveData<AppSettings>
        fun getAppSettings() = appSettings

        init {
            appSettings = database.getSettings()
        }

    fun onEnglish() {
        GlobalScope.launch {
            database.updateAppSettings(AppSettings(appSettings.value?.id!!, "ENG", appSettings.value?.speedEntryEnabled))
        }
    }

    fun onFinnish() {
        GlobalScope.launch {
            database.updateAppSettings(AppSettings(appSettings.value?.id!!, "FI", appSettings.value?.speedEntryEnabled))
        }
    }

    fun onSpeedEntryEnabled() {
        GlobalScope.launch {
            database.updateAppSettings(AppSettings(appSettings.value?.id!!, appSettings.value?.language, true))
        }
    }

    fun onSpeedEntryDisabled() {
        GlobalScope.launch {
            database.updateAppSettings(AppSettings(appSettings.value?.id!!, appSettings.value?.language, false))
        }
    }


}