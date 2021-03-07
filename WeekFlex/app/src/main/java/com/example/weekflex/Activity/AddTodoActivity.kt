package com.example.weekflex.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.weekflex.Adapter.CategoryListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Todo
import com.example.weekflex.Fragment.BottomSheetFragment
import com.example.weekflex.Network.GlobalApplication
import com.example.weekflex.Network.GlobalApplication.Companion.currentTodo
import com.example.weekflex.Network.GlobalApplication.Companion.selectCategory
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.ArrayList


class AddTodoActivity : AppCompatActivity() {
    private lateinit var categoryChoose_tv:TextView
    private lateinit var categoryChoose_color:ImageView
    private lateinit var todoNameView: EditText

    private lateinit var mon_btn:Button
    private lateinit var tue_btn:Button
    private lateinit var wed_btn:Button
    private lateinit var thu_btn:Button
    private lateinit var fri_btn:Button
    private lateinit var sat_btn:Button
    private lateinit var sun_btn:Button

    private lateinit var timeSwitch: Switch

    private lateinit var pickTime1:TimePicker
    private lateinit var pickTime2:TimePicker
    private lateinit var startTime:TextView
    private lateinit var endTime:TextView

    private lateinit var chooseEndTime_btn:TextView
    private lateinit var chooseStartTime_btn:TextView

    private lateinit var todoSave_btn:Button

    private lateinit var category: Category
    private lateinit var todo: Todo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        initView()
        setListener()
        OnClickTime()

        category = Category("",0)
        todo = Todo("","", ArrayList(),"","")
    }

    private fun initView(){
        categoryChoose_tv= findViewById(R.id.btn_categoryChoose)
        categoryChoose_color =  findViewById(R.id.categoryColor)
        todoNameView=findViewById(R.id.insertTodoName)

        todoSave_btn = findViewById(R.id.save_todo)

        mon_btn = findViewById(R.id.btn_mon)
        tue_btn = findViewById(R.id.btn_tue)
        wed_btn = findViewById(R.id.btn_wed)
        thu_btn = findViewById(R.id.btn_thu)
        fri_btn = findViewById(R.id.btn_fri)
        sat_btn = findViewById(R.id.btn_sat)
        sun_btn = findViewById(R.id.btn_sun)

        timeSwitch=findViewById(R.id.timeSettingSwitch)    //시간 설정 할지 말지

        pickTime1=findViewById(R.id.timepicker1)           //시작 시간 고르기 위젯
        pickTime1.visibility = View.INVISIBLE              //안보이는게 디폴트
        pickTime2=findViewById(R.id.timepicker2)
        pickTime2.visibility = View.INVISIBLE

        startTime = findViewById(R.id.selectStartTime)
        endTime = findViewById(R.id.selectEndTime)
        chooseEndTime_btn = findViewById(R.id.chooseEndTime)
        chooseStartTime_btn = findViewById(R.id.chooseStartTime)

        todoSave_btn = findViewById(R.id.save_todo)
    }

    private fun setListener(){
        categoryChoose_tv.setOnClickListener {
            val bottomSheet=BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)

            var arraySelectCategory = selectCategory.toArray()
            if (arraySelectCategory.size!=0){
                var newtext = arraySelectCategory[0].toString()
                var token = newtext.split("=",",")
                Log.d("msg","$newtext")
                Log.d("msg","$token")
                categoryChoose_tv.setText(token[1]) // bottom sheet에서 선택한 selectCategory(GlobalApplication에 선언된 변수 사용) 중 카테고리 이름
                categoryChoose_color.setBackgroundColor(token[3].replace(")","").toInt())// 색상 int 값을 얻어서 해당 카테고리 색 넣어줌
            }
        }

        mon_btn.setOnClickListener {
            mon_btn.setSelected(!mon_btn.isSelected())
            todo.todoDates.add("mon")
        }

        tue_btn.setOnClickListener {
            tue_btn.setSelected(!tue_btn.isSelected());
            todo.todoDates.add("tue")
        }
        wed_btn.setOnClickListener {
            wed_btn.setSelected(!wed_btn.isSelected());
            todo.todoDates.add("wed")
        }
        thu_btn.setOnClickListener {
            thu_btn.setSelected(!thu_btn.isSelected());
            todo.todoDates.add("thu")
        }
        fri_btn.setOnClickListener {
            fri_btn.setSelected(!fri_btn.isSelected());
            todo.todoDates.add("fri")
        }
        sat_btn.setOnClickListener {
            sat_btn.setSelected(!sat_btn.isSelected());
            todo.todoDates.add("sat")
        }
        sun_btn.setOnClickListener {
            sun_btn.setSelected(!sun_btn.isSelected());
            todo.todoDates.add("sun")
        }

        timeSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                pickTime1.visibility = View.INVISIBLE
                chooseStartTime_btn.setTextColor(Color.parseColor("#000000"))
                startTime.text = "  9 : 00 AM"
                startTime.setTextColor(Color.parseColor("#BCBCBC"))
                chooseStartTime_btn.setTextColor(Color.parseColor("#000000"))
                endTime.text = "21 : 00 PM"
                endTime.setTextColor(Color.parseColor("#BCBCBC"))
                chooseEndTime_btn.setTextColor(Color.parseColor("#000000"))
            } else {
                pickTime1.visibility = View.VISIBLE
                chooseStartTime_btn.setTextColor(Color.parseColor("#FD8B34"))
            }
        })

        endTime.setOnClickListener {
            pickTime1.visibility = View.INVISIBLE
            pickTime2.visibility = View.VISIBLE
            chooseStartTime_btn.setTextColor(Color.parseColor("#000000"))
            chooseEndTime_btn.setTextColor(Color.parseColor("#FD8B34"))

        }

        startTime.setOnClickListener {
            pickTime2.visibility = View.INVISIBLE
            pickTime1.visibility = View.VISIBLE
            chooseStartTime_btn.setTextColor(Color.parseColor("#FD8B34"))
            chooseEndTime_btn.setTextColor(Color.parseColor("#000000"))
        }

        todoSave_btn.setOnClickListener {
            todo.todoTitle = todoNameView.getText().toString()
            todo.todoCategory = selectCategory.toString()
            todo.todoStart = startTime.text as String
            todo.todoEnd = endTime.text as String

            GlobalApplication.currentTodo.add(todo)
            Toast.makeText(this@AddTodoActivity, "${currentTodo}입니다 ",Toast.LENGTH_LONG).show()
            finish()

        }

    }

    private fun OnClickTime() {
        val startTime = findViewById<TextView>(R.id.selectStartTime)
        val endTime = findViewById<TextView>(R.id.selectEndTime)
        val pickTime1 = findViewById<TimePicker>(R.id.timepicker1)
        val pickTime2 = findViewById<TimePicker>(R.id.timepicker2)
        pickTime1.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (startTime != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$hour : $min $am_pm"
                startTime.text = msg
                startTime.setTextColor(Color.parseColor("#000000"))
                startTime.visibility = ViewGroup.VISIBLE
            }
        }
        pickTime2.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (endTime != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$hour : $min $am_pm"
                endTime.text = msg
                endTime.setTextColor(Color.parseColor("#000000"))
                endTime.visibility = ViewGroup.VISIBLE
            }
        }
    }







}
