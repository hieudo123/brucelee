package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Area(
        @Expose
        @SerializedName("Id")
        var id: Int,
        @Expose
        @SerializedName("Area_name")
        var area_name: String

):Parcelable  {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Area> {
        override fun createFromParcel(parcel: Parcel): Area {
            return Area(parcel)
        }

        override fun newArray(size: Int): Array<Area?> {
            return arrayOfNulls(size)
        }
    }
}