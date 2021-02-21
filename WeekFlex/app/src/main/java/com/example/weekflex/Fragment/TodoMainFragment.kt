package com.example.weekflex.Fragment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.weekflex.Activity.AddRoutineActivity
import com.example.weekflex.Activity.AddTodoActivity
import com.example.weekflex.Adapter.TodoWeekDateAdapter
import com.example.weekflex.R
import kotlinx.android.synthetic.main.todo_main_fragment.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

class TodoMainFragment : Fragment(){
    private var userId : Int? =1
    private var calendar= Calendar.getInstance()
    private var addClicked = false
    private var weekOfDay=arrayOf("일요일","월요일","화요일","수요일","목요일","금요일","토요일")
    private var dfDate : SimpleDateFormat=  SimpleDateFormat("yyyy-MM-dd",Locale.KOREA)
//    var weekHeaderAdapter = TodoWeekDateAdapter(calendar.get(Calendar.DATE),calendar.get(Calendar.DAY_OF_WEEK),weekOfDay,this)
    companion object{
        fun newInstance(userId:Int):TodoMainFragment{
            var bundle=Bundle()
            bundle.putInt("userId",userId)
            var fragment = TodoMainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = arguments?.getInt("userId")
        return inflater.inflate(R.layout.todo_main_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
    }

    fun initView(){
        val dateToday = getTodayDate()
        dateToday_todo.setText(dateToday)
        val days = getThisWeek()
        weekHeader_recylerview.adapter =TodoWeekDateAdapter(days,weekOfDay,this)
    }

    fun setListener(){
        addBtn_todo.setOnClickListener {
            addClicked = !addClicked
            if(addClicked) {
                addTodoBtn_todo.visibility = View.VISIBLE
                addRoutineBtn_todo.visibility = View.VISIBLE
            }
            else{
                addTodoBtn_todo.visibility = View.GONE
                addRoutineBtn_todo.visibility = View.GONE
            }
        }
        addTodoBtn_todo.setOnClickListener {
            val intent = Intent(this.context,AddTodoActivity::class.java)
            startActivity(intent)
        }
        addRoutineBtn_todo.setOnClickListener {
            val intent = Intent(this.context,AddRoutineActivity::class.java)
            startActivity(intent)
        }
    }

    // 오늘의 날짜 리턴하는 함수
    fun getTodayDate():String{
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("msg","월: "+month)
        Log.d("msg","일: "+day)
        Log.d("msg","요일: "+dayOfWeek)
        val todayDate=""+(month+1)+"월 "+day+"일, "+weekOfDay[(dayOfWeek-1)]
        return todayDate
    }

    private fun getThisWeek(): Array<Any> {
        var thisWeek = ArrayList<String>(7)
        var todayDay = calendar.get(Calendar.DAY_OF_WEEK)-1
        if(todayDay != 0){
            val differenceInDay = 0-todayDay
            calendar.add(Calendar.DATE,differenceInDay)
        }
        for ( i in 0..6){
            thisWeek.add(dfDate.format(calendar.getTime()).toString())
            calendar.add(Calendar.DATE,1)
        }
        return thisWeek.toArray()
    }
}