package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import java.io.Serializable

class Users(var id :Int ,
            var username: String,
            var password:String ,
            var phone_number:String,
            var  address:String,
            var email: String,
            var image :String,
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