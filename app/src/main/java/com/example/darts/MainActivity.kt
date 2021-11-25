package com.example.darts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        var isInGame: Boolean = false
    }

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disable dark mode for now
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        database = DartsDatabase.getInstance(applicationContext)
        playerDao = database.playerDao()
        //exampleInsert()
    }

    fun exampleInsert() {
        GlobalScope.launch(context = Dispatchers.Default) {
            playerDao.insertPlayer("Lauri", false)

            val players = playerDao.getAll()

            players.forEach {
                d("lauhyv", it.name)
                d("lauhyv", it.id.toString())
                d("lauhyv", it.defaultSelected.toString())
            }
        }
    }

    /** Disable the back button when in game */
    override fun onBackPressed() {
        if (!isInGame) super.onBackPressed()
    }
}