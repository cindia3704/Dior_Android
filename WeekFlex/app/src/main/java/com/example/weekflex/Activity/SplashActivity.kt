package com.example.weekflex.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.weekflex.R

class SplashActivity : AppCompatActivity() {
    lateinit var splashBtn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
        setupListener()
    }
    fun initView(){
        splashBtn = findViewById(R.id.splash_comment)
    }
    fun setupListener(){
        splashBtn.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}