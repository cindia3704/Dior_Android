package com.example.weekflex.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.CompleteMakeRoutineActivity
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R

class AddedRoutineTaskAdapter(val activity: CompleteMakeRoutineActivity): RecyclerView.Adapter<AddedRoutineTaskAdapter.ViewHolder>(){
    var user_id:Int?=null
    var newRoutineTaskList:List<RoutineItem> = listOf()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val deleteButton : ImageView = itemView.findViewById(R.id.addedTaskDeleteBtn)
        val taskName: TextView = itemView.findViewById(R.id.addedTaskName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.added_task_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newRoutineTaskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("newRoutineTaskList",newRoutineTaskList.get(position).routineItemTitle)
        holder.taskName.setText(newRoutineTaskList.get(position).routineItemTitle)
        holder.deleteButton.setOnClickListener {
            activity.deleteAddedTask(newRoutineTaskList.get(position))
        }
    }

    fun changeSelectedRoutineItemList(list : List<RoutineItem>){
        newRoutineTaskList = list
        notifyDataSetChanged()
    }
}
