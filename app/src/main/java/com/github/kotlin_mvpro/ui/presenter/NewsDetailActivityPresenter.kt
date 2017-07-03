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

package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.api.ApiCacheProvider
import com.github.kotlin_mvpro.api.ApiUtils
import com.github.kotlin_mvpro.ui.view.INewsDetailActivity
import com.github.library.base.BasePresenter
import com.github.library.utils.defThread
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import github.library.parser.ExceptionParseMgr
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey

class NewsDetailActivityPresenter : BasePresenter<INewsDetailActivity>() {
    var onLoadCallback: ((title: String, data: String) -> Unit)? = null

    override fun onViewCreated(view: INewsDetailActivity, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getNewsDetail(id: Int) {
        val api = Api.IMPL.getNewDetail(id)
        ApiCacheProvider.IMPL.getNewDetail(api, DynamicKey(id), EvictDynamicKey(ApiUtils.isRxCacheEvict))
                .defThread()
                .bindToLifecycle(this)
                .doOnSubscribe { view()!!.showLoading() }
                .subscribe({
                    onLoadCallback?.invoke(it.data.title, it.data.share_url)
                },
                        {
                            ExceptionParseMgr.Instance.parseException(it, { error, message -> view()!!.showMessageFromNet(error, message) })
                        }, { view()!!.hideLoading() })
    }

    fun convertBody(preResult: String): String {
        var preResult = preResult

        preResult = preResult.replace("<div class=\"img-place-holder\">", "")
        preResult = preResult.replace("<div class=\"headline\">", "")

        val css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">"

        val theme = "<body yclassName=\"\" onload=\"onLoaded()\">"

        return StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString()
    }
}
