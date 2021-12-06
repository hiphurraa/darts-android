package com.example.darts.gamescreen

import android.content.Context
import com.example.darts.database.DartsDatabase
import com.example.darts.database.entities.Game as GameEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Game (context: Context, val settings: Settings) {

    private val players: MutableList<Player> = settings.players
    private var gameId: Long = -1

    private var iCurrentPlayer: Int = 0 // Index of which players turn it is
    var turnStatus: Int = -1 // Represents which of the 3 tosses of turn is right now
    private var orderNumber: Int = 0 // Increases +1 on every toss

    private val database = DartsDatabase.getInstance(context)
    private val gameDao = database.gameDao()
    private val tossDao = database.tossDao()


    fun start(name: String) {
        val gameEntity: GameEntity = GameEntity(0, System.currentTimeMillis(), settings.startingPoints, name)

        GlobalScope.launch {
            gameId = gameDao.insertGame(gameEntity)
            turnStatus = 0
        }
    }


    fun newToss(points: Int) {
        players[iCurrentPlayer].toss(tossDao, gameId, points, orderNumber)

        if (turnStatus < 2) turnStatus += 1
        else {
            turnStatus = 0
            if (iCurrentPlayer == players.size-1) iCurrentPlayer = 0
            else iCurrentPlayer += 1
        }

        orderNumber += 1
    }


    fun cancelPreviousToss() {
        orderNumber -= 1

        if (turnStatus > 0) turnStatus -= 1
        else {
            turnStatus = 2
            if (iCurrentPlayer == 0) iCurrentPlayer = players.size-1
            else iCurrentPlayer -= 1
        }

        players[iCurrentPlayer].cancelToss(tossDao, gameId, orderNumber)
    }


}