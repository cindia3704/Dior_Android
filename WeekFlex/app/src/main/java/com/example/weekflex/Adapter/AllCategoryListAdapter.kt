package com.example.weekflex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Category
import com.example.weekflex.Fragment.CategorySettingFragment
import com.example.weekflex.R

class AllCategoryListAdapter(
    var context: CategorySettingFragment,
    var categoryList: List<Category>
): RecyclerView.Adapter<AllCategoryListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryStarImg:ImageView
        var categoryName:TextView

        init {
           categoryStarImg = itemView.findViewById(R.id.all_category_category_image)
            categoryName = itemView.findViewById(R.id.all_category_category_name)
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
    }
}