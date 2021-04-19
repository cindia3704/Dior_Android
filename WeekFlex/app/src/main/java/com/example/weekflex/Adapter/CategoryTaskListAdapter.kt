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
import com.example.weekflex.Data.Task
import com.example.weekflex.R

class CategoryTaskListAdapter(
    val activity: CompleteMakeRoutineActivity,
    var categoryList: List<Category>,
    var searchedTaskString: String
) : RecyclerView.Adapter<CategoryTaskListAdapter.ViewHolder>() {
    private var selectedCategoryId: Int = 0
    var selectedTaskList: List<Task> = listOf()
    // property 찾아보기
    val taskList: List<Task> get() = getCategoryTaskList(categoryList, selectedCategoryId)
    val searchedTaskList: List<Task> get() = taskList.filter { t -> t.taskTitle.contains(searchedTaskString) }.toList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookmark: ImageView = itemView.findViewById(R.id.taskList_bookmarkImg)
        val taskName: TextView = itemView.findViewById(R.id.taskList_taskName)
        val taskTime: TextView = itemView.findViewById(R.id.taskList_time)
        val container: ConstraintLayout = itemView.findViewById(R.id.taskList_wholeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_view, parent, false)
        return ViewHolder(view)
    }

    // property getter setter 줄일때 이렇게 씀
    override fun getItemCount() = searchedTaskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // selectedCategoryId=0
        // 뷰 초기 세팅
        val task = searchedTaskList[position]

        var isSelectedTask = task in selectedTaskList
        holder.container.setBackgroundResource(if (isSelectedTask) R.color.task_gray else R.color.white)

        var isBookMarked = task.bookMarked
        holder.bookmark.setImageResource(if (isBookMarked) R.drawable.bookmark_fill else R.drawable.bookmark_empty)

        holder.taskTime.setText("${getWeekDays(task.weekdaysScheduled)} ${task.startTime}-${task.endTime}")
        holder.taskName.setText(task.taskTitle)
        holder.container.setOnClickListener {
            updateBackgroundColor(holder, task)
            notifyDataSetChanged()
        }
        holder.bookmark.setOnClickListener {
            Log.d("msg", "clicked!!!")
            updateBookmark(task)
        }
    }

    fun getWeekDays(days: List<String>): String {
        val weekdays = days.joinToString(", ")
        Log.d("msg", "weekday!!! $weekdays")

        return weekdays
    }

    fun getCategoryTaskList(list: List<Category>, selectedCategoryId: Int): List<Task> {
        var taskList: List<Task> = list.find { category -> category.categoryId == selectedCategoryId }?.taskList ?: emptyList()

        return taskList
    }

    fun updateBackgroundColor(holder: ViewHolder, item: Task) {
        if (item in selectedTaskList) {
            Log.d("msg", "INCLUDED!!")
        } else {
            holder.container.setBackgroundResource(R.color.task_gray)
            activity.addTaskToRoutine(item)
            Log.d("msg", "NOT INCLUDED!!")
        }
    }

    fun updateBookmark(item: Task) {
        taskList.find { i -> i.equals(item) }?.apply {
            bookMarked = !bookMarked
            Log.d("msg", "bookmarked: $bookMarked")
        }
        notifyDataSetChanged()
    }

    fun changeSelectedCategoryId(id: Int) {
        selectedCategoryId = id
        notifyDataSetChanged()
    }
    fun changeSelectedTaskList(list: List<Task>) {
        selectedTaskList = list
        notifyDataSetChanged()
    }

    fun changeSearchedRoutine(item: String) {
        searchedTaskString = item
        notifyDataSetChanged()
    }
}
