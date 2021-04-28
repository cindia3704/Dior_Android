package com.example.weekflex.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.R

class ProfileTopFragment : Fragment() {
    private var userId: Int? = 1

    companion object {
        fun newInstance(userId: Int): ProfileTopFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
            var fragment = ProfileTopFragment()
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
        val view = inflater.inflate(R.layout.profile_top_fragment, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.changeFragment(1)

    }
}