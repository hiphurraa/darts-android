package com.example.darts

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        var isInGame: Boolean = false
    }

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao
    private lateinit var appSettingsDao: AppSettingsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disable dark mode for now
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        database = DartsDatabase.getInstance(applicationContext)
        playerDao = database.playerDao()
        appSettingsDao = database.appSettingsDao()
        createDefaultSettingsIfNotExist()

    }

    fun createDefaultSettingsIfNotExist() {
        GlobalScope.launch {
            val settings = appSettingsDao.getSettingsSync()
            if (settings == null) {
                val defaultSettings = AppSettings(1L, "FI", false)
                appSettingsDao.insertSettings(defaultSettings)
            }
        }
    }


    /** Disable the back button when in game */
    override fun onBackPressed() {
        if (!isInGame) super.onBackPressed()
    }
}