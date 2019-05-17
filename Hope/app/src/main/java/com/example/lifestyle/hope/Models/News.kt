package com.example.lifestyle.hope.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class News(
        @Expose
        @SerializedName("Id") var id:Int,
        @Expose
        @SerializedName("Title") var title:String,
        @Expose
        @SerializedName("Like_counter")var like_count:Int,
        @Expose
        @SerializedName("View_counter")var view_count:Int,
        @Expose
        @SerializedName("Content") var content:String,
        @Expose
        @SerializedName("Created_by")var created_by:String,
        @Expose
        @SerializedName("Created_time") var created_time:Long,
        @Expose
        @SerializedName("Image") var image:String,
        @Expose
        @SerializedName("Status") var status:Int) : Serializable {
}