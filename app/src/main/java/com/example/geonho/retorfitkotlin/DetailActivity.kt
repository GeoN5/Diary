package com.example.geonho.retorfitkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    lateinit var id :String

    companion object {
        val TAG:String = DetailActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        id=intent.getStringExtra("id")
        loadData()
    }

    fun loadData(){
        var diaryService:DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        var call : Call<DetailResponse> = diaryService.detailDiary(id)
        call.enqueue(object : Callback<DetailResponse>{
            override fun onFailure(call: Call<DetailResponse>?, t: Throwable?) {
                Log.w(TAG,t)
            }

            override fun onResponse(call: Call<DetailResponse>?, response: Response<DetailResponse>?) {
                if(response!!.body()!=null&&response.body()!!.result.success){
                    var diary : Diary = response.body()!!.diary
                    titleText.text = diary.title
                    contentText.text = diary.content
                    dateText.text = diary.date
                }else{
                    if(response.body()==null){
                        Log.e(TAG,"Server Error or Network Error!")
                        Toast.makeText(applicationContext,"네트워크 오류입니다. 잠시후 다시 시도해주세요.",Toast.LENGTH_SHORT).show()
                    }else{
                        Log.e(TAG,response.body()!!.result.message)
                        Toast.makeText(applicationContext,response.body()!!.result.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
