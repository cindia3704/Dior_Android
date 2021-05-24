package com.example.weekflex.Data

import com.example.weekflex.R
import java.io.Serializable

data class Routine(
    var routineTitle: String,
    var taskList: List<Task> = emptyList()
) : Serializable

val categoryToStarImage = mapOf<Int, Int>(
        0 to R.drawable.graystar,
        1 to R.drawable.pinkstar,
        2 to R.drawable.yellowstar,
        3 to R.drawable.yellowstar,
        12 to R.drawable.bluestar
)

val categoryColors = mapOf <Int,Int>(
    1 to R.drawable.color1_circle,
    2 to R.drawable.color2_circle,
    3 to R.drawable.color3_circle,
    4 to R.drawable.color4_circle,
    5 to R.drawable.color5_circle,
    6 to R.drawable.color6_circle,
    7 to R.drawable.color7_circle,
    8 to R.drawable.color8_circle,
    9 to R.drawable.color9_circle,
    10 to R.drawable.color10_circle,
    11 to R.drawable.color11_circle,
    12 to R.drawable.color12_circle,
    13 to R.drawable.color13_circle,
    14 to R.drawable.color14_circle,
    15 to R.drawable.color15_circle
)