package com.example.darts.gamescreen

import java.io.Serializable

data class Settings (var startingPoints: Int, var startsWithDouble: Boolean, var players: MutableList<Player>): Serializable {

    init {
        require(startingPoints == 501 || startingPoints == 301) { "Starting points value is invalid" }
        require(players.isNotEmpty()) { "List of player ID:s is empty" }
    }
}