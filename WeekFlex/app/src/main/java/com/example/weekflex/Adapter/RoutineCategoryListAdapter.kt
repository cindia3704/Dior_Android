package com.example.weekflex.Adapter

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.CompleteMakeRoutineActivity
import com.example.weekflex.Activity.categoryList
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R

// CompleteMakeRoutineActivity의 카테고리 리스트 어댑터
class RoutineCategoryListAdapter(val activity: CompleteMakeRoutineActivity,
                                 var routineCategoryList: List<Category>,
                                 var searchedRoutine:String)
    : RecyclerView.Adapter<RoutineCategoryListAdapter.ViewHolder>() {

    var lambdaList : List<((Int) -> Unit)> = listOf()
    var user_id: Int? = null
    // null propagation 널 전파
    var selectedId : Int = routineCategoryList.getOrNull(0)?.categoryId ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //category_list_item_view의 카테고리 이름
        val categoryTitle: TextView = itemView.findViewById(R.id.category_name_categoryList)
        val categoryColor: ImageView = itemView.findViewById(R.id.category_starimg_categoryList)
        val content: ConstraintLayout = itemView.findViewById(R.id.categoryList_itemView)
        val underline: View = itemView.findViewById(R.id.category_underline_categoryList)
        val container: ConstraintLayout = itemView.findViewById(R.id.categoryList_itemView)

        fun bind(position: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return routineCategoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = routineCategoryList.get(position)
        if(!searchedRoutine.isNullOrBlank()){
            selectedId = 0
        }else {
            val selected = category.categoryId == selectedId

            holder.categoryColor.setImageResource(R.drawable.graystar)
            holder.underline.visibility = if (selected) View.VISIBLE else View.INVISIBLE
            holder.categoryTitle.setText(category.categoryName)
            holder.categoryTitle.setTypeface(
                null,
                if (selected) Typeface.BOLD else Typeface.NORMAL
            );

            Log.d(
                "msg",
                "current : ${category.categoryId} selected : $selectedId visible : $selected"
            )

            holder.bind(position)

            holder.container.setOnClickListener {
                selectedId = category.categoryId
                notifyDataSetChanged()
                for (lambda in lambdaList) {
                    lambda.invoke(selectedId)
                }
            }
        }
    }

    fun changeSearchedRoutine(item : String){
        searchedRoutine=item
        notifyDataSetChanged()
    }


}