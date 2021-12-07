package com.example.darts.gamescreen

import com.example.darts.database.entities.Toss as TossEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Player (val playerId: Long, val name: String) {

    var pointsLeft: Int = -1
    var doubleRequired: Boolean = true
    var latestTurn: Turn? = null
    var iToss: Int = 0 // Index of 0-2, represents which of the 3 tosses of turn it is right now


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