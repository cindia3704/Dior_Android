package com.example.weekflex.Network

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication : Application(){
    lateinit var retrofitService:RetrofitService
    //var token = ""
    // test위해서 일단 이전에 만든 서버로 연결해 둠!
    val baseUrl = "https://capstonebutton.kro.kr:9000"

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
    }


}