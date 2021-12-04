package com.example.darts.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darts.database.AppSettingsDao
import java.lang.IllegalArgumentException

class GameSettingsViewModelFactory(
    private val dataSource: AppSettingsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameSettingsViewModel::class.java)) {
            return GameSettingsViewModel(dataSource) as T
        }
        throw  IllegalArgumentException("Unkown ViewModel class")
    }
}