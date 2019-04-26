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

package com.github.library.utils.ext

import android.app.Activity
import android.app.Service
import androidx.fragment.app.Fragment
import com.github.mzule.activityrouter.router.Routers

//apply for ActivityRouter

fun Activity.router(toPage: String, vararg pairs: Pair<*, *>) {
    Routers.open(this, "router://$toPage${getParamString(pairs)}")
}

fun Activity.routerForResult(toPage: String, requestCode: Int, vararg pairs: Pair<*, *>) {
    Routers.openForResult(this, "router://$toPage${getParamString(pairs)}", requestCode)
}

fun Fragment.router(toPage: String, vararg pairs: Pair<*, *>) {
    Routers.open(activity, "router://$toPage${getParamString(pairs)}")
}

fun Service.router(toPage: String, vararg pairs: Pair<*, *>) {
    Routers.open(this, "router://$toPage${getParamString(pairs)}")
}

private fun getParamString(pairs: Array<out Pair<*, *>>): String {
    var param: String = ""
    pairs.forEach {
        param = param.plus("${it.first}=${it.second}&")
    }

    if (param.isNotBlank()) {
        param = "?${param.substring(0, param.length - 1)}"
    }
    return param
}