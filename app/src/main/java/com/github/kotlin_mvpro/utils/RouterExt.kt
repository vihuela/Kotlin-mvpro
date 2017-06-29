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