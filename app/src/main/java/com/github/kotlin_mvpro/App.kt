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

package com.github.kotlin_mvpro

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.github.kotlin_mvpro.api.ApiUtils
import com.github.library.utils.getProcessName
import com.github.library.utils.impl.INetState
import io.paperdb.Paper

class App : Application(), INetState {
    override fun onCreate() {
        super.onCreate()

        when (getProcessName()) {
            packageName -> {
                commonInit()
                observeNetwork(this, { ApiUtils.isRxCacheEvict = it })
            }
        }
    }

    fun commonInit() {
        //api
        ApiUtils.init(this)
        //db
        Paper.init(this)
        //utils
        Utils.init(this)
    }

    //wait future use
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when {
            level > Application.TRIM_MEMORY_UI_HIDDEN -> {
            }
        }
    }
}