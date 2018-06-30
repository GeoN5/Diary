package com.example.geonho.retorfitkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime - backPressedTime
        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            ActivityCompat.finishAffinity(this);
        } else {
            backPressedTime = tempTime;
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
