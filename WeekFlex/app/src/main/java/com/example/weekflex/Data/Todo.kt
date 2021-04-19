package com.example.weekflex.Data

data class Todo(
    var todoTitle: String,
    var todoCategory: String,
    var todoDates: ArrayList<String>,
    var todoStart: String,
    var todoEnd: String
)
