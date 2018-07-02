package com.example.geonho.retorfitkotlin

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface services {
}

interface UserService{
    @POST("/sign")//로그인
    fun login(@Body user:User) : Call<LoginResponse>

    @POST("/users")//회원가입
    @Multipart
    fun register(@Part("data")user : User , @Part profile: MultipartBody.Part): Call<Response>

}

interface DiaryService{
    @GET("/diaries/users/{userName}")
    fun loadDiary(@Path("userName")userName:String) : Call<DiaryListGet>
}