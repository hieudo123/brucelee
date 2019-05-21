package com.example.lifestyle.hope.presenter.News.UpdateNews
import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Views.News.ViewHandlerUpdateNews
import com.example.lifestyle.hope.respone.resNewsDetail
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerUpdateNews(var context:Context,var news: News,var v: ViewHandlerUpdateNews):PreImpUpdateNews {
    override fun updateNews() {
        v.updateNewsInProgress()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call : Call<resNewsDetail> = apiService.updateNews(news.title,
                news.content,
                news.created_by,
                news.created_time,
                news.view_count,
                news.like_count,
                news.id,
                news.image)
        call.enqueue(object : Callback<resNewsDetail>{
            override fun onFailure(call: Call<resNewsDetail>?, t: Throwable?) {
                v.updateNewsOnFail()
            }

            override fun onResponse(call: Call<resNewsDetail>?, response: Response<resNewsDetail>?) {
                if (response !=null){
                    var jsonResponse = response.body()
                    if(jsonResponse.success ==1){
                        news = jsonResponse.data
                        Log.e("COUNTER",news.view_count.toString())
                    }
                    v.updateNewsOnFail()
                }
                v.updateNewsOnFail()
            }

        })
    }
}