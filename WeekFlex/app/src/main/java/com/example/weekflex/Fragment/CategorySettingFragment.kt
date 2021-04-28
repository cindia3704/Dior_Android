package com.example.weekflex.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.Adapter.AllCategoryListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Task
import com.example.weekflex.R
val categoryLists = listOf(
    Category(1, "언어", 0, listOf(
        Task("Speaking", 3, "10:00AM", "1:00PM", true, listOf("월", "화")),
        Task("전화영어", 2, "1:00PM", "1:30PM", false, listOf("수")),
        Task("스피킹", 1, "5:00PM", "6:00PM", true, listOf("금", "일"))
    )),
    Category(2, "Coding", 0, listOf(
        Task("CS", 3, "10:00AM", "1:00PM", false, listOf("수")),
        Task("알고리즘", 2, "1:00PM", "1:30PM", true, listOf("월", "화"))
    )),
    Category(3, "운동", 0, listOf(
        Task("코어", 1, "10:00AM", "1:00PM", false, listOf("일")),
        Task("하체", 2, "1:00PM", "1:30PM", false, listOf("토"))
    ))
)
class CategorySettingFragment : Fragment() {
    private var userId: Int? = 1
//    lateinit var navController: NavController
    private lateinit var backBtn:ImageView
    private lateinit var categoryList: RecyclerView
    companion object {
        fun newInstance(userId: Int): CategorySettingFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
            var fragment = CategorySettingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = arguments?.getInt("userId")
        Log.d("msg","IN CATEGORY SETTING FRAG!")
        val view = inflater.inflate(R.layout.category_setting_fragment, container, false)
        backBtn = view.findViewById(R.id.category_setting_goback)
        categoryList = view.findViewById(R.id.category_setting_categorylist)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList.adapter = AllCategoryListAdapter(this,categoryLists)
        setListener()
    }

    fun setListener(){
        backBtn.setOnClickListener {
            (activity as MainActivity?)?.changeFragment(1)
        }
    }

}