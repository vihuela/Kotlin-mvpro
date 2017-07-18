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

package com.github.kotlin_mvpro.ui.base

import android.app.Service
import com.github.library.utils.impl.IEventBus

abstract class BaseService : Service(), IEventBus {
    override fun onCreate() {
        super.onCreate()
        super.registerEventBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        super.unregisterEventBus(this)
    }
}
