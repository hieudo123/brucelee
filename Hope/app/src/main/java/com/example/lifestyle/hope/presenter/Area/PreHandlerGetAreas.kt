package com.example.lifestyle.hope.presenter.Area

import android.content.Context
import com.example.lifestyle.hope.Models.Area
import com.example.lifestyle.hope.Views.Area.ViewHandlerGetAreas
import com.example.lifestyle.hope.respone.resArea
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerGetAreas(var context: Context,var v: ViewHandlerGetAreas, var list : ArrayList<Area>):PreImpGetAreas {
    override fun getAreas() {
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call : Call<resArea> = apiService.getArea()
        call.enqueue(object : Callback<resArea>{
            override fun onFailure(call: Call<resArea>?, t: Throwable?) {
                v.getOnFail()
            }

            override fun onResponse(call: Call<resArea>?, response: Response<resArea>?) {
                if(response != null){
                    var jsonResponse = response.body()
                    if(jsonResponse.success ==1){
                        for(i: Int in 0..jsonResponse.data.size-1){
                            list.add(jsonResponse.data[i])
                        }
                        v.getOnSuccess()
                    }
                }
                v.getOnFail()
            }

        })
    }
}