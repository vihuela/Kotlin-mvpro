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

package com.github.kotlin_mvpro.ui.view

import com.ricky.kotlin_mvpro.base.interfaces.IView

interface IList : IView {

    fun setData(beanList: List<*>, loadMore: Boolean)

    fun setMessage(error: Any, content: String)
}