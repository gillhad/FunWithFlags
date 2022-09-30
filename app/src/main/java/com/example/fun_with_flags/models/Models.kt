package com.example.fun_with_flags.models

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class Country(name:String, continent:String,flag:String, closeFlags: ArrayList<Country>?) : Parcelable {
    var name = name
    var continent: String = continent
    var flag: String = flag
    var closeFlags: ArrayList<Country>? = closeFlags

    constructor(parcel: Parcel) : this(
        TODO("name"),
        TODO("continent"),
        TODO("flag"),
        TODO("closeFlags")
    ) {
        name = parcel.readString().toString()
        continent = parcel.readString().toString()
        flag = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(continent)
        parcel.writeString(flag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }

    }
}

class CountryList{
    var countries : ArrayList<Country> = ArrayList()
}

