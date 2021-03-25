package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weekflex.Adapter.RoutineListAdapter
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.Fragment.TodoMainFragment
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_routine.*

val routineList = listOf(
        Routine("English Master :-)", listOf(
            RoutineItem("스피킹", 3, "10:00AM", "1:00PM",true, listOf("월","화")),
            RoutineItem("전화영어", 2, "1:00PM", "1:30PM",false, listOf("수")),
            RoutineItem("스피킹", 1, "5:00PM", "6:00PM",true, listOf("금","일"))
        )),
        Routine("빡세게 면접 준비", listOf(
            RoutineItem("CS", 3, "10:00AM", "1:00PM",false, listOf("수")),
            RoutineItem("알고리즘", 2, "1:00PM", "1:30PM",true, listOf("월","화"))
        )),
        Routine("운동 뿌셔", listOf(
            RoutineItem("코어", 1, "10:00AM", "1:00PM",false, listOf("일")),
            RoutineItem("하체", 2, "1:00PM", "1:30PM",false, listOf("토"))
        ))
)

class AddRoutineActivity : AppCompatActivity() {
    private lateinit var backBtn:Button
    private lateinit var introMentView:TextView
    private lateinit var makeRoutineBtn: Button
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

        val adapter = RoutineListAdapter(LayoutInflater.from(this@AddRoutineActivity), routineList)
        recyclerview_addRoutine.adapter = adapter
        recyclerview_addRoutine.layoutManager= GridLayoutManager(this@AddRoutineActivity,1,GridLayoutManager.VERTICAL,false)
    }
}