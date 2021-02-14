package com.example.weekflex.Activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.example.weekflex.R


class InsertRoutineNameActivity : AppCompatActivity() {
    private lateinit var backBtn : Button
    private lateinit var routineNameView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_routine_name)
        initView()
        setListener()
    }

    private fun initView(){
        backBtn = findViewById(R.id.back_insertRoutineName)
        routineNameView= findViewById(R.id.insertRoutineName)
        routineNameView.imeOptions = EditorInfo.IME_ACTION_DONE

    }

    private fun setListener(){
        backBtn.setOnClickListener {
            finish()
        }
        // 키보드 enter --> 완료로 바꾸고 다음 액티비티로 보내기
        routineNameView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val intent = Intent(this@InsertRoutineNameActivity,CompleteMakeRoutineActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                }
            }
            true
        })

    }


}