package com.example.darts.gamehistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.darts.database.GameDao
import com.example.darts.database.entities.Game
import com.example.darts.database.relations.GameWithPlayers

class GameHistoryViewModel(dataSource: GameDao): ViewModel() {
    val gameDatabase = dataSource

    private val allGames: LiveData<List<Game>>
    fun getAllGames() = allGames

    init {
        allGames = gameDatabase.getAll()
    }


}

/*


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

 */