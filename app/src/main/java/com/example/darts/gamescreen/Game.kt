package com.example.darts.gamescreen

import android.content.Context
import com.example.darts.database.DartsDatabase
import com.example.darts.database.entities.Game as GameEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Game (context: Context, private val settings: Settings) {

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

    var isGameOver = false



    /** Starts a new game and saves it to database */
    fun start(name: String) {
        val gameEntity = GameEntity(0, System.currentTimeMillis(), settings.startingPoints, name)
        players.forEach {
            it.pointsLeft = settings.startingPoints
            it.doubleRequired = settings.startsWithDouble
        }
        players[iPlayer].isCurrentPlayer = true

        GlobalScope.launch {
            gameId = gameDao.insertGame(gameEntity)
        }
    }



    private fun gameOver(player: Player, winningToss: Toss) {
        player.toss(this, winningToss)
        isGameOver = true
    }



    fun newToss(points: Int, factor: Int) {
        val player = players[iPlayer]
        val turn = turns[iTurn]

        if (player.iToss == 2) previewState = true

        var newToss = Toss(points, factor, player.doubleRequired)
        if (factor == 2){
            player.doubleRequired = false
        }
        /** Player still need the double, set points to 0 */
        else if (player.doubleRequired) {
            newToss = Toss(0, factor, false)
        }

        val tossOutCome = player.checkToss(newToss)

        when (tossOutCome) {
            Player.WINNER -> gameOver(player, newToss)
            Player.BUST -> bust(player, turn)
            else -> {
                /** Normal toss */
                turn.tosses[player.iToss] = newToss
                player.toss(this, newToss)
                orderNumber += 1

                /** Change turn? */
                if (player.iToss == 0) {
                    /** Before changing turn save the current turn as latest */
                    player.latestTurn = turn

                    /** Go to next turn */
                    turns.add(Turn())
                    iTurn += 1

                    /** Update the iPlayer index */
                    if (iPlayer < players.size-1) iPlayer += 1
                    else iPlayer = 0

                    /** Set current player */
                    players.forEach { it.isCurrentPlayer = false }
                    players[iPlayer].isCurrentPlayer = true
                }
            }
        }
    }



    fun cancelPreviousToss() {
        val previousToss: Toss = getPreviousToss()!!
        orderNumber -= 1

        /** Previous toss was by previous player? -> Change turn back */
        if (players[iPlayer].iToss == 0) {
            /** Go to previous turn */
            turns.removeAt(iTurn)
            iTurn -= 1
            turns[iTurn].isBust = false

            /** Reverse the iPlayer index */
            if (iPlayer == 0) iPlayer = players.size-1
            else iPlayer -= 1

            players[iPlayer].cancelToss(this, previousToss)

            /** If full round done, change the latest turn to the one from previous round */
            if (iTurn > players.size-1) players[iPlayer].latestTurn = turns[iTurn - players.size]
            else players[iTurn].latestTurn = null
        }
        else {
            /** Previous toss was made by the current player, cancel normally */
            players[iPlayer].cancelToss(this, previousToss)
        }

        turns[iTurn].tosses[players[iPlayer].iToss] = null
    }



    private fun bust(player: Player, turn: Turn) {

        /** BUST, rest tosses are 0 points  */
        while (player.iToss != 2){
            turn.tosses[player.iToss] = Toss(0, 0, false)
            player.toss(this, Toss(0, 0, false))
            orderNumber += 1
        }
        turn.tosses[player.iToss] = Toss(0, 0, false)
        player.toss(this, Toss(0, 0, false))
        orderNumber += 1

        /** Go to next turn */
        turns.add(Turn())
        iTurn += 1

        /** Update the iPlayer index */
        if (iPlayer < players.size-1) iPlayer += 1
        else iPlayer = 0

        /** Set current player */
        players.forEach { it.isCurrentPlayer = false }
        players[iPlayer].isCurrentPlayer = true

        repeat(3) {
            cancelPreviousToss()
        }
        repeat(3) {
            newToss(0, 1)
        }
        getPreviousTurn().isBust = true
    }


    fun updatePlayerHighlight() {
        players.forEach {
            it.isCurrentPlayer = false
        }
        if (previewState && iPlayer == 0) {
            players[players.size - 1].isCurrentPlayer = true
        }
        else if (previewState) {
            players[iPlayer - 1].isCurrentPlayer = true
        }
        else {
            players[iPlayer].isCurrentPlayer = true
        }
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