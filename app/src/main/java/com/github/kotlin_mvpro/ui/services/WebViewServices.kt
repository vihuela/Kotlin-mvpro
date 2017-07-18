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

package com.github.kotlin_mvpro.ui.services

import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.github.kotlin_mvpro.ui.base.BaseService
import com.github.kotlin_mvpro.utils.RouterImpl
import com.github.library.utils.eventbus.Event
import com.github.library.utils.impl.IEventBus
import com.github.library.utils.killCurrentProcess

class WebViewServices : BaseService() {
    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        fun start(context: Context) {
            context.startService(Intent(context, WebViewServices::class.java))
        }
    }

    override fun isRegisterEventBus(): Boolean = true
    override fun <T> onEvent(event: Event<T>?) {
        super.onEvent(event)
        when (event?.code) {
            RouterImpl.WebViewActivityDestroy -> {
                IEventBus.destroy()
                stopSelf()
                application.killCurrentProcess()
            }
        }
    }

}