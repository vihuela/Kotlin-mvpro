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

import android.app.Application
import com.github.library.utils.eventbus.Event
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xiaofei.library.hermeseventbus.HermesEventBus

//apply for eventBus
interface IEventBus {

    fun isRegisterEventBus() = false

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: Event<T>?) = Unit

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun <T> onEventSticky(event: Event<T>?) = Unit

    fun registerEventBus(subscriber: Any) {
        if (isRegisterEventBus()) {
            HermesEventBus.getDefault().register(subscriber)
        }

    }

    fun unregisterEventBus(subscriber: Any) {
        if (isRegisterEventBus()) {
            HermesEventBus.getDefault().unregister(subscriber)
        }
    }

    //apply for HermesEventBus，进程间通讯的eventBus
    //传递对象会被序列号成linkedTreeMap
    companion object {
        //进程结束时调用
        fun destroy() {
            HermesEventBus.getDefault().destroy()
        }

        //在Application中进程初始化调用
        fun init(context: Application, isMainProcess: Boolean) {
            when (isMainProcess) {
                true -> HermesEventBus.getDefault().init(context)
                false -> HermesEventBus.getDefault().connectApp(context, context.packageName)
            }

        }
    }


}