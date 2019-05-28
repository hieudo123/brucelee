package com.example.lifestyle.hope.presenter.News.GetImage

import android.util.Log
import com.example.lifestyle.hope.Views.News.ViewHandlerGetImage
import com.example.lifestyle.hope.respone.test
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerGetImage(var list:ArrayList<String>,var v:ViewHandlerGetImage):PreImpGetImage {
    override fun getImage(id: Int) {
        v.getImageInProgress()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call : Call<test> = apiService.getImageNews(id)
        call.enqueue(object :Callback<test>{
            override fun onFailure(call: Call<test>?, t: Throwable?) {
                Log.e("EEE",t.toString())
                v.getImageOnFail()
            }

            override fun onResponse(call: Call<test>?, response: Response<test>?) {
                Log.e("OOO",response!!.body().data.toString())
                if(response !=null){
                    var jsonRequest = response.body().data
                    for(i:Int in 0..jsonRequest.size-1){
                        list.add(jsonRequest[i])
                    }
                    Log.e("OO",list.toString())
                    v.getImageOnSuccess()
                }
            }
        })
    }
}