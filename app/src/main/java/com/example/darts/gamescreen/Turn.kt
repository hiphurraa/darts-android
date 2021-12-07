package com.example.darts.gamescreen

data class Turn (val tosses: MutableList<Toss?> = mutableListOf(null, null, null)){
    var isBust = false
}