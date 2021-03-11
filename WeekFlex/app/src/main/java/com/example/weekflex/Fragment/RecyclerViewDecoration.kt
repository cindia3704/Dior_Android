package com.example.weekflex.Fragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerDecoration(private val divWidth : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val LAST_ITEM_POSITION = 6
        if(parent.getChildAdapterPosition(view) == LAST_ITEM_POSITION){
            outRect.right=0
        }else {
            outRect.right += divWidth
        }


    }

}