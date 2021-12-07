package com.example.darts.gamescreen

import com.example.darts.database.entities.Toss as TossEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Player (val playerId: Long, val name: String) {

    companion object {
        val OK = "OK"
        val BUST = "BUST"
        val WINNER = "WINNER"
    }

    var pointsLeft: Int = -1
    var doubleRequired: Boolean = true
    var latestTurn: Turn? = null
    var iToss: Int = 0 // Index of 0-2, represents which of the 3 tosses of turn it is right now
    var isCurrentPlayer: Boolean = false


    /** Saves the toss to database and decreases the points left */
    fun toss(game: Game, toss: Toss) {

        when (iToss) {
            0 -> iToss = 1
            1 -> iToss = 2
            2 -> iToss = 0
        }

        pointsLeft -= (toss.points * toss.factor)

        /** Save the toss to database */
        GlobalScope.launch {
            val tossEntity = TossEntity(0, game.gameId, playerId, toss.points, game.orderNumber)
            game.tossDao.insertToss(tossEntity)
        }
    }



    fun checkToss(toss: Toss): String {
        /** To win, points must go to exactly 0 and the last dart must hit a double */
        if (pointsLeft - (toss.points * toss.factor) == 0 && toss.factor != 2) {
            return BUST
        }
        /** Winner */
        else if (pointsLeft - (toss.points * toss.factor) == 0 && toss.factor == 2){
            return WINNER
        }
        /** If points go under 2, it's a bust */
        else if (pointsLeft - (toss.points * toss.factor) < 2) {
            return BUST
        }
        /** Normal turn */
        else {
            return OK
        }
    }



    /** Deletes the toss from database and cancels the decreasement of the points */
    fun cancelToss(game: Game, toss: Toss) {

        pointsLeft += (toss.points * toss.factor)
        if (toss.isFirstDouble) doubleRequired = true

        when (iToss) {
            2 -> iToss = 1
            1 -> iToss = 0
            0 -> iToss = 2
        }

        GlobalScope.launch {
            /** Delete toss from db */
            game.tossDao.deleteToss(game.gameId, game.orderNumber)
        }
    }
}