package com.example.weekflex.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.weekflex.R

class LoginActivity : AppCompatActivity() {
    lateinit var kakaoLogin : ImageView
    lateinit var googleLogin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        setupListener()
    }

    fun initView(){
        kakaoLogin = findViewById(R.id.login_kakao)
        googleLogin = findViewById(R.id.login_google)
    }

    fun setupListener(){
        kakaoLogin.setOnClickListener{

        }

        googleLogin.setOnClickListener {

        }
    }

    // sharedPreference에 토큰 저장하는 함수
    fun saveUserToken(token:String, activity: Activity){
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token",token)
        editor.commit()
    }
}