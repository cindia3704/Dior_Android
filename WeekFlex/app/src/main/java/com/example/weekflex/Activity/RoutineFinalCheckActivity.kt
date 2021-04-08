package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weekflex.Adapter.RoutineTaskListAdapter
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_routine_final_check.*

//val routineSelected: Routine = Routine("Coding",listOf( RoutineItem("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
//    RoutineItem("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
//))

class RoutineFinalCheckActivity : BaseActivity() {
    private lateinit var routineNameView: EditText
    private lateinit var routineMentView: TextView
    private lateinit var addTaskBtn: ConstraintLayout
    private lateinit var saveRoutineBtn:ConstraintLayout
    private lateinit var goBackBtn:ImageView
    private lateinit var taskListAdapter : RoutineTaskListAdapter
    private lateinit var routineSelected:Routine
    private lateinit var pageTitle:TextView
    private  var isFinalCheck : Boolean = false

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
        pageTitle = findViewById(R.id.title_editRoutine)
        goBackBtn = findViewById(R.id.back_routineFinalCheck)
        routineSelected = intent.getSerializableExtra("routine") as Routine
        routineNameView.setText(routineSelected.routineTitle)

        // 최종점검 & 수정 디자인 반영 위해
        isFinalCheck = intent.hasExtra("final")
        pageTitle.visibility = if(isFinalCheck) View.INVISIBLE else View.VISIBLE
        routineMentView.visibility = if(isFinalCheck) View.VISIBLE else View.GONE
    }
    private fun setListener(){
        goBackBtn.setOnClickListener {
            if(isFinalCheck){
                backToCompleteMakeRoutineActivity()
            }
            else{
                val intentToAddRoutineAcitivity = Intent(this@RoutineFinalCheckActivity,AddRoutineActivity::class.java)
                navigateWithFinish(intentToAddRoutineAcitivity)
            }
        }
        addTaskBtn.setOnClickListener {
            // 수정하기에서 왔을때 --> "modify"  , 새로운 루틴 만들다가 최종 점검에서 왔을때 --> "final check"
            backToCompleteMakeRoutineActivity()
        }
        saveRoutineBtn.setOnClickListener {
//            val savedRoutine:Routine = taskListAdapter.getSavedRoutine()
            val savedRoutine:Routine = routineSelected
            if(savedRoutine.taskList.isNotEmpty()) {
                val noteToNextActivity= if(isFinalCheck) "newRoutine" else "originalRoutine"
                val intentToSaveRoutine =
                    Intent(this@RoutineFinalCheckActivity, AddRoutineActivity::class.java)
                intentToSaveRoutine.putExtra(noteToNextActivity, savedRoutine)

                navigateWithFinish(intentToSaveRoutine)
            }
        }
        routineNameView.doOnTextChanged { text, start, before, count ->
            routineSelected.routineTitle = text.toString()
        }
    }

    private fun backToCompleteMakeRoutineActivity(){
        val noteToNextActivity= if(isFinalCheck) "final check" else "modify"
        val intentToAddTask =
            Intent(this@RoutineFinalCheckActivity, CompleteMakeRoutineActivity::class.java)
        intentToAddTask.putExtra("routine",routineSelected)
        intentToAddTask.putExtra(noteToNextActivity, routineSelected)

        navigateWithFinish(intentToAddTask)
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
