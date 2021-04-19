package com.example.weekflex.Adapter

import android.graphics.Color
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
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.categoryToStarImage
import com.example.weekflex.R

// CompleteMakeRoutineActivity의 카테고리 리스트 어댑터
class RoutineCategoryListAdapter(
    val activity: CompleteMakeRoutineActivity,
    var routineCategoryList: List<Category>,
    var searchedRoutine: String
) :
    RecyclerView.Adapter<RoutineCategoryListAdapter.ViewHolder>() {

    var lambdaList: List<((Int) -> Unit)> = listOf()
    var user_id: Int? = null
    // null propagation 널 전파
    private var selectedId: Int = routineCategoryList.getOrNull(0)?.categoryId ?: 0
    // 검색중이면 0으로 바꿔주고 유지한다.
    val isSearching: Boolean get() = searchedRoutine.isNullOrBlank() == false

    fun setMySelectedId(categoryId: Int) {
        selectedId = if (isSearching) 0 else categoryId
        for (lambda in lambdaList) {
            lambda.invoke(getMySelectedId())
        }
    }
    fun getMySelectedId(): Int {
        return if (isSearching) 0 else selectedId
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // category_list_item_view의 카테고리 이름
        val categoryTitle: TextView = itemView.findViewById(R.id.category_name_categoryList)
        val categoryColor: ImageView = itemView.findViewById(R.id.category_starimg_categoryList)
        val content: ConstraintLayout = itemView.findViewById(R.id.categoryList_itemView)
        val underline: View = itemView.findViewById(R.id.category_underline_categoryList)
        val container: ConstraintLayout = itemView.findViewById(R.id.categoryList_itemView)

        var category: Category? = null
        var selected: Boolean = false

        fun bind(c: Category, s: Boolean) {
            category = c
            selected = s

            render()
        }

        fun render() {
            fun setCategoryTitle(selected: Boolean) {
                categoryTitle.text = category?.categoryName ?: "ERROR"

                val typeFace = if (selected) Typeface.BOLD else Typeface.NORMAL
                val textColor = Color.parseColor(if (selected) "#181818" else "#666666")

                categoryTitle.setTypeface(null, typeFace)
                categoryTitle.setTextColor(textColor)
            }

            underline.visibility = if (selected) View.VISIBLE else View.INVISIBLE

            setCategoryTitle(selected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item_view, parent, false)

        return ViewHolder(view)
            .also {
                val viewHolderLambda = {
                    Log.d("test", "$isSearching $searchedRoutine")
                    val clickedId = it.category?.categoryId ?: 0
                    setMySelectedId(clickedId)

                    notifyDataSetChanged()
                }

                it.container.setOnClickListener { viewHolderLambda.invoke() }
            }
    }

    override fun getItemCount(): Int {
        return routineCategoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = routineCategoryList.get(position)
        val selected = category.categoryId == getMySelectedId()

        holder.bind(category, selected)

        val drawableStar = categoryToStarImage[category.categoryId] ?: R.drawable.graystar
        holder.categoryColor.setImageResource(drawableStar)

        Log.d("msg", "current : ${category.categoryId} selected : ${getMySelectedId()} visible : $selected")
    }

    fun changeSearchedRoutine(item: String) {
        searchedRoutine = item
        notifyDataSetChanged()
    }
}
