package com.example.weekflex.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weekflex.Adapter.CategoryTaskListAdapter
import com.example.weekflex.Adapter.RoutineCategoryListAdapter
import com.example.weekflex.Adapter.RoutineListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_routine.*
import kotlinx.android.synthetic.main.activity_complete_make_routine.*

val categoryList = listOf(
    Category(1,"언어",0,listOf(
        RoutineItem("스피킹", 3, "10:00AM", "1:00PM",true, listOf("월","화")),
        RoutineItem("전화영어", 2, "1:00PM", "1:30PM",false, listOf("수")),
        RoutineItem("스피킹", 1, "5:00PM", "6:00PM",true, listOf("금","일"))
    )),
    Category(2,"코딩",0,listOf(
        RoutineItem("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
        RoutineItem("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
    )),
    Category(3,"운동",0,listOf(
        RoutineItem("코어", 1, "10:00AM", "1:00PM",false, listOf("일")),
        RoutineItem("하체", 2, "1:00PM", "1:30PM",false, listOf("토"))
    ))
)

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
        layoutInit()
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
    private fun layoutInit(){
        //TODO: 서버랑 연결해서 루틴 리스트 받아오기
        val adapter = RoutineCategoryListAdapter(LayoutInflater.from(this@CompleteMakeRoutineActivity), categoryList,1)
        completeRoutine_categoryList.adapter = adapter
        completeRoutine_categoryList.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.HORIZONTAL,false)

        val categoryTaskAdapter = CategoryTaskListAdapter(LayoutInflater.from(this@CompleteMakeRoutineActivity), categoryList)
        taskList_recyclerview.adapter = categoryTaskAdapter
        taskList_recyclerview.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.VERTICAL,false)
    }
}
