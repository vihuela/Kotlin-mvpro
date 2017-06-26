package com.github.kotlin_mvpro.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class UserInfo(val name: String,
                    val nick: String) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        PaperParcelUserInfo.writeToParcel(this, parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = PaperParcelUserInfo.CREATOR
    }

}