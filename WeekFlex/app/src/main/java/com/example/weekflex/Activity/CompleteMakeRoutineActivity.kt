package com.example.weekflex.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Adapter.AddedRoutineTaskAdapter
import com.example.weekflex.Adapter.CategoryTaskListAdapter
import com.example.weekflex.Adapter.RoutineCategoryListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_complete_make_routine.*


val categoryList = listOf(
    Category(1,"언어",0,listOf(
        Task("Speaking", 3, "10:00AM", "1:00PM",true, listOf("월","화")),
        Task("전화영어", 2, "1:00PM", "1:30PM",false, listOf("수")),
        Task("스피킹", 1, "5:00PM", "6:00PM",true, listOf("금","일"))
    )),
    Category(2,"Coding",0,listOf(
        Task("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
        Task("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
    )),
    Category(3,"운동",0,listOf(
        Task("코어", 1, "10:00AM", "1:00PM",false, listOf("일")),
        Task("하체", 2, "1:00PM", "1:30PM",false, listOf("토"))
    ))
)

class CompleteMakeRoutineActivity : AppCompatActivity() {
    private var isNewRoutine = true

    private lateinit var makeRoutineTopheader : ConstraintLayout
    private lateinit var gobackBtn:ImageView
    private lateinit var nextBtn:TextView
    private lateinit var nameOfRoutine:TextView
    private lateinit var searchCategoryView: EditText
    private lateinit var searchIconImg : ImageView
    private lateinit var deleteSearchBtn: ImageView
    private lateinit var addTodoBtn: Button
    private lateinit var addTaskComment:TextView
    private lateinit var addedTaskView:RecyclerView

    var selectedTaskListForNewRoutine  = listOf<Task>()
    var allCategoryList:List<Category> = emptyList()
    var searchedRoutineName:String = ""

    private lateinit var routineCategoryListAdapter :RoutineCategoryListAdapter
    private lateinit var categoryTaskListAdapter :CategoryTaskListAdapter
    private lateinit var addedRoutineTaskAdapter :AddedRoutineTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addAllCategoryTask(categoryList)
        setContentView(R.layout.activity_complete_make_routine)
        layoutInit()
        initView()

        setListener()

        routineCategoryListAdapter.lambdaList = listOf( {x -> categoryTaskListAdapter.changeSelectedCategoryId(x)} )
    }

    override fun onResume() {
        super.onResume()
        initView()

    }

    private fun initView(){
        makeRoutineTopheader = findViewById(R.id.makeRoutine_topheader)
        makeRoutineTopheader.setOnClickListener { hideKeyboard()}

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
        // 최종 점검 / 수정할때 데이터 받기
        if(intent.hasExtra("routine")){
            val recievedRoutine = intent.getSerializableExtra("routine") as Routine
            nameOfRoutine.text = recievedRoutine.routineTitle
            selectedTaskListForNewRoutine = recievedRoutine.taskList.map { task -> task }
            refreshAddedRoutineItem()
        }

        // 현재 상태가 수정중인건지 아니면 최종 점검인지 확인하기 위해
        isNewRoutine = !intent.hasExtra("modify")


        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        setHeaderView()
    }

    fun setHeaderView(){
        val noSelectedRoutine = selectedTaskListForNewRoutine.size!=0
        addTaskComment.visibility=if(noSelectedRoutine) View.INVISIBLE else View.VISIBLE
        addedTaskView.visibility=if(!noSelectedRoutine) View.INVISIBLE else View.VISIBLE
    }

    private fun setListener(){
        showDeleteButton(searchCategoryView,deleteSearchBtn)
        gobackBtn.setOnClickListener {
            if(isNewRoutine){
                Log.d("THIS IS NEW!!! , ","   Routinename: "+nameOfRoutine.text)
                val intentToInsertRoutineName = Intent(this@CompleteMakeRoutineActivity,InsertRoutineNameActivity::class.java)
                intent.putExtra("name",nameOfRoutine.text.toString())
                startActivity(intentToInsertRoutineName)
                finish()
            }else {
                finish()
            }
        }
        nextBtn.setOnClickListener{
            if(selectedTaskListForNewRoutine.size>0){
                Log.d("msg","is New Routine?? $isNewRoutine")
                val noteToIntent:String = if(isNewRoutine) "final" else "modify"
                val intent = Intent(this@CompleteMakeRoutineActivity,RoutineFinalCheckActivity::class.java)
                val newRoutine : Routine = Routine(nameOfRoutine.text.toString(),selectedTaskListForNewRoutine)
                intent.putExtra("routine",newRoutine)
                intent.putExtra(noteToIntent,"")
                startActivity(intent)
                finish()
            }

        }
        deleteSearchBtn.setOnClickListener {
            searchCategoryView.setText("")
            searchedRoutineName=""
            searchCategoryView.clearFocus()
            categoryTaskListAdapter.changeSearchedRoutine(searchedRoutineName)
            routineCategoryListAdapter.changeSearchedRoutine(searchedRoutineName)
        }
        addTodoBtn.setOnClickListener {
            val intent = Intent(this@CompleteMakeRoutineActivity,AddTodoActivity::class.java)
            startActivity(intent)
        }

        searchCategoryView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) searchIconImg.setImageResource(R.drawable.search_black)
            else searchIconImg.setImageResource(R.drawable.serach_gray)
        }

        searchCategoryView.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val str = if(s.isNotBlank()) s.toString() else ""
                Log.d("TextChange", "asxdfsdf$str")
                searchedRoutineName = searchCategoryView.text.toString()
                if(str.isNotEmpty()) {
                    routineCategoryListAdapter.setMySelectedId(0)
                }
                categoryTaskListAdapter.changeSearchedRoutine(str)
                routineCategoryListAdapter.changeSearchedRoutine(str)
            }

        })

//      키보드 enter --> 완료로 바꾸고 검색 진행
        searchCategoryView.setOnEditorActionListener { v, actionId, event ->
//            searchIconImg.setImageResource(R.drawable.search_black)
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    searchedRoutineName = searchCategoryView.text.toString()
                    categoryTaskListAdapter.changeSearchedRoutine(searchedRoutineName)
                    routineCategoryListAdapter.changeSearchedRoutine(searchedRoutineName)

//                    searchIconImg.setImageResource(R.drawable.serach_gray)

                    Log.d("msg","Search start!! "+searchedRoutineName)
                    hideKeyboard()
                }
                else -> {
                }
            }
            true
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
        routineCategoryListAdapter = RoutineCategoryListAdapter(this@CompleteMakeRoutineActivity, allCategoryList,searchedRoutineName)
        completeRoutine_categoryList.adapter = routineCategoryListAdapter
        completeRoutine_categoryList.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.HORIZONTAL,false)

        categoryTaskListAdapter = CategoryTaskListAdapter(this@CompleteMakeRoutineActivity, allCategoryList,searchedRoutineName)
        taskList_recyclerview.adapter = categoryTaskListAdapter
        taskList_recyclerview.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.VERTICAL,false)

        addedRoutineTaskAdapter = AddedRoutineTaskAdapter(this@CompleteMakeRoutineActivity)
        addedTasksList.adapter = addedRoutineTaskAdapter
        addedTasksList.layoutManager= GridLayoutManager(this@CompleteMakeRoutineActivity,1,
            GridLayoutManager.HORIZONTAL,false)

        addedRoutineTaskAdapter.notifyDataSetChanged()
        categoryTaskListAdapter.notifyDataSetChanged()
        routineCategoryListAdapter.notifyDataSetChanged()
    }

    fun deleteAddedTask(item: Task){
        selectedTaskListForNewRoutine = selectedTaskListForNewRoutine.minus(item)
        Log.d("msg","removed!!!!")
        refreshAddedRoutineItem()
    }

    fun refreshAddedRoutineItem(){
        addedRoutineTaskAdapter.changeSelectedRoutineItemList(selectedTaskListForNewRoutine)
        categoryTaskListAdapter.changeSelectedRoutineItemList(selectedTaskListForNewRoutine)

        setHeaderView()
    }

    fun addTaskToRoutine(item:Task){
        selectedTaskListForNewRoutine = selectedTaskListForNewRoutine.plus(item)

        Log.d("msg","added!!!!")
        Log.d("msg","count: "+selectedTaskListForNewRoutine.size)
        Log.d("msg","!!!!: "+selectedTaskListForNewRoutine[selectedTaskListForNewRoutine.size-1].routineItemTitle)

        refreshAddedRoutineItem()
    }

    fun addAllCategoryTask(categoryList:List<Category>){

        var allRoutineItem:List<Task> = categoryList.flatMap { category -> category.taskList }

        // 어댑터에 보내기 전 가장 앞 element = 전체로 만듬
        val allCategory = Category(0,"전체",0,allRoutineItem)
        allCategoryList=allCategoryList.plus(allCategory) + categoryList

        Log.d("msg","!!!!!!! ALL CATEGORY LIST SIZE : "+allCategoryList.size)

    }
}
