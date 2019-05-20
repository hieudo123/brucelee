package com.example.lifestyle.hope.presenter.News.CreateNews

interface PrelmCreateNews {
    fun createNews(title:String, content:String, createdby: String, createdtime: Long, image: String)
}