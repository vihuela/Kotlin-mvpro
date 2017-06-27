package com.github.kotlin_mvpro

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.github.kotlin_mvpro.api.ApiUtils

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        ApiUtils.init(this)
        Utils.init(this)
    }
}