package com.example.geonho.retorfitkotlin

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(SharedPreferenceUtil.getData(applicationContext,"token")==null){
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            }
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        },3000)
    }
}
