package com.example.weekflex.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weekflex.Activity.AddTodoActivity
import com.example.weekflex.Activity.MainActivity
import com.example.weekflex.Activity.WithdrawalFirstAcitivity
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
            (activity as MainActivity?)?.changeFragment(2,null)
        }
        taskSettingBtn.setOnClickListener {

        }
        logoutBtn.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)

            builder.setMessage("로그아웃 하시겠습니까?")

            builder.setPositiveButton("로그아웃") { dialog, which ->
                // TODO: 서버 연결시 로그아웃 요청 보내기
            }

            builder.setNegativeButton("그만두기") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: android.app.AlertDialog = builder.create()
            dialog.show()

            this.context?.let {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.gray_4))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.color6))
            }
        }
        withdrawalBtn.setOnClickListener {
            val intent = Intent(this.context, WithdrawalFirstAcitivity::class.java)
            startActivity(intent)
        }
        inquiryBtn.setOnClickListener {

        }

    }

}
