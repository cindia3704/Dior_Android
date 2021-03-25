package com.example.weekflex.Adapter

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

class CategoryTaskListAdapter (val activity: CompleteMakeRoutineActivity,var categoryList:List<Category>):RecyclerView.Adapter<CategoryTaskListAdapter.ViewHolder>(){

    var user_id:Int?=null
    var selectedCategoryId:Int=1
    var selectedRoutineItemList : List<RoutineItem> = listOf()

    // property 찾아보기
    val taskList: List<RoutineItem> get() = getCategoryTaskList(categoryList, selectedCategoryId)

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
        if(taskList.get(position) in selectedRoutineItemList){
            holder.container.setBackgroundResource(R.color.task_gray)
        }else{
            holder.container.setBackgroundResource(R.color.white)
        }

        val item = taskList[position]

        if(taskList.get(position).bookMarked==true) {
            holder.bookmark.setImageResource(R.drawable.bookmark_fill)
        }
        else {
            holder.bookmark.setImageResource(R.drawable.bookmark_empty)
        }

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

    fun getCategoryTaskList(list:List<Category>, selectedCategoryId: Int):List<RoutineItem>{
        lateinit var category:Category
        for (i in list.indices)
            if(list[i].categoryId==selectedCategoryId){
                category = list[i]
            }
        return category.routineItemList
    }

    fun updateBackgroundColor(holder:ViewHolder,item:RoutineItem){
        if(item in selectedRoutineItemList){
            Log.d("msg","INCLUDED!!")
            holder.container.setBackgroundResource(R.color.white)
            activity.deleteAddedTask(item)
        }else{
            holder.container.setBackgroundResource(R.color.task_gray)
            activity.addTaskToRoutine(item)
            Log.d("msg","NOT INCLUDED!!")
        }
    }

    fun changeSelectedCategoryId(id : Int){
        selectedCategoryId = id
        notifyDataSetChanged()
    }
    fun changeSelectedRoutineItemList(list : List<RoutineItem>){
        selectedRoutineItemList = list
        notifyDataSetChanged()
    }
}