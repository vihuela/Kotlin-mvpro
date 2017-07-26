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
import com.blankj.utilcode.util.ToastUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import github.library.parser.ExceptionParseMgr
import github.library.utils.Error
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//rx default mainThread
fun <T> Observable<T>.defThread(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

//net error parse
fun Throwable.parse(iHandler: (error: Error, message: String) -> Unit) {
    ExceptionParseMgr.Instance.parseException(this, iHandler)
}

//retry when error，default check time is 2's
fun <T> Observable<T>.defRetry(checkSeconds: Long = 20): Observable<T> {
    val everyCheckTime: Long = 2
    val totalCheckCount = if (checkSeconds > 2) checkSeconds / 2 else 1

    return this
            .observeOn(Schedulers.io())
            .retryWhen {
                var index = 0
                it.flatMap {
                    if (index < totalCheckCount) {
                        //only NetWork Error retry
                        if (ExceptionParseMgr.Instance.isMatchException(it) == Error.NetWork) {
                            index++
                            Observable.just(Unit).delay(everyCheckTime, TimeUnit.SECONDS)
                        } else {
                            Observable.error(it)
                        }
                    } else {
                        Observable.error(it)
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())

}

//rxPermissions【https://ww4.sinaimg.cn/large/6a195423jw1ezwpc11cs0j20hr0majwm.jpg】
fun Activity.requestAllPermission(rxPermissions: RxPermissions,
                                  success: () -> Unit,
                                  vararg permissions: String) {
    rxPermissions.request(*permissions)
            .subscribe {
                if (it) {
                    success.invoke()
                } else {
                    ToastUtils.showShortSafe("permission request fail")
                }
            }
}

//error: (denyName: String) -> Unit = { ToastUtils.showShortSafe("$it permission request fail") }
fun Activity.requestEachPermission(rxPermissions: RxPermissions,
                                   success: () -> Unit,
                                   vararg permissions: String) {
    rxPermissions.requestEach(*permissions)
            .subscribe {
                if (it.granted) {
                    success.invoke()
                } else if (it.shouldShowRequestPermissionRationale) {
                    ToastUtils.showShortSafe("${it.name} permission request fail")
                } else {
                    ToastUtils.showShortSafe("${it.name} permission request fail")
                }
            }
}

