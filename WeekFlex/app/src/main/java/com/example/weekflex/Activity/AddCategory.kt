package com.example.weekflex.Activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_category.*


class AddCategory : AppCompatActivity(){

    private lateinit var insertedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        insertedCategory  = insertCategory.text.toString()


    }


}