package com.example.weekflex.Data

// TODO 용어 맞춰야함
data class RoutineItem(
    val routineItemTitle : String,
    val category : Int,
    val startTime : String,
    val endTime : String,
    val bookMarked:Boolean,
    val weekdaysScheduled:List<String> = emptyList()
)