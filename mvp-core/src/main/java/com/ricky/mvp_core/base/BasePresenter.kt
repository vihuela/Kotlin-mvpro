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

import com.ricky.mvp_core.base.interfaces.IView
import com.ricky.mvp_core.utils.BehaviorMap

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
abstract class BasePresenter<V : IView> : RxViewModel() {

    var mView: V? = null//TODO
    val mBehaviorMap by lazy { BehaviorMap() }

    fun view(): V = mView!!

    //注意如果复用Presenter时，获得的View将是最后一个设置Presenter的
    fun setView(view: Any) {
        this.mView = view as V
    }

}
