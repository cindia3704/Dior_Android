package com.example.weekflex.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.weekflex.Fragment.CategoryBottomFragment
import com.example.weekflex.R

class WithdrawalSecondActivity : AppCompatActivity() {
    private lateinit var notUseBtn:ImageView
    private lateinit var inconvenienceBtn:ImageView
    private lateinit var vipBtn:ImageView
    private lateinit var otherServiceBtn:ImageView
    private lateinit var etcBtn:ImageView
    private lateinit var etcReason : EditText
    private lateinit var gobackBtn:ImageView
    private lateinit var withdrawBtn:TextView
    private var selected = 0

    val withdrawReasons = mapOf <Int,Int>(
        1 to R.id.notUseBtn_withdrawal2,
        2 to R.id.inconvenienceBtn_withdrawal2,
        3 to R.id.vipBtn_withdrawal2,
        4 to R.id.otherServiceBtn_withdrawal2,
        5 to R.id.etcBtn_withdrawal2
    )

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal_second)
        initView()
        setListener()
    }

    private fun initView(){
        notUseBtn = findViewById(R.id.notUseBtn_withdrawal2)
        inconvenienceBtn = findViewById(R.id.inconvenienceBtn_withdrawal2)
        vipBtn = findViewById(R.id.vipBtn_withdrawal2)
        otherServiceBtn = findViewById(R.id.otherServiceBtn_withdrawal2)
        etcBtn = findViewById(R.id.etcBtn_withdrawal2)
        etcReason = findViewById(R.id.etc_reason_withdrawal2)
        gobackBtn = findViewById(R.id.goBack_withdrawal2)
        withdrawBtn = findViewById(R.id.next_withdrawal2)
    }
    private fun setListener(){
        gobackBtn.setOnClickListener {
            finish()
        }
        withdrawBtn.setOnClickListener {
            if(selected!=0){
                //TODO: 계정 삭제! 서버
                if(selected == 5){
                    val reason = etcReason.text
                }
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

                builder.setMessage("탈퇴하기를 누르면 되돌릴 수 없습니다.\n" +
                        "정말 탈퇴 하시겠습니까?")

                builder.setPositiveButton("탈퇴하기") { dialog, which ->
                    // TODO: 서버 연결시 삭제 요청 보내기
                }

                builder.setNegativeButton("그만두기") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog: android.app.AlertDialog = builder.create()
                dialog.show()

                this?.let {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.gray_4))
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.color6))
                }
            }
        }

        notUseBtn.setOnClickListener {
            changeCheck(1)
        }
        inconvenienceBtn.setOnClickListener {
            changeCheck(2)
        }
        vipBtn.setOnClickListener {
            changeCheck(3)
        }
        otherServiceBtn.setOnClickListener {
            changeCheck(4)
        }
        etcBtn.setOnClickListener {
            changeCheck(5)
        }


    }


    private fun changeCheck(select: Int){
        val toggle = selected == select

        if(toggle){
            val btn: ImageView? = withdrawReasons[selected]?.let { findViewById(it) }
            btn?.setImageResource(R.drawable.withdrawal_white_check)
            selected = 0
        }
        else {
            val btn: ImageView? = withdrawReasons[selected]?.let { findViewById(it) }
            btn?.setImageResource(R.drawable.withdrawal_white_check)
            selected = select
        }

        setSelectedButton(selected)

    }

    private fun setSelectedButton(selected: Int){
        if(selected == 5){
            etcReason.visibility = View.VISIBLE
        }else{
            etcReason.visibility = View.INVISIBLE
        }

        val newBtn: ImageView? = withdrawReasons[selected]?.let { findViewById(it) }
        newBtn?.setImageResource(R.drawable.withdrawal_black_check)


        val img = if(selected==0) R.drawable.gray_border_background else R.drawable.black_task_corner_background
        withdrawBtn.setBackgroundResource(img)
    }
}