package com.example.geonho.retorfitkotlin.controllers.activities

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.preference.PreferenceManager
import com.example.geonho.retorfitkotlin.R
import com.example.geonho.retorfitkotlin.SharedPreferenceUtil


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val login = (java.lang.Boolean.valueOf(prefs.getBoolean("autoLogin", false)).toString())

        Handler().postDelayed({

            if(SharedPreferenceUtil.getData(applicationContext, "token") ==null){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }else if(SharedPreferenceUtil.getData(applicationContext, "token") !=null&& login == "false"){
                Toast.makeText(applicationContext, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }else if (SharedPreferenceUtil.getData(applicationContext, "token") !=null&& login == "true"){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
        },3000)
    }
}
