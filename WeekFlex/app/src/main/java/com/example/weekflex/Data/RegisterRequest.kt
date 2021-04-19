package com.example.weekflex.Data

data class RegisterRequest(
    val accessToken: String,
    val email: String,
    val name: String,
    val signupType: String
)
