package com.example.weekflex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.example.weekflex.Activity.AddRoutineActivity
import com.example.weekflex.Data.Routine
import com.example.weekflex.R

class RoutineListAdapter (
    val activity: AddRoutineActivity,
    var routineList: List<Routine>
):RecyclerView.Adapter<RoutineListAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineTitle: TextView
        val taskCount: TextView
        val modifyRoutineButton: ImageButton
        val deleteRoutineButtone : ImageButton
        val swipeContainer : SwipeRevealLayout
        init {
            routineTitle = itemView.findViewById(R.id.routineList_item_addRoutine)
            taskCount = itemView.findViewById(R.id.routineList_item_numTodo)
            modifyRoutineButton = itemView.findViewById(R.id.modifyRoutine_routineList)
            deleteRoutineButtone = itemView.findViewById(R.id.deleteRoutine_routineList)
            swipeContainer = itemView.findViewById(R.id.routine_item_view_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_item_content,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var routine  = routineList[position]
        holder.swipeContainer.close(true)           // 삭제 후에 swipe되어 있지 않게!
        holder.routineTitle.setText(routine.routineTitle)
        holder.taskCount.setText(routine.taskList.size.toString()+"개의 할 일")
        holder.deleteRoutineButtone.setOnClickListener {
            activity.deleteRoutine(routine)
        }
        holder.modifyRoutineButton.setOnClickListener {
            activity.modifyRoutine(routine)
        }

    }
    fun changeRoutine(item: List<Routine>){
        routineList=item
        notifyDataSetChanged()
    }
}