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

package com.github.library.utils.impl

import android.app.Activity
import android.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.library.R
import com.kennyc.view.MultiStateView
import org.jetbrains.anko.contentView
import org.jetbrains.anko.findOptional

//apply for MultiStateView
interface IStateView {

    //default layout is multiStateView
    //接口内部不保存变量，每次操作都会回调，所以外部可以配合懒加载，避免每次创建
    fun getStateView(): MultiStateView? = null

    //error layout retry callback
    fun onStateViewRetryListener() = Unit

    fun stateViewLoading() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    fun stateViewError(error: Any, content: String) {
        getStateView()?.apply {
            viewState = MultiStateView.VIEW_STATE_ERROR
            val errorView = getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<TextView>(R.id.tv)
            val retryButton = getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<Button>(R.id.retry)
            errorView?.text = content
            retryButton?.setOnClickListener { onStateViewRetryListener() }
        }
    }

    fun stateViewEmpty() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    fun stateViewContent() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    fun <T> stateViewInit(context: T): MultiStateView? {
        when (context) {
            is Fragment -> {
                return defaultInit(context.view)
            }
            is Activity -> {
                return defaultInit(context.contentView)
            }
            else -> throw IllegalArgumentException("context must be Fragment Or Activity")
        }
    }

    private fun defaultInit(view: View?): MultiStateView? {
        val multiStateView = view?.findOptional<MultiStateView>(R.id.multiStateView)
        multiStateView?.apply {
            setViewForState(R.layout.error_view, MultiStateView.VIEW_STATE_ERROR)
            setViewForState(R.layout.empty_view, MultiStateView.VIEW_STATE_EMPTY)
            setViewForState(R.layout.loading_view, MultiStateView.VIEW_STATE_LOADING, true)
            setAnimateLayoutChanges(true)
        }
        return multiStateView
    }
}
