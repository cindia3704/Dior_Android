package com.example.weekflex.Activity

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    fun navigateWithFinish(intent: Intent) {
        navigateWithoutFinish(intent)
        finish()
    }

    fun navigateWithoutFinish(intent: Intent) {
        startActivity(intent)
    }

    fun makeToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
