package com.example.darts.gamehistory

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darts.database.GameDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.darts.database.entities.Game as GameEntity

class GameHistoryViewModel(dataSource: GameDao, application: Application): ViewModel() {
    val gameDatabase = dataSource

    val games = gameDatabase.getAll()

    fun onInsert() {
        GlobalScope.launch {
            gameDatabase.insertGame(GameEntity(0, System.currentTimeMillis(), 501, "Torstai tikka"))
        }
    }
}