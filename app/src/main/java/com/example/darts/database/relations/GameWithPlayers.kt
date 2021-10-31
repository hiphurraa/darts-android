package com.example.darts.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.darts.database.entities.Game
import com.example.darts.database.entities.Player
import com.example.darts.database.entities.Throw

data class GameWithPlayers(
    @Embedded val game: Game,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "playerId",
        associateBy = Junction(Throw::class)
    )
    val players: List<Player>
)