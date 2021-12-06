package com.example.darts.gamescreen

data class Turn (val player: Player){
    val tosses: MutableList<Toss?> = mutableListOf(null, null, null)
}