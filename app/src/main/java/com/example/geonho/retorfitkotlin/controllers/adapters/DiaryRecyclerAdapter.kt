package com.example.geonho.retorfitkotlin.controllers.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.retorfitkotlin.DateUtil
import com.example.geonho.retorfitkotlin.Diary
import com.example.geonho.retorfitkotlin.R
import com.example.geonho.retorfitkotlin.controllers.activities.DetailActivity
import kotlinx.android.synthetic.main.item_default_diary.view.*

class DiaryRecyclerAdapter(var diaryList : List<Diary>, var context : Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.item_default_diary, parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Diary = diaryList.get(position)
        holder.itemView.titleText.text = item.title
        holder.itemView.dateText.text = DateUtil.formatDate(item.date)

        holder.itemView.setOnClickListener {
            var intent : Intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id",item._id)
            context.startActivity(intent)
        }
    }
}

class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)