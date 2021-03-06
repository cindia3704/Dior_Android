package com.example.weekflex.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.weekflex.R

class InsertRoutineNameActivity : BaseActivity() {
    private lateinit var backBtn: Button
    private lateinit var routineNameView: EditText
    private lateinit var checkImg: ImageView
    private lateinit var routineNameCommentView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_routine_name)
        initView()
        setListener()
    }

    private fun initView() {
        backBtn = findViewById(R.id.back_insertRoutineName)
        routineNameView = findViewById(R.id.insertRoutineName)
        routineNameView.imeOptions = EditorInfo.IME_ACTION_DONE
        checkImg = findViewById(R.id.checkImg_insertRoutineName)
        routineNameCommentView = findViewById(R.id.addRoutineName_ment)
        if (intent.hasExtra("name")) {
            routineNameView.setText(intent.getStringExtra("name"))
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun setListener() {
        backBtn.setOnClickListener {
            hideKeyboard()
            finish()
        }

        // 루틴 이름 입력시 코멘트 바꾸기 & 상단 체크 표시 바꾸기
        routineNameView.doAfterTextChanged { s ->
            if (s.isNullOrEmpty() || s.length <2) {
                checkImg.setImageResource(R.drawable.uncheck)
                routineNameCommentView.setText("루틴 이름을 입력해주세요")
            } else {
                checkImg.setImageResource(R.drawable.check)
                routineNameCommentView.setText("멋진 이름이에요! *-*")
            }
        }

        checkImg.setOnClickListener {
            hideKeyboard()
                    val intent = Intent(this@InsertRoutineNameActivity, CompleteMakeRoutineActivity::class.java)
                    intent.putExtra("name", "" + routineNameView.text.toString())
                    navigateWithFinish(intent)
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(routineNameView.windowToken, 0)
    }
}
