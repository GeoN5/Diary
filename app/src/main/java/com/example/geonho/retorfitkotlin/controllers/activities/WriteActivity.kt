package com.example.geonho.retorfitkotlin.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.geonho.retorfitkotlin.*
import com.example.geonho.retorfitkotlin.server.Diary
import com.example.geonho.retorfitkotlin.server.DiaryService
import com.example.geonho.retorfitkotlin.server.Response
import kotlinx.android.synthetic.main.activity_write.*
import retrofit2.Call
import retrofit2.Callback

class WriteActivity : AppCompatActivity() {

    companion object {
        val TAG:String = WriteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        setListener()
    }

    private fun setListener(){
        writeButton.setOnClickListener {
            writeData()
        }
    }

    private fun writeData(){
        val username : String? = SharedPreferenceUtil.getData(applicationContext, "username")
        val diaryService : DiaryService = RetrofitUtil.getLoginRetrofit(applicationContext).create(DiaryService::class.java)
        val diary = Diary(editTitle.text.toString(), editContent.text.toString(), username!!)

        val call: Call<Response> = diaryService.writeDiary(diary)
        call.enqueue(object : Callback<Response>{
            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                if(response!!.body()!=null&&response.body()!!.result.success){
                    Toast.makeText(applicationContext,"성공적으로 작성하였습니다.",Toast.LENGTH_SHORT).show()
                    finish()
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
