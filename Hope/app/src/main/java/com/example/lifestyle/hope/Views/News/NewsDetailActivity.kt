package com.example.lifestyle.hope.Views.News

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.*
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.util.TypedValue.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Adapter.CommentAdapter
import com.example.lifestyle.hope.Adapter.Image2Adapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Adapter.ViewpageAdapter
import com.example.lifestyle.hope.CallBack
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Models.Time
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.presenter.News.GetImage.PreHandlerGetImage
import com.example.lifestyle.hope.presenter.News.GetImage.PreImpGetImage
import com.example.lifestyle.hope.presenter.News.GetNewsDetail.PreHandlerGetNewsDetail
import com.example.lifestyle.hope.presenter.News.UpdateNews.PreHandlerUpdateNews
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class  NewsDetailActivity:BaseActivity(),ViewHandlerGetNewsDetail,
        ViewHandlerUpdateNews,
        View.OnClickListener,
        ViewHandlerGetImage,
        CallBack
{


    //Handler
    lateinit var preHandlerGetImage:PreHandlerGetImage
    lateinit var preHandlerGetNewsDetail: PreHandlerGetNewsDetail
    lateinit var preHandlerUpdateNews: PreHandlerUpdateNews

    var time = Time()
    lateinit var like : TextView
    lateinit var viewPager: ViewPager
    lateinit var news : News
    lateinit var title: TextView
    lateinit var bodyNews : TextView
    lateinit var createdTime : TextView
    lateinit var imageNews : ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var imageRecyclerView: RecyclerView
    lateinit var viewCounter: TextView
    var current_position =0
    var list= ArrayList<String>()
    var isLike = false
    lateinit var timer : Timer
    lateinit var pageCounter : TextView

    //adapter
    lateinit var viewAdapter: ViewpageAdapter
    lateinit var adapter: CommentAdapter
    lateinit var adapterImage: Image2Adapter
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news_detail
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var toolbar:android.support.v7.widget.Toolbar = findViewById(R.id.tb_toolbar)as Toolbar
        setTitle("Tin tức")
        init()
        getNewsDetail()
        getImageNews()
    }
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.tv_like_counter->{
                if(isLike)
                    isLike = false
                else
                    isLike = true
                countLike()
            }
        }
    }
    fun getNewsDetail(){
        var intent : Intent = getIntent()
        news = intent.getSerializableExtra("News") as News
        news.view_count +=1
        title.setText(news.title)
        bodyNews.setText(news.content)
//        Picasso.get().load(news.image).into(imageNews)
        createdTime.setText(time.getDateTime(news.created_time.toString()))
        viewCounter.setText(news.view_count.toString())
        like.setText(news.like_count.toString())
        list.add(news.image)

    }
    fun countLike(){
        if (isLike && news.like_count >=0) {
            news.like_count += 1
            like.setText(news.like_count.toString())
            like.setTextColor(getColor(R.color.bg_sigin))
        }
        else{
            news.like_count -=1
            like.setTextColor(getColor(R.color.colorPrimaryDark))
            like.setText(news.like_count.toString())
        }

    }
    fun updateNews(){
        preHandlerUpdateNews = PreHandlerUpdateNews(this,news,this)
        preHandlerUpdateNews.updateNews()
    }
    fun init() {
        viewPager = findViewById(R.id.vp_slide)
        list = ArrayList()
//        imageRecyclerView = findViewById(R.id.rv_imageNews)
        like = findViewById(R.id.tv_like_counter)
        recyclerView = findViewById(R.id.rv_comment)
        title = findViewById(R.id.tv_news_detail_title)
        bodyNews = findViewById(R.id.tv_news_content)
        createdTime = findViewById(R.id.tv_news_detail_createdtime)
//        imageNews = findViewById(R.id.iv_news_detail)
        viewCounter = findViewById(R.id.tv_view)
        pageCounter = findViewById(R.id.tv_counter_page)
        like.setOnClickListener(this)
    }
    fun setViewPager(list : ArrayList<String>){
        viewAdapter = ViewpageAdapter(list,this)
        viewPager.adapter = viewAdapter

    }
    fun createSideShow(){
        val handler : Handler = Handler()
        val runnable : Runnable = object :Runnable{
            override fun run() {
                if(current_position == list.size)
                    current_position = 0
                viewPager.setCurrentItem(current_position++,true)
            }
        }
        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(runnable)
            }
        },200,1500)
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                current_position = p0
                pageCounter.setText("${p0+1}/${list.size}")
            }

            override fun onPageSelected(p0: Int) {
                current_position = p0
                pageCounter.setText("${p0+1}/${list.size}")
            }
        } )
    }
    fun setAdapter(){
        adapter = CommentAdapter(this,list)
        var linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter= adapter
    }
    fun setAdapterImages(){
        adapterImage = Image2Adapter(list,this)
        adapterImage.setClickItemListener(this)
        var linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
        imageRecyclerView.layoutManager=linearLayoutManager
        imageRecyclerView.adapter= adapterImage
    }
    fun getImageNews(){
        preHandlerGetImage = PreHandlerGetImage(list,this)
        preHandlerGetImage.getImage(news.id)
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
    override fun onClick(position: Int) {
        Picasso.get().load(list[position].toString()).into(imageNews)
    }

    override fun getImageInProgress() {

    }

    override fun getImageOnSuccess() {
//        setAdapterImages()
        setViewPager(list)
        createSideShow()
    }

    override fun getImageOnFail() {

    }
    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateNews()
    }
}