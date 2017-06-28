package com.github.kotlin_mvpro.ui.view

import com.github.library.base.interfaces.IView
import github.library.utils.Error

interface IList : IView {

    fun setData(beanList: List<*>, loadMore: Boolean)

    fun setMessage(error: Any, content: String)
}