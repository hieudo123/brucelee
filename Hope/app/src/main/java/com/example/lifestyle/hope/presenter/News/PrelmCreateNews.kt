package com.example.lifestyle.hope.presenter.News

import java.util.*

interface PrelmCreateNews {
    fun createNews(title:String, content:String, createdby: String, createdtime: Long, image: String)
}