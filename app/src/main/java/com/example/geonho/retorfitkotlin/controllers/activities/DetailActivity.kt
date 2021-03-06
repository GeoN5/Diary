package com.example.geonho.retorfitkotlin.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import com.example.geonho.retorfitkotlin.*
import com.example.geonho.retorfitkotlin.server.DetailResponse
import com.example.geonho.retorfitkotlin.server.Diary
import com.example.geonho.retorfitkotlin.server.DiaryService
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
        contentText.movementMethod = ScrollingMovementMethod()
        loadData()
        setListener()
    }

    private fun setListener(){
        updateFab.setOnClickListener {
            goToUpdate()
        }
        deleteFab.setOnClickListener {
            delete()
        }
    }

    private fun loadData(){
        val diaryService: DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        val call : Call<DetailResponse> = diaryService.detailDiary(id)
        call.enqueue(object : Callback<DetailResponse>{
            override fun onFailure(call: Call<DetailResponse>?, t: Throwable?) {
                Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<DetailResponse>?, response: Response<DetailResponse>?) {
                if(response!!.body()!=null&&response.body()!!.result.success){
                    val diary : Diary = response.body()!!.diary
                    titleText.text = diary.title
                    contentText.text = diary.content
                    dateText.text = DateUtil.formatDate(diary.date)
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

    private fun goToUpdate(){
          startActivity(Intent(this@DetailActivity,UpdateActivity::class.java).putExtra("id",id))
    }

    private fun delete(){
        val diaryService: DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        val call : Call<com.example.geonho.retorfitkotlin.server.Response> = diaryService.deleteDiary(id)
        call.enqueue(object : Callback<com.example.geonho.retorfitkotlin.server.Response>{
            override fun onFailure(call: Call<com.example.geonho.retorfitkotlin.server.Response>?, t: Throwable?) {
                Toast.makeText(applicationContext,"삭제에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<com.example.geonho.retorfitkotlin.server.Response>?, response: Response<com.example.geonho.retorfitkotlin.server.Response>?) {
                if(response!!.body()!=null&& response.body()!!.result.success){
                    Toast.makeText(applicationContext,"성공적으로 삭제하였습니다!",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    if(response.body()==null){
                        Log.e(TAG,"Server Error or Network Error")
                        Toast.makeText(applicationContext,"네트워크 오류입니다.",Toast.LENGTH_SHORT).show()
                    }else{
                        Log.e(TAG,response.body()!!.result.message)
                        Toast.makeText(applicationContext,response.body()!!.result.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
