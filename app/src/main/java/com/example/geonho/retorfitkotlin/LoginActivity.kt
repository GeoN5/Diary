package com.example.geonho.retorfitkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
    }

    fun setListeners() {
        registerTextView.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            var user: User = User(usernameEditText.text.toString(), passwordEditText.text.toString())
            var userService: UserService = RetrofitUtil.retrofit.create(UserService::class.java)
            var call: Call<LoginResponse> = userService.login(user)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "네트워크 에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
                    if (response.body() != null && response.body()!!.result.success) {
                        Toast.makeText(applicationContext, "로그인에 성공하였습니다!", Toast.LENGTH_LONG).show()
                        SharedPreferenceUtil.saveData(applicationContext, "token", response.body()!!.auth.token)
                        SharedPreferenceUtil.saveData(applicationContext, "username", response.body()!!.user.username)
                        Log.d("token", SharedPreferenceUtil.getData(applicationContext, "token") + "is saved")
                    } else {
                        Toast.makeText(applicationContext, response.body()?.result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}
