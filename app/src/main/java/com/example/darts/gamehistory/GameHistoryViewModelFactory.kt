package com.example.darts.gamehistory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darts.database.DartsDatabase
import com.example.darts.database.GameDao
import com.example.darts.gamesettings.GameSettingsViewModel
import java.lang.IllegalArgumentException
import javax.sql.CommonDataSource

class GameHistoryViewModelFactory(
    private val dataSource: GameDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameHistoryViewModel::class.java)) {
            return GameHistoryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}


/*

class GameSettingsViewModelFactory(
    private val dataSource: AppSettingsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameSettingsViewModel::class.java)) {
            return GameSettingsViewModel(dataSource) as T
        }
        throw  IllegalArgumentException("Unkown ViewModel class")
    }
}

 */