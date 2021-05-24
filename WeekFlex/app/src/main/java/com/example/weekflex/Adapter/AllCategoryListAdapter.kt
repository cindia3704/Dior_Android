package com.example.weekflex.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Routine
import com.example.weekflex.Data.Task
import com.example.weekflex.Fragment.CategoryBottomFragment
import com.example.weekflex.Fragment.CategorySettingFragment
import com.example.weekflex.R

class AllCategoryListAdapter(
//    var context: CategorySettingFragment,
    val inflater: LayoutInflater,
    var categoryList: List<Category>,
    val onClickCategoryMenuButton: (Category) -> Unit
): RecyclerView.Adapter<AllCategoryListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryStarImg:ImageView
        val categoryName:TextView
        val moreBtn: ImageView
        val view: ConstraintLayout
        init {
            categoryStarImg = itemView.findViewById(R.id.all_category_category_image)
            categoryName = itemView.findViewById(R.id.all_category_category_name)
            moreBtn = itemView.findViewById(R.id.all_category_more_btn)
            view = itemView.findViewById(R.id.all_category_one_item_container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = categoryList[position]
        holder.categoryName.text = category.categoryName
        holder.categoryStarImg.setImageResource(R.drawable.yellowstar)
        holder.moreBtn.setOnClickListener {
            onClickCategoryMenuButton(category)
        }
    }
}