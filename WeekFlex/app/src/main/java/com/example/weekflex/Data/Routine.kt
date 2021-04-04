package com.example.weekflex.Data

import com.example.weekflex.R
import java.io.Serializable

data class Routine (
    var routineTitle: String,
    var taskList:List<Task> = emptyList()
):Serializable

val categoryToStarImage = mapOf<Int, Int>(
        0 to R.drawable.graystar,
        1 to R.drawable.bluestar,
        2 to R.drawable.yellowstar,
        3 to R.drawable.pinkstar
)