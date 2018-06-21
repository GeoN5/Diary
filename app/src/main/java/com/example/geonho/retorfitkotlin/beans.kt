package com.example.geonho.retorfitkotlin

data class User(val username : String,val password :String)

data class Diary(var _id : String?, var title : String,var content:String ,var username : String ,var date : String?)

data class Result(val success : Boolean,val message : String)

data class Response(val result:Result)

data class Auth(val token:String)

data class LoginResponse(val result: Result,val user :User,val auth:Auth)