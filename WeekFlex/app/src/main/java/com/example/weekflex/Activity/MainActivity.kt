package com.example.weekflex.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.example.weekflex.Adapter.NavigationTabAdapter
import com.example.weekflex.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    var userId: Int =1
    private lateinit var fragmentView : ViewPager
    private lateinit var navigationBar: TabLayout
    private lateinit var fragmentAdapter: NavigationTabAdapter
    private lateinit var navigationBarLayout: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        makeNavigationBar()
    }

    fun initView(){
        fragmentView = findViewById(R.id.fragmentViewMain)
        navigationBar =findViewById(R.id.navigationBar)
        fragmentAdapter = NavigationTabAdapter(supportFragmentManager,3,userId)
        navigationBarLayout = this.layoutInflater.inflate(R.layout.navibation_bar,null,false)
        fragmentView.adapter=fragmentAdapter
        navigationBar.setupWithViewPager(fragmentView)
        fragmentView.currentItem=0
    }

    private fun makeNavigationBar(){
        navigationBar.getTabAt(0)!!.customView= navigationBarLayout.findViewById(R.id.mainTab) as RelativeLayout
        navigationBar.getTabAt(1)!!.customView = navigationBarLayout.findViewById(R.id.calendarTab)as RelativeLayout
        navigationBar.getTabAt(2)!!.customView = navigationBarLayout.findViewById(R.id.routineTab)as RelativeLayout
    }



}