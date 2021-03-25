package com.example.weekflex.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.CompleteMakeRoutineActivity
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R

class CategoryTaskListAdapter (val activity: CompleteMakeRoutineActivity,var categoryList:List<Category>,val newRoutineTaskList:ArrayList<RoutineItem>):RecyclerView.Adapter<CategoryTaskListAdapter.ViewHolder>(){

    var user_id:Int?=null
    var selectedCategory:Int=1
    var taskList:List<RoutineItem> = getCategoryTaskList(categoryList)
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val bookmark : ImageView = itemView.findViewById(R.id.taskList_bookmarkImg)
        val taskName: TextView = itemView.findViewById(R.id.taskList_taskName)
        val taskTime: TextView = itemView.findViewById(R.id.taskList_time)
        val container: ConstraintLayout=itemView.findViewById(R.id.taskList_wholeLayout)
        fun bind(position: Int){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(taskList.get(position) in newRoutineTaskList){
            holder.container.setBackgroundResource(R.color.task_gray)
        }else{
            holder.container.setBackgroundResource(R.color.white)
        }

        val item = taskList[position]
        val adapter = this
        if(taskList.get(position).bookMarked==true) holder.bookmark.setImageResource(R.drawable.bookmark_fill)
        else holder.bookmark.setImageResource(R.drawable.bookmark_empty)
        holder.taskName.setText(taskList.get(position).routineItemTitle)
        holder.taskTime.setText(getWeekDays(taskList.get(position).weekdaysScheduled)+taskList.get(position).startTime.toString()+"-"+taskList.get(position).endTime)
        holder.bind(position)

        holder.container.setOnClickListener {
            updateBackgroundColor(holder,item)
            notifyDataSetChanged()
        }


    }

    fun getWeekDays(days:List<String>):String{
        var weekdays  = ""
        for (day in days)
            weekdays+day

        return weekdays
    }
    fun getCategoryTaskList(list:List<Category>):List<RoutineItem>{
        lateinit var category:Category
        for (i in list.indices)
            if(list[i].categoryId==selectedCategory){
                category = list[i]
            }
        return category.routineItemList
    }
    fun isIncluded(newRoutineTaskList:ArrayList<RoutineItem>, item:RoutineItem):Boolean{
        return item in newRoutineTaskList
    }

    fun updateBackgroundColor(holder:ViewHolder,item:RoutineItem){
        if(isIncluded(newRoutineTaskList,item)){
            Log.d("msg","INCLUDED!!")
            holder.container.setBackgroundResource(R.color.white)
            activity.deleteAddedTask(item)
        }else{
            holder.container.setBackgroundResource(R.color.task_gray)
            activity.addTaskToRoutin(item)
            Log.d("msg","NOT INCLUDED!!")

        }
    }



}