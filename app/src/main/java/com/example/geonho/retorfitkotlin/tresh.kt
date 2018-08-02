package com.example.geonho.retorfitkotlin

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.geonho.retorfitkotlin.controllers.activities.PrefEditActivity

class tresh() : AppCompatActivity(){

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var curId = item?.itemId
        when (curId) {
            R.id.setting -> startActivity(Intent(this@tresh, PrefEditActivity::class.java))
        //R.id.setting -> startActivity(Intent(this@NavigationActivity,UserActivity::class.java))
        }
        return true
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