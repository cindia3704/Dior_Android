package com.example.weekflex.Network

import com.example.weekflex.Data.RegisterRequest
import com.example.weekflex.Data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("api/v1/users/socialLogin")
        fun register(
       @Body registerRequest: RegisterRequest
    ) : Call<RegisterResponse>
}