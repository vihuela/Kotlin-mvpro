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

package com.github.library.utils

import android.app.Activity
import android.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import github.library.parser.ExceptionParseMgr
import github.library.utils.Error
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//rx default mainThread
fun <T> Observable<T>.defThread(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

//net error parse
fun Throwable.parse(iHandler: (error: Error, message: String) -> Unit) {
    ExceptionParseMgr.Instance.parseException(this, iHandler)
}

//rxPermissions【https://ww4.sinaimg.cn/large/6a195423jw1ezwpc11cs0j20hr0majwm.jpg】
fun Activity.requestPermission(rxPermissions: RxPermissions, success: () -> Unit, vararg permissions: String) {
    rxPermissions.request(*permissions)
            .subscribe {
                if (it) {
                    success.invoke()
                } else {
                    toast("Permission request fail")
                }
            }
}

fun Fragment.requestPermission(rxPermissions: RxPermissions, success: () -> Unit, vararg permissions: String) {
    rxPermissions.request(*permissions)
            .subscribe {
                if (it) {
                    success.invoke()
                } else {
                    toast("Permission request fail")
                }
            }
}

