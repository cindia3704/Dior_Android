package com.example.weekflex.Network

import android.app.Application
import android.content.Context
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Todo
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication : Application(){
    lateinit var retrofitService:RetrofitService
    //var token = ""

    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSdk.init(this,"a6e7d1dfcdab3e23056bbced8c2e7097")
    }
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
        var currentCategory: ArrayList<Category> = ArrayList() //Category("",0)
        var currentTodo: ArrayList<Todo> = ArrayList()
        var selectCategory: ArrayList<Category> = ArrayList()
    }


}