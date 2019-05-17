package com.example.lifestyle.hope.presenter.News

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lifestyle.hope.Models.House
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Views.News.ViewHandlerGetNews
import com.example.lifestyle.hope.respone.resNews
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class PreHandlerNews(var context: Context, var v : ViewHandlerGetNews, var list:ArrayList<News>):PreImpNews {
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
                for (i : Int in 0..jsonResponse.data.size-1){
                    list.add(jsonResponse.data[i])
                }
                Log.e("LOL",list[0].content)
                v.getAllNewsSuccess()
            }
        })
    }
}