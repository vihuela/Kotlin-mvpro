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
import androidx.annotation.FloatRange
import android.view.View
import android.view.ViewGroup
import com.github.library.utils.java.StatusBarUtil


//apply for StatusBarUtil
//使用了状态栏填充之后，整体布局会往上，需要调整布局
//在xml中需要修改的View加上：android:contentDescription="status_padding" 修改布局的padding
//android:contentDescription="status_margin" 修改布局的margin
fun Activity.applyStatusBar(color: Int = 0, @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0f) {
    updateContent(this, color, alpha, false)
}

fun Activity.applyStatusBarDark(color: Int = 0, @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0f) {
    updateContent(this, color, alpha, true)
}

private fun updateContent(aty: Activity, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float, darkMode: Boolean) {

    val root: View = aty.findViewById(android.R.id.content)

    val targetRoot: ViewGroup = root as? ViewGroup ?: return

    when (darkMode) {
        true -> StatusBarUtil.darkMode(aty, color, alpha)
        false -> StatusBarUtil.immersive(aty, color, alpha)
    }
    //padding
    with(arrayListOf<View>()) {
        targetRoot.findViewsWithText(this, "status_padding", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        forEach { StatusBarUtil.setPaddingSmart(it.context, it) }
    }
    //margin
    with(arrayListOf<View>()) {
        targetRoot.findViewsWithText(this, "status_margin", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        forEach { StatusBarUtil.setMargin(it.context, it) }
    }
}
