package com.github.kotlin_mvpro.ui.view

import com.github.kotlin_mvpro.api.request.NewsRequest
import com.github.library.base.interfaces.IView


interface INewsDetailActivity : IView{
    fun setData(res: NewsRequest.DetailRes)
}