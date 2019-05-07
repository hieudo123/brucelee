package com.example.lifestyle.hope.Views.News

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.util.TypedValue.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.R

class  NewsDetailActivity:BaseActivity()
{
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var toolbar:android.support.v7.widget.Toolbar = findViewById(R.id.tb_toolbar)as Toolbar
        setTitle("Tin tức")
    }
}