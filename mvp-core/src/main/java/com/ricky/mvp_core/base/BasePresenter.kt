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
import com.ricky.mvp_core.base.interfaces.IView
import com.ricky.mvp_core.utils.BehaviorMap
import com.trello.rxlifecycle3.components.support.RxFragment

/**
 *
 * <h3>Build Presenter with Fragment：</h3>
 *
 * Fragment has a life cycle
 *
 * FragmentManager can cache fragment
 *
 * Coordinate with RxLifeCycle
 *
 */
abstract class BasePresenter<V : IView> : RxFragment() {
    var mView: V? = null
    val mBehaviorMap by lazy { BehaviorMap() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView != null)
            onViewCreated(mView!!, arguments, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(view: V, arguments: Bundle?, savedInstanceState: Bundle?)

    //abandon
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun view(): V = mView!!

    fun setView(view: Any) {
        this.mView = view as V
    }

}
