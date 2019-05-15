package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class BaseModels(
        @SerializedName("Success")
        var success:Int) : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(success)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseModels> {
        override fun createFromParcel(parcel: Parcel): BaseModels {
            return BaseModels(parcel)
        }

        override fun newArray(size: Int): Array<BaseModels?> {
            return arrayOfNulls(size)
        }
    }
}