package com.example.weekflex.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Fragment.TodoMainFragment
import com.example.weekflex.R
import kotlinx.android.synthetic.main.todo_week_date_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class TodoWeekDateAdapter (
    val weekDays: Array<Any>,
    val daysOfWeek:Array<String>,
    val fragment: TodoMainFragment,
    val today:String
):RecyclerView.Adapter<TodoWeekDateAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateDay:TextView
        val weekOfDay:TextView
        val underline: View
        val dayImage: ImageView

        init {
            dateDay = itemView.findViewById(R.id.dateDay_todo)
            weekOfDay = itemView.findViewById(R.id.dateweekDay_todo)
            underline = itemView.findViewById(R.id.underline_today_todo)
            dayImage= itemView.findViewById(R.id.routine_star_img_todo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_week_date_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("msg","weekday: "+(weekDays.get(position)).toString())
        if((weekDays.get(position)).toString().subSequence(8,10) == today){
            holder.underline.visibility = View.VISIBLE
        }else{
            holder.underline.visibility = View.INVISIBLE
        }
        holder.dateDay.setText((weekDays.get(position)).toString().subSequence(8,10))
        holder.weekOfDay.setText(daysOfWeek.get(position).toString())

    }
}