package com.example.lifestyle.hope.Models

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Classify(
        @Expose
        @SerializedName("Id")
        var id: Int,
        @SerializedName("Name")
        var classify_name: String
):Parcelable{
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

    companion object CREATOR : Parcelable.Creator<Classify> {
        override fun createFromParcel(parcel: Parcel): Classify {
            return Classify(parcel)
        }

        override fun newArray(size: Int): Array<Classify?> {
            return arrayOfNulls(size)
        }
    }
}