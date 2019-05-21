package com.example.lifestyle.hope.Views.News

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.*
import android.util.TypedValue
import android.util.TypedValue.*
import android.widget.ImageView
import android.widget.TextView
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Adapter.CommentAdapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Models.Time
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.presenter.News.GetNewsDetail.PreHandlerGetNewsDetail
import com.example.lifestyle.hope.presenter.News.UpdateNews.PreHandlerUpdateNews
import com.squareup.picasso.Picasso

class  NewsDetailActivity:BaseActivity(),ViewHandlerGetNewsDetail,ViewHandlerUpdateNews
{
    var time = Time()
    lateinit var news : News
    lateinit var preHandlerGetNewsDetail: PreHandlerGetNewsDetail
    lateinit var preHandlerUpdateNews: PreHandlerUpdateNews
    lateinit var title: TextView
    lateinit var bodyNews : TextView
    lateinit var createdTime : TextView
    lateinit var imageNews : ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var viewCounter: TextView
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
        getNewsDetail()
    }
    fun getNewsDetail(){
        var intent : Intent = getIntent()
        news = intent.getSerializableExtra("News") as News
        news.view_count +=1

        title.setText(news.title)
        bodyNews.setText(news.content)
        Picasso.get().load(news.image).into(imageNews)
        createdTime.setText(time.getDateTime(news.created_time.toString()))
        viewCounter.setText(news.view_count.toString())

    }
    fun updateNews(){
        preHandlerUpdateNews = PreHandlerUpdateNews(this,news,this)
        preHandlerUpdateNews.updateNews()
    }
    fun init() {
        recyclerView = findViewById(R.id.rv_comment)
        title = findViewById(R.id.tv_news_detail_title)
        bodyNews = findViewById(R.id.tv_news_content)
        createdTime = findViewById(R.id.tv_news_detail_createdtime)
        imageNews = findViewById(R.id.iv_news_detail)
        viewCounter = findViewById(R.id.tv_view)
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
    override fun getDetailOnSuccess() {

    }
    override fun getDatailOnFail() {

    }
    override fun updateNewsOnSuccess() {

    }
    override fun updateNewsOnFail() {

    }

    override fun updateNewsInProgress() {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateNews()
    }
}