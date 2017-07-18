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

import android.widget.Button
import android.widget.TextView
import com.github.library.R
import com.kennyc.view.MultiStateView
import org.jetbrains.anko.findOptional

//apply for MultiStateView
interface IStateView {

    //default layout is multiStateView
    fun getStateView(): MultiStateView? = null

    //error layout retry callback
    fun onStateViewRetryListener() = Unit

    fun stateViewLoading() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    fun stateViewError(error: Any, content: String) {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_ERROR
        val errorView = getStateView()?.getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<TextView>(R.id.tv)
        val retryButton = getStateView()?.getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<Button>(R.id.retry)
        errorView?.text = content
        retryButton?.setOnClickListener { onStateViewRetryListener() }
    }

    fun stateViewEmpty() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    fun stateViewContent() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

}
