package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.Activity.MainActivity
import com.example.lifestyle.hope.CallBack
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Models.Time
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.News.NewsDetailActivity
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter(var context: Context,var list:ArrayList<News>):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    lateinit var callback  : CallBack
    var time = Time()
    override fun onCreateViewHolder(p0: ViewGroup, i: Int): ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_news,p0,false)
        return ViewHolder(v)
    }
    fun setClickItemListener(callBackItem: CallBack)
    {
        callback = callBackItem
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.createdTime.setText(time.getDateTime(list[i].created_time.toString()))
        holder.likeCounter.setText(list[i].like_count.toString())
        holder.viewCounter.setText(list[i].view_count.toString())
        holder.title.setText(list[i].title)
        Picasso.get().load(list[i].image).error(R.drawable.ic_image).into(holder.image)
        holder.itemView.setOnClickListener {
            if (callback!=null){
                callback.onClick(i)
                Log.e("BBBBB",i.toString())
            }
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var createdTime : TextView = itemView.findViewById(R.id.tv_createdTime)
        var image : ImageView = itemView.findViewById(R.id.iv_news)
        var title : TextView = itemView.findViewById(R.id.tv_news_title)
        var viewCounter : TextView = itemView.findViewById(R.id.tv_view_counter)
        var likeCounter : TextView = itemView.findViewById(R.id.tv_quality_like)
        var commentCounter : TextView = itemView.findViewById(R.id.tv_comment_counter)
    }

}