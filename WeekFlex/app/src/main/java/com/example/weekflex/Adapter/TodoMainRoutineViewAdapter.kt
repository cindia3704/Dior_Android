package com.example.weekflex.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Routine
import com.example.weekflex.R

class TodoMainRoutineViewAdapter (
        val inflater: LayoutInflater,
        val routineList: List<Routine>
):RecyclerView.Adapter<TodoMainRoutineViewAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRecyclerView : RecyclerView
        val title : TextView

        init {
            itemRecyclerView = itemView.findViewById(R.id.todo_routine_home_view_itemRecyclerView)
            title = itemView.findViewById(R.id.todo_routine_home_view_title)
        }
    }

    // 생성될 때 해주는거
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.todo_routine_home_view
        val view = inflater.inflate(layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routineList[position]
        holder.title.text = routine.routineTitle
        holder.itemRecyclerView.adapter = TodoMainRoutineViewItemAdapter(inflater, routine.routineItemList)
    }
}