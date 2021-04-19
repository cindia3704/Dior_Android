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
    val searchedTaskList: List<Task> get() = taskList.filter { t -> t.routineItemTitle.contains(searchedTaskString) }.toList()

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
        holder.taskName.setText(task.routineItemTitle)
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
    fun changeSelectedRoutineItemList(list: List<Task>) {
        selectedTaskList = list
        notifyDataSetChanged()
    }

    // 요 기능을 나눌겁니다.
    // RoutineItem(of 전체 카테고리)를 대상으로 가져오기. 전체가 아닐때는 정상적으로 가져와야된다.
    // 전체도, 카테고리 중 하나니까, 그냥 선택 카테고리를 검색이면 전체로 바꾸면 됨.
    // (검색중인지 여부도 검사 안함. 전제조건 검사 X -> filterd~에서 보면, contain으로 한다. contain("")는 무조건 true라서)
    // 검색해서 맞는거 가져오기 ->filtered~~
    // 검색결과가 있으면 searchDoesNotExist = true로 바꾸기 (false로 바꾸지 않음.)

    fun changeSearchedRoutine(item: String) {
        searchedTaskString = item
        notifyDataSetChanged()
    }
}
