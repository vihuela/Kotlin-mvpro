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

package com.ricky.mvp_core.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.ricky.mvp_core.base.interfaces.IView
import com.ricky.mvp_core.utils.PresenterFactory
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * no presenter use {@link EmptyPresenter}
 */
abstract class BaseBindingActivity<T : BasePresenter<*>, B : ViewDataBinding> : RxAppCompatActivity(), IView {

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = createViewDataBinding()
        mPresenter = PresenterFactory.createPresenter(this)
    }

    private fun createViewDataBinding(): B = DataBindingUtil.setContentView(this, getLayoutId())

    abstract fun getLayoutId(): Int

}
