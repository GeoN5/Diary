package com.example.geonho.retorfitkotlin.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.geonho.retorfitkotlin.*
import kotlinx.android.synthetic.main.activity_write.*
import retrofit2.Call
import retrofit2.Callback

class UpdateActivity:AppCompatActivity(){

    lateinit var id :String

    companion object {
        val TAG:String = UpdateActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        writeButton.text = "수정 완료"
        id = intent.getStringExtra("id")
        loadData()
        setListener()
    }

    private fun setListener(){
        writeButton.setOnClickListener {
            updateData()
        }
    }

    private fun loadData(){
        var diaryService: DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        var call : Call<DetailResponse> = diaryService.detailDiary(id)
        call.enqueue(object : Callback<DetailResponse> {
            override fun onFailure(call: Call<DetailResponse>?, t: Throwable?) {
                Log.w(TAG,t)
            }

            override fun onResponse(call: Call<DetailResponse>?, response: retrofit2.Response<DetailResponse>?) {
                if(response!!.body()!=null&&response.body()!!.result.success){
                    var diary : Diary = response.body()!!.diary
                    editTitle.setText(diary.title)
                    editContent.setText(diary.content)
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

    fun updateData(){
        var username = SharedPreferenceUtil.getData(applicationContext,"username")
        var diary : Diary = Diary(editTitle.text.toString(),editContent.text.toString(),username!!)
        var diaryService: DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        var call : Call<Response> = diaryService.editDiary(id,diary)
        call.enqueue(object : Callback<Response>{
            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                Log.w(TAG,t)
            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                if (response!!.body() != null && response.body()!!.result.success) {
                    Toast.makeText(applicationContext, "성공적으로 수정하였습니다!.", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    if (response.body() == null) {
                        Log.e(TAG, "Server Error or Network Error!")
                        Toast.makeText(applicationContext, "네트워크 오류입니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, response.body()!!.result.message)
                        Toast.makeText(applicationContext, response.body()!!.result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}