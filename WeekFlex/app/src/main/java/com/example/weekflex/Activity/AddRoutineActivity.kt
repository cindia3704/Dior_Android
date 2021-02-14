package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.weekflex.Fragment.TodoMainFragment
import com.example.weekflex.R

class AddRoutineActivity : AppCompatActivity() {
    private lateinit var backBtn:Button
    private lateinit var introMentView:TextView
    private lateinit var makeRoutineBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)
        initView()
        setListener()
    }

    private fun initView(){
        backBtn= findViewById(R.id.back_addRoutine)
        introMentView = findViewById(R.id.ment_addRoutine)
        makeRoutineBtn = findViewById(R.id.makeRoutine_addRoutine)
    }
    private fun setListener(){
        backBtn.setOnClickListener {
            finish()
        }

        makeRoutineBtn.setOnClickListener {
            val intent = Intent(this@AddRoutineActivity,InsertRoutineNameActivity::class.java)
            startActivity(intent)
        }
    }
}