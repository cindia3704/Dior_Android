package com.example.weekflex.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Adapter.AddedRoutineTaskAdapter
import com.example.weekflex.Adapter.CategoryTaskListAdapter
import com.example.weekflex.Adapter.RoutineCategoryListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_complete_make_routine.*

val categoryList = listOf(
    Category(1,"언어",0,listOf(
        RoutineItem("스피킹", 3, "10:00AM", "1:00PM",true, arrayListOf("월","화")),
        RoutineItem("전화영어", 2, "1:00PM", "1:30PM",false, arrayListOf("수")),
        RoutineItem("스피킹", 1, "5:00PM", "6:00PM",true, arrayListOf("금","일"))
    )),
    Category(2,"코딩",0,listOf(
        RoutineItem("CS", 3, "10:00AM", "1:00PM",false, arrayListOf("수")),
        RoutineItem("알고리즘", 2, "1:00PM", "1:30PM",true, arrayListOf("월","화"))
    )),
    Category(3,"운동",0,listOf(
        RoutineItem("코어", 1, "10:00AM", "1:00PM",false, arrayListOf("일")),
        RoutineItem("하체", 2, "1:00PM", "1:30PM",false, arrayListOf("토"))
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
    private lateinit var addTaskComment:TextView
    private lateinit var addedTaskView:RecyclerView

    var selectedTaskListForNewRoutine  = listOf<RoutineItem>()

    private val routineCategoryListAdapter = RoutineCategoryListAdapter(this@CompleteMakeRoutineActivity, categoryList)
    private val categoryTaskListAdapter = CategoryTaskListAdapter(this@CompleteMakeRoutineActivity, categoryList)
    private val addedRoutineTaskAdapter = AddedRoutineTaskAdapter(this@CompleteMakeRoutineActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_make_routine)
        initView()
        layoutInit()

        setListener()

        routineCategoryListAdapter.lambdaList = listOf( {x -> categoryTaskListAdapter.changeSelectedCategoryId(x)} )
    }

    override fun onResume() {
        super.onResume()
        initView()

    }

    private fun initView(){
        gobackBtn = findViewById(R.id.back_completeRoutine)
        nextBtn=findViewById(R.id.next_completeRoutine)
        nameOfRoutine=findViewById(R.id.routineName_completeRoutine)
        searchCategoryView = findViewById(R.id.search_completeRoutine)
        searchIconImg = findViewById(R.id.search_img_completeRoutine)
        deleteSearchBtn = findViewById(R.id.deleteBtn_searchRoutine)
        addTodoBtn = findViewById(R.id.addBtn_work_completeRoutine)
        addTaskComment=findViewById(R.id.addRoutineMent_completeRoutine)
        addedTaskView = findViewById(R.id.addedTasksList)
        if (intent.hasExtra("name")) {
            nameOfRoutine.text = intent.getStringExtra("name")
        }
        //현재 루틴에 추가된 태스크 없을때 안내 메세지. 아니면 태스크 리스트
        hideKeyboard()
        setHeaderView()
    }

    fun setHeaderView(){
        if(selectedTaskListForNewRoutine.size!=0){
            addTaskComment.visibility=View.INVISIBLE
            addedTaskView.visibility=View.VISIBLE
        }else{
            addTaskComment.visibility=View.VISIBLE
            addedTaskView.visibility=View.INVISIBLE
        }
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
//     val categoryAdapter = RoutineCategoryListAdapter(LayoutInflater.from(this@CompleteMakeRoutineActivity), categoryList)
        completeRoutine_categoryList.adapter = routineCategoryListAdapter
        completeRoutine_categoryList.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.HORIZONTAL,false)

        taskList_recyclerview.adapter = categoryTaskListAdapter
        taskList_recyclerview.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.VERTICAL,false)

        addedTasksList.adapter = addedRoutineTaskAdapter
        addedTasksList.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.HORIZONTAL,false)

        addedRoutineTaskAdapter.notifyDataSetChanged()
        categoryTaskListAdapter.notifyDataSetChanged()
        routineCategoryListAdapter.notifyDataSetChanged()
    }

    fun deleteAddedTask(item: RoutineItem){
        selectedTaskListForNewRoutine = selectedTaskListForNewRoutine.minus(item)
        Log.d("msg","removed!!!!")

        refreshAddedRoutineItem()
    }

    fun refreshAddedRoutineItem(){
        addedRoutineTaskAdapter.changeSelectedRoutineItemList(selectedTaskListForNewRoutine)
        categoryTaskListAdapter.changeSelectedRoutineItemList(selectedTaskListForNewRoutine)

        setHeaderView()
    }

    fun addTaskToRoutine(item:RoutineItem){
        selectedTaskListForNewRoutine = selectedTaskListForNewRoutine.plus(item)

        Log.d("msg","added!!!!")
        Log.d("msg","count: "+selectedTaskListForNewRoutine.size)
        Log.d("msg","!!!!: "+selectedTaskListForNewRoutine[selectedTaskListForNewRoutine.size-1].routineItemTitle)

        refreshAddedRoutineItem()
    }
}
