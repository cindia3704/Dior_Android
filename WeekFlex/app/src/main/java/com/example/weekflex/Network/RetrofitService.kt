package com.example.weekflex.Network

import com.example.weekflex.Data.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("api/v1/dummy")
    @FormUrlEncoded
    fun register(
        @Field("accessToken") accessToken: String,
        @Field("email") email: String,
        @Field("name") name:String,
        @Field("signupType") signupType: String
    ) : Call<RegisterResponse>

}