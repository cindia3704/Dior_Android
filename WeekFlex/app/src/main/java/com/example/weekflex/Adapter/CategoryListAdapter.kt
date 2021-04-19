package com.example.weekflex.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Category
import com.example.weekflex.Network.GlobalApplication
import com.example.weekflex.R

class CategoryListAdapter(val context: Context) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    lateinit var CategoryList: ArrayList<Category>
    lateinit var selectedCategory: Category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 껍데기
        val view = LayoutInflater.from(context).inflate(R.layout.one_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int { // 데이터 개수 체크
        return CategoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = CategoryList.get(position).categoryName
        holder.categoryColor.setBackgroundColor(CategoryList.get(position).categoryColor)
        holder.itemView.setOnClickListener {
            selectedCategory = CategoryList.get(position)
            GlobalApplication.selectCategory.add(selectedCategory)
            Toast.makeText(context, "${selectedCategory.categoryName}", Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView
        val categoryColor: ImageView
        init {
            categoryName = itemView.findViewById(R.id.recycler_one_title)
            categoryColor = itemView.findViewById(R.id.recycler_one_color)
        }
    }

    fun refreshData(inputData: ArrayList<Category>) {
        CategoryList = inputData
        notifyDataSetChanged()
    }
}
