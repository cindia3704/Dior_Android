package com.example.weekflex.Data

import retrofit2.http.Part

data class RegisterRequest(
    val accessToken: String,
    val email: String,
    val name:String,
    val signupType: String
)