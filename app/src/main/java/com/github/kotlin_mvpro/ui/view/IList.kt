package com.github.kotlin_mvpro.ui.view

import com.github.library.base.interfaces.IView
import github.library.utils.Error

interface IList : IView {

    fun setTotalPage(totalPage: Int) = -1

    fun setData(beanList: List<*>, loadMore: Boolean)

    fun setMessage(error: Error, content: String)
}