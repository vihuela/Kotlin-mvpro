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

package com.ricky.kotlin_mvpro.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricky.kotlin_mvpro.base.interfaces.IView
import com.ricky.kotlin_mvpro.utils.PresenterFactory

/**
 * no presenter use {@link EmptyPresenter}
 */
abstract class BaseBindingFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseLazyFragment(), IView {

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T


    override fun getContext(): Context = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) super.getContext() else activity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mPresenter = PresenterFactory.createPresenter(this)
        return mBinding.root
    }

    abstract fun getLayoutId(): Int

}
