package com.example.lifestyle.hope.presenter.Classify

import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.Classify
import com.example.lifestyle.hope.Views.Classify.ViewHandlerGetClassify
import com.example.lifestyle.hope.respone.resClassify
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerGetClassify(var list:ArrayList<Classify>,var context: Context,var v:ViewHandlerGetClassify): PreImpGetClassify {
    override fun getClassify() {
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call: Call<resClassify> = apiService.getClassify()
        call.enqueue(object :Callback<resClassify>{
            override fun onFailure(call: Call<resClassify>?, t: Throwable?) {
                Log.e("CLASSIFY",t.toString())
                v.getClassifyOnFail()
            }

            override fun onResponse(call: Call<resClassify>?, response: Response<resClassify>?) {
                if(response != null){
                    if(response.body().success ==1){
                        val jsonReponse = response.body().data
                        for(i: Int in 0..jsonReponse.size-1){
                            list.add(jsonReponse[i])
                            Log.e("CLASSIFY",list.toString())
                            v.getClassifyOnSuccess()
                        }
                    }
                }
            }
        })
    }
}