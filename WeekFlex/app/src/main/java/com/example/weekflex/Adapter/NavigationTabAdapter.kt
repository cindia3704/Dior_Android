package com.example.weekflex.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.weekflex.Fragment.CalendarFragment
import com.example.weekflex.Fragment.ProfileFragment
import com.example.weekflex.Fragment.TodoMainFragment

class NavigationTabAdapter(fragmentManager: FragmentManager, val fragmentCount: Int, val userId: Int) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TodoMainFragment.newInstance(userId)
            1 -> return CalendarFragment.newInstance(userId)
            else -> return ProfileFragment.newInstance(userId)
        }
    }

    override fun getCount(): Int {
        return fragmentCount
    }
}
