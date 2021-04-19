package com.example.weekflex.Data

import java.io.Serializable

// TODO 용어 맞춰야함
data class Task(
    val taskTitle: String,
    val category: Int,
    val startTime: String,
    val endTime: String,
    var bookMarked: Boolean,
    val weekdaysScheduled: List<String>
) : Serializable
