package com.example.lifestyle.hope.presenter.News.CreateNews

import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.BaseModels
import com.example.lifestyle.hope.Views.News.ViewHandlerCreateNews
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerCreateNews(var context: Context,var v : ViewHandlerCreateNews): PrelmCreateNews
{
    override fun createNews(title: String, content: String, createdby: String, createdtime: Long, image: String) {
        v.createInProgress()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit?.create(ApiService::class.java)
        val call: Call<BaseModels> = apiService!!.createNews(title,content,createdby,createdtime,image)
        call.enqueue(object : Callback<BaseModels>{
            override fun onFailure(call: Call<BaseModels>?, t: Throwable?) {
                v.createOnFail()
                Log.e("NNN",t.toString())
            }

            override fun onResponse(call: Call<BaseModels>?, response: Response<BaseModels>?) {
                var request = response!!.body()
                Log.e("HHH",request.success.toString())
                v.createOnSuccess()
            }
        })
    }
}