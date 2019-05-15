package com.example.lifestyle.hope.presenter.Users

import android.content.Context
import android.util.Log
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.Views.Users.ViewHandlerChangePassword
import com.example.lifestyle.hope.respone.resUser
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import com.example.lifestyle.hope.utils.SharePref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreHandlerChangePassword(var user:Users,var context : Context,var v:ViewHandlerChangePassword):PrelmpChangePassword {
    override fun changePassword(password: String, repassword: String) {
        v.changeInProgress()
        val retrofit = RetrofitClient.getClient(Config.URL)
        val apiService =retrofit!!.create(ApiService::class.java)
        val call : Call<resUser> = apiService.changePassword(user.id,password,repassword)
        call.enqueue(object : Callback<resUser>{
            override fun onFailure(call: Call<resUser>?, t: Throwable?) {
                v.changeOnFail()
            }
            override fun onResponse(call: Call<resUser>?, response: Response<resUser>?) {
                val jsonRespone = response!!.body()
                if (jsonRespone.data != null) {
                    user = jsonRespone.data
                    Log.e("QQQ", user.email)
                    var sharePref = SharePref(context)
                    sharePref.clear()
                    sharePref.putUser(user)
                    v.changeOnSuccess()
                }
                v.changeOnFail()
            }
        })
    }
}