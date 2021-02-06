package com.example.weekflex.Activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weekflex.R
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.common.util.Utility


class LoginActivity : AppCompatActivity() {
    lateinit var kakaoLogin : ImageView
    lateinit var googleLogin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        setupListener()
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
    }


    fun initView(){
        kakaoLogin = findViewById(R.id.login_kakao)
        googleLogin = findViewById(R.id.login_google)
    }

    fun setupListener(){
        kakaoLogin.setOnClickListener{
            kakaoLogin()
        }

        googleLogin.setOnClickListener {

        }
    }
    private fun kakaoLogin(){
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        val kakaoUserEmail = user.kakaoAccount?.email?: "null"
                        val kakaoUsername = user.kakaoAccount?.profile?.nickname?:"null"
                        val signupType = "kakao"
                        val accessToken = token
                        Log.d("msg","useremail: "+ kakaoUserEmail)
                        Log.d("msg","user name: "+ kakaoUsername)
                        Log.d("msg","token: "+ (token))
//                        checkIsRegisteredSocialLogin(kakaoUserEmail,token.toString())
                    }
                }
            }
        }

        if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
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