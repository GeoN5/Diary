package com.example.geonho.retorfitkotlin

data class Result(val success : Boolean,val message : String)

data class Response(val result:Result)

data class User(val username : String,val password :String)

data class Username(val username: String)

data class UsernameResponse(val result :Result,val user:Username)

data class Auth(val token:String)

data class UserChange(val username:Username,val token:Auth)

data class LoginResponse(val result: Result,val auth:Auth,val user:Username)

data class Diary(var title : String,var content:String ,var username : String) {
    var date : String = ""
    var _id : String = ""
}

data class DiaryListGet(val diaries : List<Diary>,val result: Result)

data class DetailResponse(val diary: Diary,val result: Result)

data class UserEditResult(val success:Boolean,val message: String,val username: String,val token: String )

data class UserEditResponse(val result:UserEditResult)


