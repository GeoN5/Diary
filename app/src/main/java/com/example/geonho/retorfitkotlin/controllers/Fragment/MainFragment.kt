package com.example.geonho.retorfitkotlin.controllers.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.retorfitkotlin.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.geonho.retorfitkotlin.*
import com.example.geonho.retorfitkotlin.controllers.activities.WriteActivity
import com.example.geonho.retorfitkotlin.controllers.adapters.DiaryRecyclerAdapter
import kotlinx.android.synthetic.main.activity_navigation.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {


    lateinit var diaryList : List<Diary>
    lateinit var diaryRecyclerAdapter: DiaryRecyclerAdapter

    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false)
        setListener()
        loadData()
        return fragmentView
    }

    fun loadData(){
        var userName : String? = SharedPreferenceUtil.getData(context!!, "username")
        var diaryService : DiaryService = RetrofitUtil.getLoginRetrofit(context!!).create(DiaryService::class.java)
        var Call : Call<DiaryListGet> = diaryService.loadDiary(userName!!)
        Call.enqueue(object : Callback<DiaryListGet>{
            override fun onFailure(call: Call<DiaryListGet>?, t: Throwable?) {
                Log.e("Error",t!!.message)

            }

            override fun onResponse(call: Call<DiaryListGet>?, response: Response<DiaryListGet>?) {
                if(!(response?.body()!=null && response.body()!!.result.success && response.body()!!.diaries.isNotEmpty())){
                    Toast.makeText(context!!,"가져올 수 있는 데이터가 없습니다!",Toast.LENGTH_SHORT).show()
                }
                diaryList=response!!.body()!!.diaries
                setRecyclerView()
                diaryRecyclerAdapter.notifyDataSetChanged()
            }

        })
    }

    fun setRecyclerView(){
        diaryRecyclerAdapter = DiaryRecyclerAdapter(diaryList, context!!)
        fragmentView.diaryRecyclerView.adapter = diaryRecyclerAdapter
        fragmentView.diaryRecyclerView.setHasFixedSize(true)
        fragmentView.diaryRecyclerView.layoutManager = LinearLayoutManager(context!!)
    }

    override fun onResume() {
        //나중에 새로 추가하고 나서 다시 원래의 화면으로 불러올때 다시 로드
        super.onResume()
        loadData()
    }

    fun setListener(){
        fragmentView.fab.setOnClickListener {
            startActivity(Intent(context!!, WriteActivity::class.java))
        }
    }

}

