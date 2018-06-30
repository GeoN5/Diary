package com.example.geonho.retorfitkotlin

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(SharedPreferenceUtil.getData(applicationContext,"token")==null){
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            }else {
                Toast.makeText(applicationContext, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        },3000)
    }
}
