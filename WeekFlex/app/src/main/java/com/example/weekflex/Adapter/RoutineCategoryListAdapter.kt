package com.example.weekflex.Adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.CompleteMakeRoutineActivity
import com.example.weekflex.Activity.categoryList
import com.example.weekflex.Data.Category
import com.example.weekflex.R

// CompleteMakeRoutineActivity의 카테고리 리스트 어댑터
class RoutineCategoryListAdapter (val activity: CompleteMakeRoutineActivity, var routineCategoryList: List<Category>)
    :RecyclerView.Adapter<RoutineCategoryListAdapter.ViewHolder>(){

    var user_id:Int?=null
    var selectedId = 0
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //category_list_item_view의 카테고리 이름
        val categoryTitle : TextView = itemView.findViewById(R.id.category_name_categoryList)
        val content: ConstraintLayout = itemView.findViewById(R.id.categoryList_itemView)
        val underline:View = itemView.findViewById(R.id.category_underline_categoryList)
        val container:ConstraintLayout=itemView.findViewById(R.id.categoryList_itemView)

        fun bind(position: Int){

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoutineCategoryListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item_view,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return routineCategoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitle.setText(routineCategoryList.get(position).categoryName)
        Log.d("msg",routineCategoryList.get(position).categoryId.toString())
        if(routineCategoryList.get(position).categoryId==selectedId){
            Log.d("msg","visible")
            holder.underline.visibility = View.VISIBLE
            holder.categoryTitle.setTypeface(null, Typeface.BOLD);
        }else{
            Log.d("msg","invisible")
            holder.underline.visibility = View.INVISIBLE
            holder.categoryTitle.setTypeface(null, Typeface.NORMAL);
        }
        holder.bind(position)

        holder.container.setOnClickListener {
            selectedId=routineCategoryList.get(position).categoryId
            notifyDataSetChanged()
        }
    }

}