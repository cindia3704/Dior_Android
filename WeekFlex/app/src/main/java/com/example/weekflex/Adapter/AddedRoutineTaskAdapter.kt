package com.example.weekflex.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.CompleteMakeRoutineActivity
import com.example.weekflex.Data.Task
import com.example.weekflex.R

class AddedRoutineTaskAdapter(val activity: CompleteMakeRoutineActivity) : RecyclerView.Adapter<AddedRoutineTaskAdapter.ViewHolder>() {
    var user_id: Int? = null
    var newRoutineTaskList: List<Task> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton: ImageView = itemView.findViewById(R.id.addedTaskDeleteBtn)
        val taskName: TextView = itemView.findViewById(R.id.addedTaskName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.added_task_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newRoutineTaskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("newRoutineTaskList", newRoutineTaskList.get(position).taskTitle)
        holder.taskName.setText(newRoutineTaskList.get(position).taskTitle)
        holder.deleteButton.setOnClickListener {
            activity.deleteAddedTask(newRoutineTaskList.get(position))
        }
    }

    fun changeSelectedTaskList(list: List<Task>) {
        newRoutineTaskList = list
        notifyDataSetChanged()
    }
}
