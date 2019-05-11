package com.example.lifestyle.hope.presenter.Users

import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.Views.Users.UpdateProfile.ViewHandlerUpdateProfile
import com.example.lifestyle.hope.respone.resUser
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerUpdateProfile(var user :Users, var context: Context, var v : ViewHandlerUpdateProfile):PreImpUpdateProfile{
    override fun updateProfile() {
        v.updateOnProgess()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService = retrofit!!.create(ApiService::class.java)
        val call : Call<resUser> = apiService.updateProfile(user.id,
                user.username,
                user.phone_number,
                user.address,
                user.email,
                user.gender,
                user.image)
        call.enqueue(object : Callback<resUser> {
            override fun onFailure(call: Call<resUser>?, t: Throwable?) {
                Log.e("PPP",t.toString())
                v.updaterOnFail()
            }
            override fun onResponse(call: Call<resUser>?, response: Response<resUser>?) {
                val jsonRespone = response!!.body()
                if (jsonRespone.data != null){
                    user = jsonRespone.data
                    Log.e("QQQ",user.email)
                    v.updateOnSuccess()
                }
            }
        })
    }
}