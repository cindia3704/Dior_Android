package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weekflex.Adapter.RoutineTaskListAdapter
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_routine_final_check.*

//val routineSelected: Routine = Routine("Coding",listOf( RoutineItem("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
//    RoutineItem("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
//))

class RoutineFinalCheckActivity : AppCompatActivity() {
    private lateinit var routineNameView: TextView
    private lateinit var routineMentView: TextView
    private lateinit var addTaskBtn: ConstraintLayout
    private lateinit var saveRoutineBtn:ConstraintLayout
    private lateinit var goBackBtn:ImageView
    private lateinit var taskListAdapter : RoutineTaskListAdapter
    private lateinit var routineSelected:Routine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_final_check)
        initView()
        setListener()
        layoutInit()
    }

    fun initView(){
        routineNameView = findViewById(R.id.routineName_routineFinalCheck)
        routineMentView = findViewById(R.id.routineMent_routineFinalCheck)
        addTaskBtn = findViewById(R.id.addTask_routineFinalCheck)
        saveRoutineBtn = findViewById(R.id.saveRoutine_routineFinalCheck)
        goBackBtn = findViewById(R.id.back_routineFinalCheck)
        routineSelected = intent.getSerializableExtra("routine") as Routine
        routineNameView.text = routineSelected.routineTitle
    }
    private fun setListener(){
        goBackBtn.setOnClickListener {
            finish()
        }
        addTaskBtn.setOnClickListener {

        }
        saveRoutineBtn.setOnClickListener {
            val savedRoutine:Routine = taskListAdapter.getSavedRoutine()
            if(savedRoutine.taskList.isNotEmpty()) {
                val intentToSaveRoutine =
                    Intent(this@RoutineFinalCheckActivity, AddRoutineActivity::class.java)
                intentToSaveRoutine.putExtra("newRoutine", savedRoutine)
                startActivity(intentToSaveRoutine)
                finish()
            }
        }
    }

    private fun layoutInit(){
        taskListAdapter =RoutineTaskListAdapter(this@RoutineFinalCheckActivity,routineSelected)
            taskList_routineFinalCheck.adapter = taskListAdapter
        taskList_routineFinalCheck.layoutManager= GridLayoutManager(this@RoutineFinalCheckActivity,1,
            GridLayoutManager.VERTICAL,false)
    }

    fun addTaskForRoutine(item:Task){
        routineSelected.taskList = routineSelected.taskList.plus(item)
        taskListAdapter.changeTaskItemList(routineSelected.taskList)
    }

    fun deleteTaskForRoutine(item: Task){
        routineSelected.taskList=routineSelected.taskList.minus(item)
        taskListAdapter.changeTaskItemList(routineSelected.taskList)

    }
}
