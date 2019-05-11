package com.example.lifestyle.hope.Views.News

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ThemedSpinnerAdapter
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.util.TypedValue.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Adapter.CommentAdapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.R

class  NewsDetailActivity:BaseActivity()
{

    lateinit var recyclerView: RecyclerView
    var list= ArrayList<String>()
    lateinit var adapter: CommentAdapter
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news_detail
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var toolbar:android.support.v7.widget.Toolbar = findViewById(R.id.tb_toolbar)as Toolbar
        setTitle("Tin tức")
        init()
        loadData()
    }
    fun init() {
        recyclerView = findViewById(R.id.rv_comment)
    }
    fun loadData(){
        for(i:Int in 0..10-1) {
            list.add("Comment" + i);
        }
        setAdapter()
    }
    fun setAdapter(){
        adapter = CommentAdapter(this,list)
        var linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter= adapter
    }
}