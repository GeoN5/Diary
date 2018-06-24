package com.example.geonho.retorfitkotlin

data class Result(val success : Boolean,val message : String)

data class Response(val result:Result)

data class User(val username : String,val password :String)

data class Username(val username: String)

data class UsernameResponse(val result :Result,val user:Username)

data class Auth(val token:String)

data class UserChange(val username:Username,val token:Auth)

data class LoginResponse(val result: Result,val auth:Auth,val user:Username)

data class Diary(var _id: String?,var title : String,var content:String ,var username : String ,var date : String?)

data class  NoidDiary(var title : String,var content:String ,var username : String ,var date : String?)

data class UserAboutResponse(val result: Result,val diaries: Diary)

data class IdCheckResponse(val diary: NoidDiary,val result: Result)

data class Write(var title:String, var content:String,var username: String)

data class WriteChange(val auth: Auth,val write: Write)
