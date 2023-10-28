package com.example.simplechat.data.api

import com.example.simplechat.data.models.User
import com.example.simplechat.data.models.response.BaseResponse
import com.example.simplechat.data.models.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login/")
    fun login(@Body body: Map<String, String>): Call<BaseResponse<User?>>

    @POST("auth/signup/")
    fun signUp(@Body body: Map<String, String>) : Call<BaseResponse<User?>>
}