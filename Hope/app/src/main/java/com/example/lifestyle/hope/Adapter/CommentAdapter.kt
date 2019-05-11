package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestyle.hope.R

class CommentAdapter(var context :Context ,var array: ArrayList<String>):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CommentAdapter.ViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.item_comment_layout,p0,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(p0: CommentAdapter.ViewHolder, p1: Int) {

    }
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

    }
}