package com.example.geonho.retorfitkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_default_diary.view.*

class DiaryRecyclerAdapter(var diaryList : List<Diary>, var context : Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.item_default_diary, null)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Diary = diaryList.get(position)
        holder.itemView.titleText.text = item.title
        holder.itemView.dateText.text = item.date
    }
}

class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)