package com.example.weekflex.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.Data.categoryToStarImage
import com.example.weekflex.R

class TodoMainRoutineViewItemAdapter(
    val inflater: LayoutInflater,
    val routine: Routine,
    val onClickRoutineItemMenuButton: (Routine, Task) -> Unit
) : RecyclerView.Adapter<TodoMainRoutineViewItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineItemTitle: TextView
        val categoryStarImageView: ImageView
        val routineItemTime: TextView
        val menuButton: ImageView

        init {
            routineItemTitle = itemView.findViewById(R.id.todo_routine_home_view_item_routineNameTextView)
            categoryStarImageView = itemView.findViewById(R.id.todo_routine_home_view_item_starImageView)
            routineItemTime = itemView.findViewById(R.id.todo_routine_home_view_item_routineTimeTextView)
            menuButton = itemView.findViewById(R.id.todo_routine_home_view_item_Menu)
        }
    }

    // 생성될 때 해주는거
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.todo_routine_home_view_item
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return routine.taskList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routineItem = routine.taskList[position]

        holder.routineItemTitle.text = routineItem.routineItemTitle
        holder.routineItemTime.text = "${routineItem.startTime}~${routineItem.endTime}"

        val drawableStar = categoryToStarImage[routineItem.category] ?: R.drawable.graystar
        holder.categoryStarImageView.setImageResource(drawableStar)

        holder.menuButton.setOnClickListener { view ->
            onClickRoutineItemMenuButton.invoke(routine, routineItem)
        }
    }
}
