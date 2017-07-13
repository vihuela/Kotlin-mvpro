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

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//apply for eventBus
interface IEventBusIMPL {

    fun isRegisterEventBus() = false

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: Event<T>?) = Unit

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun <T> onEventSticky(event: Event<T>?) = Unit

    fun registerEventBus(subscriber: Any) {
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(subscriber)
        }

    }

    fun unregisterEventBus(subscriber: Any) {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

}