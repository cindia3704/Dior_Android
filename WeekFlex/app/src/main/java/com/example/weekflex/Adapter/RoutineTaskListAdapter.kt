package com.example.weekflex.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.RoutineFinalCheckActivity
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.R

class RoutineTaskListAdapter (val activity: RoutineFinalCheckActivity,
                               var routine: Routine
): RecyclerView.Adapter<RoutineTaskListAdapter.ViewHolder>(){
    var user_id:Int?=null
    var taskList:List<Task> = routine.taskList

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val taskName: TextView = itemView.findViewById(R.id.taskName_finalCheckItem)
        val taskDateAndTime: TextView = itemView.findViewById(R.id.taskDate_finalCheckItem)
        val taskColor: ImageView =itemView.findViewById(R.id.routineImg_finalCheckItem)
        val taskDeleteBtn: ImageView = itemView.findViewById(R.id.deleteTask_finalCheckItem)
        val divisionLine : View = itemView.findViewById(R.id.divideLine_finalCheckItem)
        fun bind(position: Int){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_final_check_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("msg","size of taskList!!!!!: "+taskList.size)
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = taskList[position]
        if(position==taskList.size-1){
            holder.divisionLine.visibility = View.INVISIBLE
        }
        Log.d("msg","item: "+item.routineItemTitle)
        holder.taskColor.setImageResource(R.drawable.yellowstar)
        holder.taskName.setText(item.routineItemTitle)
        holder.taskDateAndTime.setText(getWeekDays(item.weekdaysScheduled)+" "+ item.startTime + "-" +item.endTime)
        holder.bind(position)
        holder.taskDeleteBtn.setOnClickListener {
            activity.deleteTaskForRoutine(item)
        }
    }

    fun getWeekDays(days:List<String>):String{
        var weekdays  = ""
        for (day in days) {
            Log.d("msg", "day: $day")
            weekdays = "$weekdays$day, "
        }
        Log.d("msg","weekday!!! $weekdays")
        return weekdays.subSequence(0,weekdays.length-2).toString()
    }

    fun changeTaskItemList(list : List<Task>){
        taskList = list
        notifyDataSetChanged()
    }

    fun getSavedRoutine():Routine{
        val newRoutine: Routine=Routine(routine.routineTitle,taskList)
        return newRoutine
    }

}