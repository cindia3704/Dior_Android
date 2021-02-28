package com.example.weekflex.Fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.weekflex.Activity.AddRoutineActivity
import com.example.weekflex.Activity.AddTodoActivity
import com.example.weekflex.Adapter.TodoWeekDateAdapter
import com.example.weekflex.R
import kotlinx.android.synthetic.main.navibation_bar.*
import kotlinx.android.synthetic.main.todo_main_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class TodoMainFragment : Fragment(){
    private var userId : Int? =1
    private var calendar= Calendar.getInstance()
    private var addClicked = false
    private var weekOfDayEnglish=arrayOf("Sunday","Tuesday","Wednesday","Thursday","Friday","Saturday","Monday")
    private var weekOfDay=arrayOf("월요일","화요일","수요일","목요일","금요일","토요일","일요일")
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
        val day = calendar.get(Calendar.DATE)
        val days = getThisWeek()
        Log.d("msg","오늘: "+day.toString())
        weekHeader_recylerview.setHasFixedSize(true)
        weekHeader_recylerview.adapter =TodoWeekDateAdapter(days,weekOfDay,this,day.toString())
        weekHeader_recylerview.addItemDecoration(RecyclerDecoration(100))
    }

    fun setListener(){
        addBtn_todo.setOnClickListener {
            addClicked = !addClicked
            if(addClicked) {
                addBtnImg_todo.rotation=45F
                blackOpacityView_todo.visibility=View.VISIBLE
                addTodoBtn_todo.visibility = View.VISIBLE
                addTodoBtnImg_todo.visibility=View.VISIBLE
                addRoutineBtn_todo.visibility = View.VISIBLE
                addRoutineBtnImg_todo.visibility=View.VISIBLE
            }
            else{
                addBtnImg_todo.rotation=90F
                blackOpacityView_todo.visibility=View.GONE
                addTodoBtn_todo.visibility = View.GONE
                addTodoBtnImg_todo.visibility=View.GONE
                addRoutineBtn_todo.visibility = View.GONE
                addRoutineBtnImg_todo.visibility=View.GONE
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
        val todayDate=""+(month+1)+"월 "+day+"일, "+weekOfDayEnglish[(dayOfWeek-1)]
        return todayDate
    }

    private fun getThisWeek(): Array<Any> {
        var thisWeek = ArrayList<String>(7)
        var todayDay = calendar.get(Calendar.DAY_OF_WEEK)+5
        Log.d("msg","todayDay: "+todayDay.toString())
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