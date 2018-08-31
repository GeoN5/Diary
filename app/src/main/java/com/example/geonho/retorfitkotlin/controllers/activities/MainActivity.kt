package com.example.geonho.retorfitkotlin.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.geonho.retorfitkotlin.R
import com.example.geonho.retorfitkotlin.SharedPreferenceUtil
import com.example.geonho.retorfitkotlin.controllers.fragment.MainFragment
import com.example.geonho.retorfitkotlin.controllers.fragment.UserFragment
import com.example.geonho.retorfitkotlin.loadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.content_main,MainFragment.newInstance()).commit()

        navigationHeaderset()
    }

    private fun navigationHeaderset(){
        nav_view.getHeaderView(0).textView.text = SharedPreferenceUtil.getData(applicationContext,"username")!!
        nav_view.getHeaderView(0).imageView.loadImage("http://purplebeen.kr:3000/images/${SharedPreferenceUtil.getData(applicationContext,"username")}.jpg",this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val tempTime = System.currentTimeMillis()
            val intervalTime = tempTime - backPressedTime
            if (intervalTime in 0..FINSH_INTERVAL_TIME) {
                ActivityCompat.finishAffinity(this)
            } else {
                backPressedTime = tempTime
                Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this@MainActivity,PrefEditActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_list -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main,MainFragment.newInstance()).commit()
            }
            R.id.nav_user -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main,UserFragment.newInstance()).commit()
            }
            R.id.nav_logout ->{
                SharedPreferenceUtil.removePreferences(this@MainActivity,"username")
                SharedPreferenceUtil.removePreferences(this@MainActivity,"token")
                startActivity(Intent(this@MainActivity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
