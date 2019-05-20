package com.example.lifestyle.hope.presenter.News.GetNewsDetail

import android.content.Context
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Views.News.ViewHandlerGetNewsDetail
import com.example.lifestyle.hope.respone.resNewsDetail
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerGetNewsDetail(var news : News, var context: Context, var v: ViewHandlerGetNewsDetail):PreImpGetNewsDetail {
    override fun getNewsDetail(id: Int) {
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call: Call<resNewsDetail> = apiService.getNewsById(id)
        call.enqueue(object : Callback<resNewsDetail> {
            override fun onFailure(call: Call<resNewsDetail>?, t: Throwable?) {
                v.getDatailOnFail()
            }
            override fun onResponse(call: Call<resNewsDetail>?, response: Response<resNewsDetail>?) {
                if(response != null){
                    val jsonResponse = response.body()
                    if(jsonResponse.success == 1){
                        v.getDetailOnSuccess()
                    }
                }

            }
        })

    }
}