package com.example.weekflex.Network

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Todo
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.kakao.sdk.common.KakaoSdk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GlobalApplication : Application(){
    lateinit var retrofitService:RetrofitService
    val baseUrl = "http://dev.weekflex.com/"


    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        instance = this
        createRetrofit()
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


    fun createRetrofit(){
        // 우리 통신이 나갈때 통신을 가로채서 original 변수에 잡아두고 이를 개조!
        // 원래 나가려던 통신데 헤더 달아주기!
        val header = Interceptor{
            val original = it.request()

            if(checkIsLogin()==false){
                // 로그인 되어있지 있는 경우
                return@Interceptor it.proceed(original)
            }else{
                // 로그인이 되어 있는 경우
                getUserToken()?.let{token ->
                    // getUserToken이 null이 아닌경우
                    val request = original.newBuilder()
                        .header("Authorization","token "+token)
                        .build()
                    it.proceed(request)
                }
            }

        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    // 로그인 했는지 안했는지 알아보는
    fun checkIsLogin():Boolean{
        val sharedPreference = getSharedPreferences("login_token",Context.MODE_PRIVATE)
        val token = sharedPreference.getString("login_token","")
        Log.d("checkisLogin: ",token.toString())
        if (token != null) {
            return token.isNotBlank()
        }else{
            return false
        }
    }

    // nullable -- b/c 로그인 안된경우 그냥 null로 할 수 있도록
    fun getUserToken():String?{
        val sharedPreference = getSharedPreferences("login_token",Context.MODE_PRIVATE)
        val token = sharedPreference.getString("login_token","")
        Log.d("getuserToken: ",token.toString())
        return if(token!=""){
            token
        } else null
    }

}