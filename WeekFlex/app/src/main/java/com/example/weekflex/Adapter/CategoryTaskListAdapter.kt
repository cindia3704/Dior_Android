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
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R

class CategoryTaskListAdapter (val activity: CompleteMakeRoutineActivity,
                               var categoryList:List<Category>,
                               var searchedRoutine:String
                                ):RecyclerView.Adapter<CategoryTaskListAdapter.ViewHolder>(){
    var user_id:Int?=null
    var selectedCategoryId:Int=0
    var selectedRoutineItemList : List<RoutineItem> = listOf()
    var searchDoesNotExist : Boolean = false
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
        if(!searchedRoutine.isNullOrBlank()){
            if(searchDoesNotExist ==true) return 0 else return 1
        }else {
            return taskList.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        // 뷰 초기 세팅
        if (taskList.get(position) in selectedRoutineItemList) {
            holder.container.setBackgroundResource(R.color.task_gray)
        } else {
            holder.container.setBackgroundResource(R.color.white)
        }
        if (taskList.get(position).bookMarked == true) {
            holder.bookmark.setImageResource(R.drawable.bookmark_fill)
        } else {
            holder.bookmark.setImageResource(R.drawable.bookmark_empty)
        }

        if(!searchedRoutine.isNullOrBlank()){
            selectedCategoryId=0
            Log.d(",sg","searching,,,")
            val item:RoutineItem = showSearchedRoutineItem(searchedRoutine)
            if (item in selectedRoutineItemList) {
                holder.container.setBackgroundResource(R.color.task_gray)
            } else {
                holder.container.setBackgroundResource(R.color.white)
            }

            if(item.routineItemTitle!="null") {
                holder.taskName.setText(item.routineItemTitle)
                holder.taskTime.setText(getWeekDays(item.weekdaysScheduled) + item.startTime.toString() + "-" + item.endTime)
                holder.container.setOnClickListener {
                    updateBackgroundColor(holder, item)
                    notifyDataSetChanged()
                }
                holder.bookmark.setOnClickListener {
                    Log.d("msg", "clicked!!!")
                    updateBookmark(item)
                }
            }
        }else {
            val item = taskList[position]
            holder.bookmark.setOnClickListener {
                Log.d("msg","clicked!!!")
                updateBookmark(item)
            }
            holder.taskName.setText(taskList.get(position).routineItemTitle)
            holder.taskTime.setText(
                getWeekDays(taskList.get(position).weekdaysScheduled) +" "+ taskList.get(
                    position
                ).startTime.toString() + "-" + taskList.get(position).endTime
            )
            holder.bind(position)
            holder.container.setOnClickListener {
                updateBackgroundColor(holder, item)
                notifyDataSetChanged()
            }
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

    fun getCategoryTaskList(list:List<Category>, selectedCategoryId: Int):List<RoutineItem>{
        var routineItemList :List<RoutineItem> = emptyList()
        for (i in list.indices)
            if(list[i].categoryId==selectedCategoryId){
                routineItemList = list[i].routineItemList
            }
        return routineItemList
    }



    fun updateBackgroundColor(holder:ViewHolder,item:RoutineItem){
        if(item in selectedRoutineItemList){
            Log.d("msg","INCLUDED!!")
        }else{
            holder.container.setBackgroundResource(R.color.task_gray)
            activity.addTaskToRoutine(item)
            Log.d("msg","NOT INCLUDED!!")
        }
    }

    fun updateBookmark(item:RoutineItem){
        for ( i in taskList){
            if(i.equals(item)){
                i.bookMarked = !i.bookMarked
                Log.d("msg","bookmarked: "+i.bookMarked)
            }
        }
        notifyDataSetChanged()
    }

    fun changeSelectedCategoryId(id : Int){
        selectedCategoryId = id
        notifyDataSetChanged()
    }
    fun changeSelectedRoutineItemList(list : List<RoutineItem>){
        selectedRoutineItemList = list
        notifyDataSetChanged()
    }
    fun showSearchedRoutineItem(searchedRoutine:String): RoutineItem {
        val routineListFromAllCategory:List<RoutineItem> = categoryList[0].routineItemList
        var routineItem:RoutineItem? = null
        for(i in 0..routineListFromAllCategory.size-1){
            if(routineListFromAllCategory[i].routineItemTitle.equals(searchedRoutine)){
                routineItem = routineListFromAllCategory[i]
            }
        }

        if(routineItem==null){
            searchDoesNotExist = true
            return RoutineItem("null",0,"0","0",false, emptyList())
        }else{
            return routineItem
        }
    }
    fun changeSearchedRoutine(item : String){
        searchedRoutine=item
        notifyDataSetChanged()
    }
}