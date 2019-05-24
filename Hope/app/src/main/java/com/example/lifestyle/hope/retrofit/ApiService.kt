package com.example.lifestyle.hope.retrofit

import com.example.lifestyle.hope.Models.BaseModels
import com.example.lifestyle.hope.respone.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface ApiService {
    @FormUrlEncoded
    @POST("UpdateProfile.php")
    fun updateProfile(@Field ("Id") id:Int,
                      @Field ("Username") username: String,
                      @Field ("Phone") phone: String,
                      @Field ("Address") address: String,
                      @Field ("Email") email: String,
                      @Field ("Gender") gender: Int,
                      @Field ("Image") image :String): Call<resUser>
    @FormUrlEncoded
    @POST("ChangePassword.php")
    fun changePassword(@Field ("Id") id:Int,
                      @Field ("Password") password: String,
                      @Field ("RePassword") repassword: String): Call<resUser>
    @FormUrlEncoded
    @POST("CreateNews.php")
    fun createNews(@Field ("Title") title:String,
                   @Field ("Content") Content: String,
                   @Field ("Createdby") CreatedBy: String,
                   @Field("Createdtime") CreatedTime: Long,
                   @Field("Image") Image:String): Call<BaseModels>
    @GET("GetAllNews.php")
    fun getAllNews (@Query ("page") page : Int) : Call<resNews>
    @GET("GetNewsById.php")
    fun getNewsById(@Query ("id") id : Int) : Call<resNewsDetail>
    @FormUrlEncoded
    @POST("UpdateNews.php")
    fun updateNews(@Field ("Title") title:String,
                   @Field ("Content") Content: String,
                   @Field ("Createdby") CreatedBy: Int,
                   @Field("Createdtime") CreatedTime: Long,
                   @Field("View_counter") View_counter:Int,
                   @Field ("Like_counter") Like_counter:Int,
                   @Field("Id") Id:Int,
                   @Field("Image") Image:String): Call<resNewsDetail>
    @GET("GetArea.php")
    fun getArea():Call<resArea>
    @FormUrlEncoded
    @POST("TestPostArray.php")
    fun test(@Field("Image[]") image: List<String>,
             @Field ("Title") title:String,
             @Field ("Content") Content: String,
             @Field ("Createdby") CreatedBy: Int,
             @Field("Createdtime") CreatedTime: Long):Call<BaseModels>
}