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
import com.blankj.utilcode.util.Utils
import com.github.library.R
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
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
                    .setMessage(Utils.getContext().resources.getText(R.string.network_retry_tip))
                    .setDuration(0)
                    .showWarning()
        }

        val activityStateMap = mutableMapOf<String, Boolean>().withDefault { true }

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {


            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }


            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                doAsync {
                    if (activity is LifecycleProvider<*>) {
                        ReactiveNetwork
                                .observeNetworkConnectivity(activity.applicationContext)
                                .subscribeOn(Schedulers.io())
                                .bindUntilEvent(activity as LifecycleProvider<ActivityEvent>, ActivityEvent.DESTROY)//release util onDestroy
                                .compose {//compose operate ObservableSource，not consumer callBack
                                    activityStateMap.put(activity.javaClass.simpleName, true)
                                    it.flatMap { Observable.just((it.isAvailable && it.state == NetworkInfo.State.CONNECTED)) }
                                }
                                .map {
                                    netCallback.invoke(it)
                                    it
                                }
                                .filter { !it && activityStateMap[activity.javaClass.simpleName] ?: true }
                                .observeOn(AndroidSchedulers.mainThread())
                                .delay(300, TimeUnit.MILLISECONDS)
                                .subscribe({
                                    showSnackBar.invoke(activity)
                                })
                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                doAsync { activityStateMap.put(activity.javaClass.simpleName, true) }
            }

            override fun onActivityPaused(activity: Activity) {
                doAsync { activityStateMap.put(activity.javaClass.simpleName, false) }
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}