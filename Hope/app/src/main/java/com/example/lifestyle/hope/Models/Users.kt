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
        @SerializedName("phone_number")
        var phone_number:String,
        @SerializedName("address")
        var  address:String,
        @SerializedName("email")
        var email: String,
        @SerializedName("image")
        var image :String,
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
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(phone_number)
        parcel.writeString(address)
        parcel.writeString(email)
        parcel.writeString(image)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
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