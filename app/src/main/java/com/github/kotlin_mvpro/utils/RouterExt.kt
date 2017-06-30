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

package com.github.kotlin_mvpro.utils

import android.app.Fragment
import android.content.Context
import com.github.mzule.activityrouter.router.Routers

class RouterImpl {
    companion object {
        const val Scheme = "ricky://"
        const val NewsDetailFrom = RouterImpl.Scheme + "newsDetail" + "?id="
        const val NewsDetailTo = "newsDetail" + ":id"
    }

}

fun Context.router(path: String) {
    Routers.open(this, path)
}

fun Fragment.router(path: String) {
    Routers.open(this.activity, path)
}