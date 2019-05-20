package com.example.lifestyle.hope.presenter.News.GetAllNews

import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Views.News.ViewHandlerGetNews
import com.example.lifestyle.hope.respone.resNews
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class PreHandlerNews(var context: Context, var v : ViewHandlerGetNews, var list:ArrayList<News>): PreImpNews {
    override fun getAllNews(page: Int) {
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call: Call<resNews> = apiService.getAllNews(page)
        call.enqueue(object : Callback<resNews>{
            override fun onFailure(call: Call<resNews>?, t: Throwable?) {
                v.getAllNewsFail()
            }
            override fun onResponse(call: Call<resNews>?, response: retrofit2.Response<resNews>?) {
                val jsonResponse = response!!.body()
                if(jsonResponse.success ==1){
                    for (i : Int in 0..jsonResponse.data.size-1){
                        list.add(jsonResponse.data[i])
                    }
                    Log.e("LOL",list[0].content)
                    v.getAllNewsSuccess()
                }
                v.isLastPage()
            }
        })
    }
}