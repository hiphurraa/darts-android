package com.example.darts.gamescreen

import android.content.Context
import com.example.darts.database.DartsDatabase
import com.example.darts.database.entities.Game as GameEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Game (context: Context, val settings: Settings) {

    private var gameId: Long = -1 // This games id in the database

    private var iTurn = 0 // Index of which turn it is, turn has 3 tosses
    var iToss: Int = 0 // Index of 0-2, represents which of the 3 tosses of turn it is right now
    private var iPlayer: Int = 0 // Index of which players turn it is
    var orderNumber: Int = 0 // Increases +1 on every toss, needed for database

    private val players: MutableList<Player> = settings.players
    private val turns: MutableList<Turn> = mutableListOf(Turn(players[iPlayer]))

    var previewState = false // When true, waits for ok button or undo from user

    private val database = DartsDatabase.getInstance(context)
    private val gameDao = database.gameDao()
    private val tossDao = database.tossDao()



    /** Starts a new game and saves it to database */
    fun start(name: String) {
        val gameEntity = GameEntity(0, System.currentTimeMillis(), settings.startingPoints, name)
        players.forEach {
            it.pointsLeft = settings.startingPoints
            it.doubleRequired = settings.startsWithDouble
        }

        GlobalScope.launch {
            gameId = gameDao.insertGame(gameEntity)
            iToss = 0
        }
    }



    /** Adds new toss to game instance and database and handles the game logic */
    fun newToss(points: Int, factor: Int) {
        if (iToss == 2) previewState = true

        /** 'Starts with double' rule implementation */
        if (players[iPlayer].doubleRequired && factor != 2) {
            players[iPlayer].toss(tossDao, gameId, 0, 1, orderNumber)
            turns[iTurn].tosses[iToss] = Toss(0, 1, false)
        } else {
            players[iPlayer].toss(tossDao, gameId, points, factor, orderNumber)
            turns[iTurn].tosses[iToss] = Toss(points, factor, players[iPlayer].doubleRequired)
            players[iPlayer].doubleRequired = false
        }

        /** Change the toss and/or turn */
        if (iToss < 2) iToss += 1
        else {
            iToss = 0
            if (iPlayer == players.size-1) iPlayer = 0
            else iPlayer += 1
            turns.add(Turn(players[iPlayer]))
            iTurn += 1
        }

        orderNumber += 1
    }



    /** Deletes the previous toss from database and the game instance and handles the game logic */
    fun cancelPreviousToss() {
        orderNumber -= 1

        /** Reverse the toss and/or turn */
        if (iToss > 0) iToss -= 1
        else {
            turns.removeAt(iTurn)
            iTurn -= 1
            iToss = 2
            if (iPlayer == 0) iPlayer = players.size-1
            else iPlayer -= 1
        }

        /** Delete the previous toss from player */
        var previousTotalPoints = 0
        if (turns[iTurn].tosses[iToss] != null) {
            previousTotalPoints = (turns[iTurn].tosses[iToss]!!.points * turns[iTurn].tosses[iToss]!!.factor)
        }
        players[iPlayer].cancelToss(tossDao, gameId, orderNumber, previousTotalPoints)
        if (turns[iTurn].tosses[iToss]!!.isFirstDouble) players[iPlayer].doubleRequired = true
        turns[iTurn].tosses[iToss] = null
    }


    fun getCurrentPlayer(): Player {
        return players[iPlayer]
    }


    fun getCurrentTurn (): Turn {
        return turns[iTurn]
    }


    fun getPreviousTurn(): Turn {
        return if (iTurn != 0) turns[iTurn-1]
        else turns[iTurn]
    }


}