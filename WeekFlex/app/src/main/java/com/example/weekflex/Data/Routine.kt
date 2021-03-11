package com.example.weekflex.Data

import com.example.weekflex.Activity.routineList
import com.example.weekflex.R
import java.util.*

data class Routine (
    val routineTitle: String,
    val routineItemList:List<RoutineItem> = emptyList()
)

val categoryToStarImage = mapOf<Int, Int>(
        0 to R.drawable.graystar,
        1 to R.drawable.bluestar,
        2 to R.drawable.yellowstar,
        3 to R.drawable.pinkstar
)