package com.example.lifestyle.hope.retrofit

import com.example.lifestyle.hope.respone.resUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("UpdateProfile.php")
    fun updateProfile(@Field ("Id") id:Int,
                      @Field ("Username") username: String,
                      @Field ("Phone") phone: String,
                      @Field ("Address") address: String,
                      @Field ("Email") email: String,
                      @Field ("Gender") gender: String,
                      @Field ("Image") image :String): Call<resUser>
}