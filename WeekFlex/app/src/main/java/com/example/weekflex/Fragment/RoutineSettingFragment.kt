package com.example.weekflex.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weekflex.R

class RoutineSettingFragment : Fragment() {
    private var userId: Int? = 1
    companion object {
        fun newInstance(userId: Int): RoutineSettingFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
            var fragment = RoutineSettingFragment()
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
        return inflater.inflate(R.layout.routine_setting_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
