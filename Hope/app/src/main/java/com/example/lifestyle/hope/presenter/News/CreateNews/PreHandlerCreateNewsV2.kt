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

class PreHandlerCreateNewsV2(var v:ViewHandlerCreateNews): PreImpCreateNewsV2 {
    override fun createNews(title: String, content: String, createdby: Int, createdtime: Long, image: ArrayList<String>) {
        v.createInProgress()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call : Call<BaseModels> = apiService.test(image,title,
                content,createdby,createdtime)
        call.enqueue(object : Callback<BaseModels> {
            override fun onFailure(call: Call<BaseModels>?, t: Throwable?) {
                Log.e("LLL",t.toString())
                v.createOnFail()
            }
            override fun onResponse(call: Call<BaseModels>?, response: Response<BaseModels>?) {
                if(response!= null){
                    val jsonRequest = response.body()
                    Log.e("LLL",response.body().success.toString())
                    if(jsonRequest.success ==1){
                        v.createOnSuccess()
                    }
                    else v.createOnFail()
                }
            }
        })
    }
}