package com.example.darts.gamescreen

import com.example.darts.database.TossDao
import com.example.darts.database.entities.Toss as TossEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Player (val playerId: Long, val name: String) {

    var score: Int = -1
    var doubleRequired: Boolean = true

    fun toss(tossDao: TossDao, gameId: Long, points: Int, factor: Int, orderNumber: Int) {
        GlobalScope.launch {
            val newToss = TossEntity(0, gameId, playerId, points, orderNumber)
            tossDao.insertToss(newToss)
        }

        score -= (points * factor)
    }

    fun cancelToss(tossDao: TossDao, gameId: Long, orderNumber: Int, totalPoints: Int) {
        GlobalScope.launch {
            tossDao.deleteToss(gameId, orderNumber)
        }

        score += totalPoints
    }
}