package com.example.darts

import android.os.Parcelable
import java.io.Serializable

data class InGameSettings (var startingPoints: String, var startsWithDouble: Boolean, var playersIds: ArrayList<Int>): Serializable {

    init {
        require(startingPoints == "501" || startingPoints == "301") { "Starting points value is invalid" }
        require(playersIds.isNotEmpty()) { "List of player ID:s is empty" }
    }
}