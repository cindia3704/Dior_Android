package com.example.weekflex.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weekflex.Data.RegisterRequest
import com.example.weekflex.Data.RegisterResponse
import com.example.weekflex.Network.GlobalApplication
import com.example.weekflex.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {
    lateinit var kakaoLogin: ImageView
    lateinit var googleLogin: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        setupListener()
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
    }

    fun initView() {
        kakaoLogin = findViewById(R.id.login_kakao)
        googleLogin = findViewById(R.id.login_google)
    }

    fun setupListener() {
        kakaoLogin.setOnClickListener {
            kakaoLogin()
        }
        googleLogin.setOnClickListener {
            googleLogin()
        }
    }
    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "????????? ?????? ???(?????? ??????)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "???????????? ?????? ???", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "?????? ????????? ???????????? ?????? ????????? ??? ?????? ??????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "?????? ???????????? ??????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "???????????? ?????? scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "????????? ???????????? ??????(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "?????? ?????? ????????? ??????", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "?????? ??????", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        val kakaoUserEmail = user.kakaoAccount?.email ?: "null"
                        val kakaoUsername = user.kakaoAccount?.profile?.nickname ?: "null"
                        val signupType = "KAKAO"
                        val accessToken = token.accessToken
                        Log.d("msg", "useremail: " + kakaoUserEmail)
                        Log.d("msg", "user name: " + kakaoUsername)
                        Log.d("msg", "token: " + (token).accessToken)
                        val registerRequest = RegisterRequest(accessToken, kakaoUserEmail, kakaoUsername, signupType)
                        tryLogin(registerRequest)
                    }
                }
            }
        }

        if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun googleLogin() {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("849170160003-arrpbh8jee6dhgqfhvuiereb2pf44mbn.apps.googleusercontent.com")
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        login_google.visibility = View.VISIBLE
        login_google.setSize(SignInButton.SIZE_ICON_ONLY)

        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account = completedTask.getResult(ApiException::class.java)
        val googleUserName = account?.getDisplayName()
        val googleUserEmail = account?.getEmail()
        val token = account?.getIdToken()
        val signupType = "GOOGLE"
        Log.d("msg", "user name: " + googleUserName)
        Log.d("msg", "user email: " + googleUserEmail)
        Log.d("msg", "token: " + (token))
        if (token == null || googleUserEmail == null || googleUserName == null) {
            Toast.makeText(this@LoginActivity, "ERROR! Fields are null", Toast.LENGTH_SHORT).show()
        } else {
            val registerRequest =
                RegisterRequest(token, googleUserEmail, googleUserName, signupType)
            tryLogin(registerRequest)
        }
        login_google.visibility = View.GONE
    } catch (e: ApiException) {
        Log.w("failed", "signInResult:failed code=" + e.statusCode)
        login_google.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    // sharedPreference??? ?????? ???????????? ??????
    fun saveUserToken(token: String?, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.commit()
    }

    fun tryLogin(registerRequest: RegisterRequest) {
        saveUserToken(null, this@LoginActivity)
        (application as GlobalApplication).retrofitService.register(
            registerRequest
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("msg", "failed!")
                Log.d("msg", "!!" + registerRequest.accessToken)
                Log.d("msg", "!!" + registerRequest.email)

                Toast.makeText(this@LoginActivity, "???????????? ??????", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {

                if (response.isSuccessful) {
                    Log.d("msg", "successful!")
                    Log.d("msg", "!!" + registerRequest.accessToken)
                    Log.d("msg", "!!" + registerRequest.email)
                    val responseFromServer = response.body()
                    if (responseFromServer?.data == null) {
                        Toast.makeText(this@LoginActivity, "response from server is null", Toast.LENGTH_SHORT).show()
                    } else {
                        val token = responseFromServer.data
                        Log.d("response token: ", token.toString())
                        saveUserToken(token, this@LoginActivity)
                        val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intentToMain)
                        finish()
                    }
                } else {
                    Log.e("request header : ", call.request().headers().toString())
                    Log.e("request body : ", call.request().body().toString())
                    Log.d("response msg: ", response.message())
                    Log.d("response err body: ", response.errorBody().toString())
                    Log.d("response code: ", response.code().toString())
                    Toast.makeText(this@LoginActivity, "???????????? ??????????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
