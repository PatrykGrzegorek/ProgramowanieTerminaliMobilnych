package com.example.myfirstapp

import java.util.*

class Dice(private val numSides: Int = 6) {
    fun roll(isRangesRandom: Boolean = true): Int{
        if(isRangesRandom){
            return (1..numSides).random()
        } else {
            return Random().nextInt(numSides) + 1
        }
    }
}