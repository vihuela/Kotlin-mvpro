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

package com.github.library.utils.eventbus

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.app.Service
import org.greenrobot.eventbus.EventBus

data class Event<T>(var code: Int, var data: T) {

    companion object {
        fun obtain(code: Int): Event<*> = Event(code, null)
        fun <T> obtain(code: Int, data: T): Event<T> = Event(code, data)
    }
}

//fragment Ext
fun Fragment.sendEvent(event: Event<*>) {
    EventBus.getDefault().post(event)
}

fun Fragment.sendEventSticky(event: Event<*>) {
    EventBus.getDefault().postSticky(event)
}

//activity Ext
fun Activity.sendEvent(event: Event<*>) {
    EventBus.getDefault().post(event)
}

fun Activity.sendEventSticky(event: Event<*>) {
    EventBus.getDefault().postSticky(event)
}

//service Ext
fun Service.sendEvent(event: Event<*>) {
    EventBus.getDefault().post(event)
}

fun Service.sendEventSticky(event: Event<*>) {
    EventBus.getDefault().postSticky(event)
}

//application Ext
fun Application.sendEvent(event: Event<*>) {
    EventBus.getDefault().post(event)
}

fun Application.sendEventSticky(event: Event<*>) {
    EventBus.getDefault().postSticky(event)
}
