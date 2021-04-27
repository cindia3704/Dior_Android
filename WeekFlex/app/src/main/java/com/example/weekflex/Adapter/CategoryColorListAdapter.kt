package com.example.weekflex.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.R

class CategoryColorListAdapter(
    val inflater: LayoutInflater,
    val availableColors: List<Int>,
    val selectedColor : Int
) : RecyclerView.Adapter<CategoryColorListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorCircle: ImageView
        val checkImg: ImageView

        init {
            colorCircle = itemView.findViewById(R.id.category_color)
            checkImg = itemView.findViewById(R.id.category_color_check_img)
        }
    }

    // 생성될 때 해주는거
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.category_colors_list_item
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return availableColors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}
