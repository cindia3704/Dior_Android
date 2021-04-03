package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weekflex.Adapter.RoutineListAdapter
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_routine.*

var routineList = listOf(
        Routine("English Master :-)", listOf(
            Task("스피킹", 3, "10:00AM", "1:00PM",true, listOf("월","화")),
            Task("전화영어", 2, "1:00PM", "1:30PM",false, listOf("수")),
            Task("스피킹", 1, "5:00PM", "6:00PM",true, listOf("금","일"))
        )),
        Routine("빡세게 면접 준비", listOf(
            Task("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
            Task("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
        )),
        Routine("운동 뿌셔", listOf(
            Task("코어", 1, "10:00AM", "1:00PM",false, listOf("일")),
            Task("하체", 2, "1:00PM", "1:30PM",false, listOf("토"))
        ))
)

class AddRoutineActivity : AppCompatActivity() {
    private lateinit var backBtn:Button
    private lateinit var introMentView:TextView
    private lateinit var makeRoutineBtn: Button
    private lateinit var adapter : RoutineListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)
        initView()
        setListener()
        layoutInit()
    }

    private fun initView(){
        backBtn= findViewById(R.id.back_addRoutine)
        introMentView = findViewById(R.id.ment_addRoutine)
        makeRoutineBtn = findViewById(R.id.makeRoutine_addRoutine)
        if(intent.hasExtra("newRoutine")){
            val newRoutine = intent.getSerializableExtra("newRoutine") as Routine
            routineList = routineList.plus(newRoutine)
        }
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

    private fun layoutInit(){
        //TODO: 서버랑 연결해서 루틴 리스트 받아오기

        adapter = RoutineListAdapter(this@AddRoutineActivity, routineList)
        recyclerview_addRoutine.adapter = adapter
        recyclerview_addRoutine.layoutManager= GridLayoutManager(this@AddRoutineActivity,1,GridLayoutManager.VERTICAL,false)
    }

    fun deleteRoutine(item: Routine){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AddRoutineActivity)
        builder.setMessage("이번주의 해당 루틴 전체가 삭제됩니다.\n이대로 삭제를 진행할까요?")

        builder.setPositiveButton("삭제하기") { dialog, which ->
            routineList=routineList.minus(item)
            adapter.changeRoutine(routineList)
        }

        builder.setNegativeButton("그만두기") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()

        this?.let {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.gray_4))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.color6))
        }
    }
}