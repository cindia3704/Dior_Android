package com.example.weekflex.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.weekflex.Activity.AddRoutineActivity
import com.example.weekflex.Activity.AddTodoActivity
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.Activity.routineList
import com.example.weekflex.Adapter.TodoMainRoutineViewAdapter
import com.example.weekflex.Adapter.TodoWeekDateAdapter
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.todo_main_fragment.addBtnImg_todo
import kotlinx.android.synthetic.main.todo_main_fragment.addBtn_todo
import kotlinx.android.synthetic.main.todo_main_fragment.addRoutineBtn_todo
import kotlinx.android.synthetic.main.todo_main_fragment.addRoutineView_todo
import kotlinx.android.synthetic.main.todo_main_fragment.addTodoBtn_todo
import kotlinx.android.synthetic.main.todo_main_fragment.addTodoView_todo
import kotlinx.android.synthetic.main.todo_main_fragment.blackOpacityView_todo
import kotlinx.android.synthetic.main.todo_main_fragment.body_todo_routineRecyclerView
import kotlinx.android.synthetic.main.todo_main_fragment.dateToday_todo
import kotlinx.android.synthetic.main.todo_main_fragment.weekHeader_recylerview

class TodoMainFragment : Fragment() {
    private lateinit var inflater: LayoutInflater

    private var userId: Int? = 1
    private var calendar = Calendar.getInstance()
    private var addClicked = false
    private var monthEnglish = arrayOf("Jan", "Feb", "Mar", "Apr", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private var weekOfDayEnglish = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    private var weekOfDay = arrayOf("???", "???", "???", "???", "???", "???", "???")
    private var dfDate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
//    var weekHeaderAdapter = TodoWeekDateAdapter(calendar.get(Calendar.DATE),calendar.get(Calendar.DAY_OF_WEEK),weekOfDay,this)
    companion object {
        fun newInstance(userId: Int): TodoMainFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
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

        this.inflater = inflater

        return inflater.inflate(R.layout.todo_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
    }

    fun initView() {
        val dateToday = getTodayDate()
        dateToday_todo.setText(dateToday)
        val day = calendar.get(Calendar.DATE)
        val days = getThisWeek()
        Log.d("msg", "??????: " + day.toString())
        weekHeader_recylerview.setHasFixedSize(true)
        weekHeader_recylerview.adapter = TodoWeekDateAdapter(days, weekOfDay, this, day.toString())
        val display = (this.resources.displayMetrics.xdpi + (24 * 7)) / 6
        val deco = RecyclerDecoration(display.toInt())
        weekHeader_recylerview.addItemDecoration(deco)

        body_todo_routineRecyclerView.adapter = TodoMainRoutineViewAdapter(
                inflater,
                routineList,
                onClickDeleteButton = ::onClickDeleteRoutine,
                onClickTaskMenuButton = ::onClickTaskMenuButton
        )
    }

    fun onClickTaskMenuButton(routine: Routine, task: Task) {
        parentFragmentManager?.let {
            val result = "result"
            setFragmentResult(TaskMenuDetailBottomFragment.requestKey(task), bundleOf(TaskMenuDetailBottomFragment.bundleKey(task) to result))
            TaskMenuDetailBottomFragment.showTask(it, task)
            parentFragmentManager.beginTransaction()
                .replace(R.id.task_menu_detail_bottom_fragment, TaskMenuDetailBottomFragment.instance)
                .commit()
        }
    }

    fun onClickDeleteRoutine(routine: Routine) {
//        Toast.makeText(context,"??????!! ",Toast.LENGTH_SHORT).show()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setMessage("???????????? ?????? ?????? ????????? ???????????????.\n????????? ????????? ????????????????")

        builder.setPositiveButton("????????????") { dialog, which ->
            Toast.makeText(context, "??? ??????!! ", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("????????????") { dialog, which ->
            Toast.makeText(context, "??????  ??????!! ", Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()

        this.context?.let {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.gray_4))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.color6))
        }
    }

    fun setListener() {
        addBtn_todo.setOnClickListener {
            addClicked = !addClicked
            Log.d("msg", "clicked")
            if (addClicked) {
                (activity as MainActivity?)?.makeDarkTabView()
                addBtnImg_todo.animate().rotation(-45F).setDuration(200)
                blackOpacityView_todo.visibility = View.VISIBLE

                addTodoBtn_todo.visibility = View.VISIBLE
                addTodoView_todo.visibility = View.VISIBLE
                addTodoView_todo.translationY = -132F
                addTodoView_todo.animate().alpha(1f).translationYBy(-132F).setDuration(200)

                addRoutineView_todo.visibility = View.VISIBLE
                addRoutineView_todo.translationY = -264F
                addRoutineView_todo.animate().alpha(1f).translationYBy(-264F).setDuration(200)
            } else {
                (activity as MainActivity?)?.undoDarkTabView()
                addBtnImg_todo.animate().rotation(0F).setDuration(200)
                blackOpacityView_todo.visibility = View.GONE
                addRoutineView_todo.visibility = View.GONE
                addTodoView_todo.visibility = View.GONE
            }
        }
        addTodoBtn_todo.setOnClickListener {
            val intent = Intent(this.context, AddTodoActivity::class.java)
            startActivity(intent)
        }
        addRoutineBtn_todo.setOnClickListener {
            val intent = Intent(this.context, AddRoutineActivity::class.java)
            startActivity(intent)
        }
    }

    // ????????? ?????? ???????????? ??????
    fun getTodayDate(): String {
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("msg", "???: " + month)
        Log.d("msg", "???: " + day)
        Log.d("msg", "??????: " + dayOfWeek)
        val dayStyle = when (day) {
            1 -> "st"
            31 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        val todayDate = "" + monthEnglish[(month)] + " " + day + dayStyle + ", " + weekOfDayEnglish[(dayOfWeek - 1)]
        return todayDate
    }

    private fun getThisWeek(): Array<Any> {
        var thisWeek = ArrayList<String>(7)
        var todayDay = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("msg", "todayDay: " + todayDay.toString())
        val differenceInDay = floorMod(todayDay - Calendar.MONDAY, 7)
        Log.d("diff: ", differenceInDay.toString())
        calendar.add(Calendar.DATE, -differenceInDay)

        for (i in 0..6) {
            Log.d("msg", "calday: " + i + "" + calendar.get(Calendar.DAY_OF_WEEK).toString())
            thisWeek.add(dfDate.format(calendar.getTime()).toString())
            calendar.add(Calendar.DATE, 1)
        }
        return thisWeek.toArray()
    }

    private fun floorMod(number: Int, mod: Int): Int {
        val result = number % mod
        if (result < 0) {
            return result + mod
        } else {
            return result
        }
    }
}
