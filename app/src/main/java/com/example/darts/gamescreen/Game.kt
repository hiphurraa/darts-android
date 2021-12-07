package com.example.darts.gamescreen

import android.content.Context
import com.example.darts.database.DartsDatabase
import com.example.darts.database.entities.Game as GameEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Game (context: Context, val settings: Settings) {

    var gameId: Long = -1 // This games id in the database

    private var iTurn = 0 // Index of which turn it is, turn has 3 tosses
    private var iPlayer: Int = 0 // Index of which players turn it is
    var orderNumber: Int = 0 // Increases +1 on every toss, needed for database

    private val players: MutableList<Player> = settings.players
    private val turns: MutableList<Turn> = mutableListOf(Turn())

    var previewState = false // When true, waits for ok button or undo from user

    private val database = DartsDatabase.getInstance(context)
    private val gameDao = database.gameDao()
    val tossDao = database.tossDao()



    /** Starts a new game and saves it to database */
    fun start(name: String) {
        val gameEntity = GameEntity(0, System.currentTimeMillis(), settings.startingPoints, name)
        players.forEach {
            it.pointsLeft = settings.startingPoints
            it.doubleRequired = settings.startsWithDouble
        }

        GlobalScope.launch {
            gameId = gameDao.insertGame(gameEntity)
        }
    }



    fun newToss(points: Int, factor: Int) {
        if (players[iPlayer].iToss == 2) previewState = true

        var newToss = Toss(points, factor, players[iPlayer].doubleRequired)
        if (factor == 2){
            players[iPlayer].doubleRequired = false
        }
        /** Player still need the double, set points to 0 */
        else if (players[iPlayer].doubleRequired) {
            newToss = Toss(0, factor, false)
        }

        turns[iTurn].tosses[players[iPlayer].iToss] = newToss
        players[iPlayer].toss(this, newToss)

        if (players[iPlayer].iToss == 0) {
            /** Before changing turn save the current turn as latest */
            players[iPlayer].latestTurn = turns[iTurn]

            /** Go to next turn */
            turns.add(Turn())
            iTurn += 1

            /** Update the iPlayer index */
            if (iPlayer < players.size-1) iPlayer += 1
            else iPlayer = 0
        }

        orderNumber += 1
    }

    private fun getPreviousToss(): Toss? {
        var turnIndex = iTurn
        var playerIndex = iPlayer

        /** Simulate the index logic */
        if (players[playerIndex].iToss == 0){
            turnIndex -= 1
            if (playerIndex == 0) playerIndex = players.size-1
            else playerIndex -= 1
            return turns[turnIndex].tosses[2]
        }
        else {
            return turns[iTurn].tosses[players[this.iPlayer].iToss-1]
        }
    }


    fun cancelPreviousToss() {
        val previousToss: Toss = getPreviousToss()!!
        orderNumber -= 1

        if (players[iPlayer].iToss == 0) {
            /** Go to previous turn */
            turns.removeAt(iTurn)
            iTurn -= 1

            /** Update the iPlayer index */
            if (iPlayer == 0) iPlayer = players.size-1
            else iPlayer -= 1

            players[iPlayer].cancelToss(this, previousToss)

            /** If full round done, change the latest turn the one from previous round */
            if (iTurn > players.size-1) players[iPlayer].latestTurn = turns[iTurn - players.size]
            else players[iTurn].latestTurn = null
        }
        else {
            players[iPlayer].cancelToss(this, previousToss)
        }
        turns[iTurn].tosses[players[iPlayer].iToss] = null
    }


    fun getCurrentPlayer(): Player {
        return players[iPlayer]
    }

    fun getPreviousPlayer(): Player {
        return if (iPlayer == 0) players[players.size-1]
        else players[iPlayer-1]
    }


    fun getCurrentTurn (): Turn {
        return turns[iTurn]
    }


    fun getPreviousTurn(): Turn {
        return if (iTurn != 0) turns[iTurn-1]
        else turns[iTurn]
    }


}