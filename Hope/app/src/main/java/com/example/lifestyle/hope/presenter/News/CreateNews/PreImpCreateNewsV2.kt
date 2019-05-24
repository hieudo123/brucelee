package com.example.lifestyle.hope.presenter.News.CreateNews

interface PreImpCreateNewsV2 {
    fun createNews(title:String, content:String, createdby: Int, createdtime: Long, image:ArrayList<String>)
}