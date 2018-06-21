package com.example.geonho.retorfitkotlin

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface services {
}

interface UserService{
    @POST("/sign")//로그인
    fun login(@Body user:User) : Call<LoginResponse>

    @POST("/users")//회원가입
    @Multipart
    fun register(@Part("data")user : User , @Part profile: MultipartBody.Part): Call<Response>
}