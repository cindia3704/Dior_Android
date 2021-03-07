package com.example.weekflex.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.weekflex.R

class CompleteMakeRoutineActivity : AppCompatActivity() {
    private lateinit var gobackBtn:ImageView
    private lateinit var nextBtn:TextView
    private lateinit var nameOfRoutine:TextView
    private lateinit var searchCategoryView: EditText
    private lateinit var searchIconImg : ImageView
    private lateinit var deleteSearchBtn: ImageView
    private lateinit var addTodoBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_make_routine)
        initView()
        setListener()
    }

    private fun initView(){
        gobackBtn = findViewById(R.id.back_completeRoutine)
        nextBtn=findViewById(R.id.next_completeRoutine)
        nameOfRoutine=findViewById(R.id.routineName_completeRoutine)
        searchCategoryView = findViewById(R.id.search_completeRoutine)
        searchIconImg = findViewById(R.id.search_img_completeRoutine)
        deleteSearchBtn = findViewById(R.id.deleteBtn_searchRoutine)
        addTodoBtn = findViewById(R.id.addBtn_work_completeRoutine)
        if (intent.hasExtra("name")) {
            nameOfRoutine.text = intent.getStringExtra("name")
        }
        hideKeyboard()
    }

    private fun setListener(){
        showDeleteButton(searchCategoryView,deleteSearchBtn)
        gobackBtn.setOnClickListener {
            finish()
        }
        nextBtn.setOnClickListener{

        }
        deleteSearchBtn.setOnClickListener {
            searchCategoryView.setText("")
        }
        addTodoBtn.setOnClickListener {
            val intent = Intent(this@CompleteMakeRoutineActivity,AddTodoActivity::class.java)
            startActivity(intent)
        }
    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchCategoryView.windowToken, 0)
    }

    private fun showDeleteButton(editText: EditText,imageView: ImageView){
        editText.doAfterTextChanged { s->
            if(s.isNullOrEmpty()){
                imageView.visibility = View.GONE
            }else{
                imageView.visibility = View.VISIBLE
            }
        }
    }
}