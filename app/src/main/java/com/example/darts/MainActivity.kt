package com.example.darts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        var isInGame: Boolean = false
        lateinit var playerDao: PlayerDao
        lateinit var appSettingsDao: AppSettingsDao
    }

    private lateinit var database: DartsDatabase
    //private lateinit var playerDao: PlayerDao
    //private lateinit var appSettingsDao: AppSettingsDao

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
        GlobalScope.launch(context = Dispatchers.Default) {
            val settings = appSettingsDao.getSettings()
            if(settings == null) {
                appSettingsDao.insertSettings("FI", false)
            }
        }

    }


    /** Disable the back button when in game */
    override fun onBackPressed() {
        if (!isInGame) super.onBackPressed()
    }
}