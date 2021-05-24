package com.example.weekflex.Adapter

import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.categoryColors
import com.example.weekflex.R


class CategoryColorListAdapter(
    val inflater: LayoutInflater,
    val availableColors: List<Int>,
    val selectedColor: Int?
) : RecyclerView.Adapter<CategoryColorListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var colorCircle: ImageView
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
        Log.d("color SIZE : ",availableColors.size.toString())
        return availableColors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categoryColors[availableColors[position]]?.let {
            Log.d("color: ",it.toString())
            holder.colorCircle.setBackgroundResource(it)
        }

        holder.checkImg.visibility = if(availableColors[position] == selectedColor) View.VISIBLE else View.INVISIBLE


//        categoryToStarImage[CategoryBottomFragment.category.categoryColor]?.let { categoryImg.setImageResource(it) }


    }
}
