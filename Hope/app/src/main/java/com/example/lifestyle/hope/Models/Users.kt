package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Users(
        @Expose
        @SerializedName("Id")
        var id :Int ,
        @Expose
        @SerializedName("Username")
        var username: String,
        @Expose
        @SerializedName("Password")
        var password:String ,
        @Expose
        @SerializedName("Phone")
        var phone_number:String,
        @Expose
        @SerializedName("Address")
        var  address:String,
        @Expose
        @SerializedName("Email")
        var email: String,
        @Expose
        @SerializedName("Image")
        var image :String,
        @Expose
        @SerializedName("Gender")
        var gender :Int,
        @Expose
        @SerializedName("Status")
        var status :Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
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