package com.example.darts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.room.Room
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DartsDatabase.getInstance(applicationContext)
        playerDao = database.playerDao()
        exampleInsert()


        setContentView(R.layout.activity_main)
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
}