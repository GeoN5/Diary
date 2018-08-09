package com.example.geonho.retorfitkotlin.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.geonho.retorfitkotlin.R
import com.example.geonho.retorfitkotlin.SharedPreferenceUtil
import com.example.geonho.retorfitkotlin.controllers.Fragment.MainFragment
import com.example.geonho.retorfitkotlin.controllers.Fragment.UserFragment
import com.example.geonho.retorfitkotlin.loadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.content_main,MainFragment()).commit()

        navigationHeaderset()
    }

    fun navigationHeaderset(){
        //nav_view.textView.text = SharedPreferenceUtil.getData(applicationContext,"username")!!
        //nav_view.imageView.loadImage("purplebeen.kr:3000/images/${SharedPreferenceUtil.getData(applicationContext,"username")}.jpg",this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this@MainActivity,PrefEditActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_list -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main,MainFragment()).commit()
            }
            R.id.nav_user -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main,UserFragment()).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
