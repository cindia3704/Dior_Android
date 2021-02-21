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
import com.example.weekflex.Fragment.TodoMainFragment
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_routine.*

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
        val routineList = ArrayList<Routine>()
        routineList.add(Routine("면접집중"))
        routineList.add(Routine("운동집중"))
        routineList.add(Routine("영어공부"))
        routineList.add(Routine("취업"))
        val adapter = RoutineListAdapter(LayoutInflater.from(this@AddRoutineActivity),routineList)
        recyclerview_addRoutine.adapter = adapter
        recyclerview_addRoutine.layoutManager= GridLayoutManager(this@AddRoutineActivity,2,GridLayoutManager.VERTICAL,false)
    }
}