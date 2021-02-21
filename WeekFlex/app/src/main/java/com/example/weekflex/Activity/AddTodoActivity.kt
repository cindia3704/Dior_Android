package com.example.weekflex.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.weekflex.Fragment.BottomSheetFragment
import com.example.weekflex.R



class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val btn_categoryChoose = findViewById<Button>(R.id.btn_categoryChoose)

        btn_categoryChoose.setOnClickListener {
            val bottomSheet=BottomSheetFragment()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }




    }
}