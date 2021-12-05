package com.example.darts

import android.os.Parcelable
import com.example.darts.database.entities.Player
import java.io.Serializable

data class InGameSettings (var startingPoints: Int, var startsWithDouble: Boolean, var selectedPlayers: MutableList<Player>): Serializable {

    init {
        require(startingPoints == 501 || startingPoints == 301) { "Starting points value is invalid" }
        require(selectedPlayers.isNotEmpty()) { "List of player ID:s is empty" }
    }
}