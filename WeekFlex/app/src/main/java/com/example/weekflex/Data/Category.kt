package com.example.weekflex.Data

data class Category(
    var categoryId:Int,
    var categoryName : String,
    var categoryColor: Int,
    var routineItemList: List<RoutineItem> = emptyList()
)