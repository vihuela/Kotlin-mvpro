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
import android.os.Debug
import com.blankj.utilcode.util.Utils
import com.github.kotlin_mvpro.api.ApiUtils
import io.paperdb.Paper

class App : Application() {
    override fun onCreate() {
        super.onCreate()

//        Debug.startMethodTracing("kotlin_mvpro")
        //api
        ApiUtils.init(this)
        //db
        Paper.init(this)
        //utils
        Utils.init(this)
//        Debug.stopMethodTracing()
    }
}