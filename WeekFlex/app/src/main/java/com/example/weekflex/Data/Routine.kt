package com.example.weekflex.Data

import com.example.weekflex.R
import java.io.Serializable

data class Routine(
    var routineTitle: String,
    var taskList: List<Task> = emptyList()
) : Serializable

val categoryToStarImage = mapOf<Int, Int>(
        0 to R.drawable.graystar,
        1 to R.drawable.bluestar,
        2 to R.drawable.yellowstar,
        3 to R.drawable.pinkstar
)

val categoryColors = mapOf <Int,Int>(
    1 to R.color.color1,
    2 to R.color.color2,
    3 to R.color.color3,
    4 to R.color.color4,
    5 to R.color.color5,
    6 to R.color.color06,
    7 to R.color.color7,
    8 to R.color.color8,
    9 to R.color.color9,
    10 to R.color.color10,
    11 to R.color.color11,
    12 to R.color.color12,
    13 to R.color.color13,
    14 to R.color.color14,
    15 to R.color.color15
)