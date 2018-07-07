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

    @DELETE("/users/{userName}")//계정삭제
    fun delete(@Path("userName")userName: String) : Call<Response>

}

interface DiaryService{
    @GET("/diaries/users/{userName}")//username diary 조회
    fun loadDiary(@Path("userName")userName:String) : Call<DiaryListGet>

    @GET("/diaries/{id}")//id조회
    fun detailDiary(@Path("id")id:String) : Call<DetailResponse>

    @POST("/diaries")//diary 작성
    fun writeDiary(@Body diary: Diary) : Call<Response>

    @PUT("/diaries/{id}")//diary 수정
    fun editDiary(@Path("id")id:String,@Body diary: Diary) : Call<Response>

    @DELETE("/diaries/{id}")//diary 삭제
    fun deleteDiary(@Path("id")id:String):Call<Response>
}
