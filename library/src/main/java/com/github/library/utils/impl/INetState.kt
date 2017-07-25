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

package com.github.library.utils.impl

import android.app.Activity
import android.app.Application
import android.net.NetworkInfo
import android.os.Bundle
import com.blankj.utilcode.util.SnackbarUtils
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import java.util.concurrent.TimeUnit

//apply for ReactiveNetwork
interface INetState {


    fun observeNetwork(app: Application, netCallback: (isNetAvailable: Boolean) -> Unit) {
        val showSnackBar: (activity: Activity) -> Unit = { activity ->
            SnackbarUtils.with(activity.findViewById(android.R.id.content))
                    .setMessage("网络未连接")
                    .setDuration(0)
                    .showWarning()
        }

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {


            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }


            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                doAsync {
                    if (activity is LifecycleProvider<*>) {
                        ReactiveNetwork.observeNetworkConnectivity(activity.applicationContext)
                                .subscribeOn(Schedulers.io())
                                .bindToLifecycle(activity)
                                .compose { it }
                                .flatMap { Observable.just((it.isAvailable && it.state == NetworkInfo.State.CONNECTED)) }
                                .map {
                                    netCallback.invoke(it)
                                    it
                                }
                                .filter { !it }
                                .observeOn(AndroidSchedulers.mainThread())
                                .delay(300,TimeUnit.MILLISECONDS)
                                .subscribe({
                                    showSnackBar.invoke(activity)
                                })

                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}