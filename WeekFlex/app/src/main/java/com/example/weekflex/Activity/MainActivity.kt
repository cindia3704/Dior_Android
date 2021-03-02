package com.example.weekflex.Activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.weekflex.Adapter.NavigationTabAdapter
import com.example.weekflex.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navibation_bar.*


class MainActivity : AppCompatActivity() {
    var userId: Int =1
    private lateinit var fragmentView : ViewPager
    private lateinit var navigationBar: TabLayout
    private lateinit var fragmentAdapter: NavigationTabAdapter
    private lateinit var navigationBarLayout: View
    private lateinit var mainTabImg : ImageView
    private lateinit var calendarTabImg : ImageView
    private lateinit var routineTabImg : ImageView

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
        mainTabImg = navigationBarLayout.findViewById(R.id.mainTab_img)
        routineTabImg =  navigationBarLayout.findViewById(R.id.routineTab_img)
        calendarTabImg =  navigationBarLayout.findViewById(R.id.calendarTab_img)
        fragmentView.adapter=fragmentAdapter
        navigationBar.setupWithViewPager(fragmentView)
        fragmentView.currentItem=0

    }

    private fun makeNavigationBar(){
        navigationBar.getTabAt(0)!!.customView= navigationBarLayout.findViewById(R.id.mainTab) as RelativeLayout
        navigationBar.getTabAt(1)!!.customView = navigationBarLayout.findViewById(R.id.calendarTab)as RelativeLayout
        navigationBar.getTabAt(2)!!.customView = navigationBarLayout.findViewById(R.id.routineTab)as RelativeLayout

        // 탭 바꿀때마다 이미지 교체
        navigationBar.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    mainTabImg.setImageResource(R.drawable.home)
                    calendarTabImg.setImageResource(R.drawable.calendar)
                    routineTabImg.setImageResource(R.drawable.profile)
                }
                if (tab.position == 1) {
                    calendarTabImg.setImageResource(R.drawable.calendarselected)
                    mainTabImg.setImageResource(R.drawable.homeunselected)
                    routineTabImg.setImageResource(R.drawable.profile)
                }
                if (tab.position == 2) {
                    routineTabImg.setImageResource(R.drawable.profileselected)
                    mainTabImg.setImageResource(R.drawable.homeunselected)
                    calendarTabImg.setImageResource(R.drawable.calendar)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    public fun makeDarkTabView(){
//        navigationBar_opacityView_home.visibility=View.VISIBLE
//        navigationBar_opacityView_calendar.visibility=View.VISIBLE
//        navigationBar_opacityView_profile.visibility=View.VISIBLE
        navigationOpacity.visibility=View.VISIBLE
    }

    public fun undoDarkTabView(){
//        navigationBar_opacityView_home.visibility=View.GONE
//        navigationBar_opacityView_calendar.visibility=View.GONE
//        navigationBar_opacityView_profile.visibility=View.GONE
        navigationOpacity.visibility=View.GONE

    }




}