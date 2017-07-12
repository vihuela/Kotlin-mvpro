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
import com.github.library.utils.eventbus.IEventBusIMPL
import com.github.library.utils.eventbus.registerEventbus
import com.github.library.utils.eventbus.unregisterEventbus
import com.ricky.mvp_core.base.BaseBindingActivity
import com.ricky.mvp_core.base.BasePresenter

//stateView and eventBus
abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>() , IEventBusIMPL {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.registerEventBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        super.unregisterEventBus(this)
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
    }

    override fun showEmpty() {
        super.showEmpty()
    }

    override fun showContent() {
        super.showContent()
    }

    override fun showMessage(content: String) {
        super.showMessage(content)
    }

    override fun hideLoading() {
        super.hideLoading()
    }
}
