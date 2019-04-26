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

import androidx.databinding.ViewDataBinding
import android.os.Bundle
import com.github.kotlin_mvpro.R
import com.github.library.utils.ext.toast
import com.github.library.utils.impl.IEventBus
import com.github.library.utils.impl.IStateView
import com.github.library.widget.ITitlebar
import com.github.library.widget.Titlebar
import com.kennyc.view.MultiStateView
import com.ricky.mvp_core.base.BaseBindingActivity
import com.ricky.mvp_core.base.BasePresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import org.jetbrains.anko.findOptional

//stateView and eventBus
abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>(), IStateView, IEventBus, ITitlebar {

    val mRxPermissions: RxPermissions by lazy { RxPermissions(this) }
    val mTitlebar: Titlebar? by lazy { findOptional<Titlebar>(R.id.titlebar)?.setLeftClick { finish() } }

    override fun getStateView(): MultiStateView? {
        return super.getStateView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.registerEventBus(this)
        super.stateViewSetup(this)
        if (isInitTitlebar()) mTitlebar
    }

    override fun onDestroy() {
        super.onDestroy()
        super.unregisterEventBus(this)
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
