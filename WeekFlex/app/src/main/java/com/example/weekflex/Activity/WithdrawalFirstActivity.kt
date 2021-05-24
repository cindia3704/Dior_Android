package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.weekflex.R

class WithdrawalFirstActivity : AppCompatActivity() {
    private lateinit var agreeCheck:ImageView
    private lateinit var nextBtn:TextView
    private lateinit var gobackBtn:ImageView
    private var agree = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal_first_acitivity)
        initView()
        setListener()
    }
    private fun initView(){
        agreeCheck = findViewById(R.id.agreeBtn_withdrawal1)
        nextBtn = findViewById(R.id.next_withdrawal1)
        gobackBtn = findViewById(R.id.goBack_withdrawal1)
    }
    private fun setListener(){
        agreeCheck.setOnClickListener {
            agree = !agree
            var img = if(agree) R.drawable.withdrawal_black_check else R.drawable.withdrawal_white_check
            var btnImg = if(agree) R.drawable.black_task_corner_background else R.drawable.gray_border_background

            agreeCheck.setImageResource(img)
            nextBtn.setBackgroundResource(btnImg)
        }
        nextBtn.setOnClickListener {
            if(agree){
                val intent = Intent(this@WithdrawalFirstActivity,WithdrawalSecondActivity::class.java)
                startActivity(intent)
            }
        }
        gobackBtn.setOnClickListener {
            finish()
        }

    }
}