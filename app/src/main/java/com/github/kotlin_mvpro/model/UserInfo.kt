/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

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