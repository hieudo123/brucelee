package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Users(
        @SerializedName("id")
        var id :Int ,
        @SerializedName("username")
        var username: String,
        @SerializedName("password")
        var password:String ,
        @SerializedName("phone")
        var phone_number:String,
        @SerializedName("address")
        var  address:String,
        @SerializedName("email")
        var email: String,
        @SerializedName("image")
        var image :String,
        @SerializedName("gender")
        var gender :String,
        @SerializedName("status")
        var status :Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}