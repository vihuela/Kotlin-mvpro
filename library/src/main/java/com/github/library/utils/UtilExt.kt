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

package com.github.library.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.github.library.utils.eventbus.Event
import github.library.parser.ExceptionParseMgr
import github.library.utils.Error
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus


fun Fragment.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    when (length) {
        Toast.LENGTH_SHORT -> ToastUtils.showShortSafe(msg)
        Toast.LENGTH_LONG -> ToastUtils.showLongSafe(msg)
    }
}

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    when (length) {
        Toast.LENGTH_SHORT -> ToastUtils.showShortSafe(msg)
        Toast.LENGTH_LONG -> ToastUtils.showLongSafe(msg)
    }
}

fun Activity.hideKeyboard(): Boolean {
    val view = currentFocus
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}



