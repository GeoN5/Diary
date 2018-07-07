package com.example.geonho.retorfitkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0

    lateinit var diaryList : List<Diary>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListener()
        loadData()
    }

    fun loadData(){
        var userName : String? = SharedPreferenceUtil.getData(applicationContext,"username")
        var diaryService : DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        var Call : Call<DiaryListGet> = diaryService.loadDiary(userName!!)
        Call.enqueue(object : Callback<DiaryListGet>{
            override fun onFailure(call: Call<DiaryListGet>?, t: Throwable?) {
                Log.e("Error",t!!.message)

            }

            override fun onResponse(call: Call<DiaryListGet>?, response: Response<DiaryListGet>?) {
                if(response?.body()!=null && response.body()!!.result.success && response.body()!!.diaries.isNotEmpty()){
                    diaryList = response.body()!!.diaries
                    setRecyclerView()
                }else{
                    Toast.makeText(applicationContext,"가져올 수 있는 데이터가 없습니다!",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun setRecyclerView(){
        var diaryRecyclerAdapter : DiaryRecyclerAdapter = DiaryRecyclerAdapter(diaryList,applicationContext)
        diaryRecyclerView.adapter = diaryRecyclerAdapter
        diaryRecyclerView.setHasFixedSize(true)
        diaryRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onResume() {
        //나중에 새로 추가하고 나서 다시 원래의 화면으로 불러올때 다시 로드
        super.onResume()
        loadData()
    }

    fun setListener(){
        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity,WriteActivity::class.java))
        }
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
