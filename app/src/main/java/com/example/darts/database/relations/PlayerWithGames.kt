package com.example.darts.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.darts.database.entities.Game
import com.example.darts.database.entities.Player
import com.example.darts.database.entities.Throw

data class PlayerWithGames(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "gameId",
        associateBy = Junction(Throw::class)
    )
    val games: List<Game>
)