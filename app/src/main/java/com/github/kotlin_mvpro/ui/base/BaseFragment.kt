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

package com.github.kotlin_mvpro.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.github.kotlin_mvpro.R
import com.github.library.utils.IStateViewIMPL
import com.github.library.utils.eventbus.IEventBusIMPL
import com.github.library.utils.toast
import com.kennyc.view.MultiStateView
import com.ricky.mvp_core.base.BaseBindingFragment
import com.ricky.mvp_core.base.BasePresenter
import org.jetbrains.anko.findOptional

//stateView and eventBus
abstract class BaseFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingFragment<T, B>(), IStateViewIMPL , IEventBusIMPL {

    override fun getStateView(): MultiStateView? {
        return view.findOptional<MultiStateView>(R.id.multiStateView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.registerEventBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        super.unregisterEventBus(this)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun showLoading() {
        super.showLoading()
        super.stateViewLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
        super.stateViewError(error, content)
    }

    override fun showEmpty() {
        super.showEmpty()
        super.stateViewEmpty()
    }

    override fun showContent() {
        super.showContent()
        super.stateViewContent()
    }

    override fun showMessage(content: String) {
        super.showMessage(content)
        toast(content)
    }

    override fun hideLoading() {
        super.hideLoading()
        showContent()
    }

}
