package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lifestyle.hope.Activity.MainActivity
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.News.NewsDetailActivity


class NewsAdapter(var context: Context,var list:ArrayList<String>):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, i: Int): ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_news,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(context,list.get(i),Toast.LENGTH_SHORT).show()
            var intent = Intent(context, NewsDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}