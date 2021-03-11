package com.example.weekflex.Adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Routine
import com.example.weekflex.R

class RoutineListAdapter (
    val inflater: LayoutInflater,
    val routineList: List<Routine>
):RecyclerView.Adapter<RoutineListAdapter.ViewHolder>(){
//    val routineList: ArrayList<Routine> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineTitle: TextView
        init {
            routineTitle = itemView.findViewById(R.id.routineList_item_addRoutine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.routine_item_content,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.routineTitle.setText(routineList.get(position).routineTitle)
    }
}