package com.example.weekflex.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.AddCategory
import com.example.weekflex.Adapter.CategoryListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Network.GlobalApplication
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var categoryListAdapter : CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        categoryListAdapter = CategoryListAdapter(context!!)

        view.findViewById<RecyclerView>(R.id.categoryList).let {
            it.adapter = categoryListAdapter
            it.layoutManager = LinearLayoutManager(context!!)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.addCategory)?.setOnClickListener {
            activity?.let{
                val addIntent = Intent(it, AddCategory::class.java)
                startActivity(addIntent)
            }
        }
    }

    override fun onResume(){
        super.onResume()
        categoryListAdapter.refreshData(GlobalApplication.currentCategory)
    }


}