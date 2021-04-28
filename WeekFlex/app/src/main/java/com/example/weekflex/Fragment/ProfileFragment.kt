package com.example.weekflex.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.R

class ProfileFragment : Fragment() {
    private var userId: Int? = 1
    private lateinit var userName:TextView
    private lateinit var userLoginChannel:TextView
    private lateinit var userImage:ImageView
    private lateinit var categorySettingBtn:TextView
    private lateinit var taskSettingBtn: TextView
    private lateinit var logoutBtn: TextView
    private lateinit var withdrawalBtn: TextView
    private lateinit var inquiryBtn: TextView
    private lateinit var version: TextView

    companion object {
        fun newInstance(userId: Int): ProfileFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
            var fragment = ProfileFragment()
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
        val view = inflater.inflate(R.layout.routine_setting_fragment, container, false)
        userName = view.findViewById(R.id.myProfile_userName)
        userLoginChannel = view.findViewById(R.id.myProfile_login_channel)
        userImage = view.findViewById(R.id.myProfile_profileImg)
        categorySettingBtn = view.findViewById(R.id.myProfile_categorySettings)
        taskSettingBtn = view.findViewById(R.id.myProfile_taskSettings)
        logoutBtn = view.findViewById(R.id.logout)
        withdrawalBtn = view.findViewById(R.id.account_withdrawal)
        inquiryBtn = view.findViewById(R.id.inquiry)
        version = view.findViewById(R.id.version_num)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = view.findNavController()
        setListener()
    }

    fun setListener(){
        categorySettingBtn.setOnClickListener {
            (activity as MainActivity?)?.changeFragment(2)
//            navController.navigate(R.id.action_profileFragment_to_categorySettingFragment)
//            Navigation.createNavigateOnClickListener(R.id.action_profileFragment_to_categorySettingFragment,null)
        }
        taskSettingBtn.setOnClickListener {

        }
        logoutBtn.setOnClickListener {

        }
        withdrawalBtn.setOnClickListener {

        }
        inquiryBtn.setOnClickListener {

        }

    }

}
