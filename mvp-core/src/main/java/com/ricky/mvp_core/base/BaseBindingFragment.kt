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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ricky.mvp_core.base.interfaces.IView
import com.ricky.mvp_core.utils.PresenterFactory

/**
 * no presenter use {@link EmptyPresenter}
 */
abstract class BaseBindingFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseLazyFragment(), IView {

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mPresenter = PresenterFactory.createPresenter(this)
        return mBinding.root
    }

    abstract fun getLayoutId(): Int

}
