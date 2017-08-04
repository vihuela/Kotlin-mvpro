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
import com.github.library.utils.ext.defPolicy_Retry
import com.github.library.utils.ext.parse
import com.ricky.mvp_core.base.BasePresenter
import com.ricky.mvp_core.base.interfaces.IView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey

class WebViewActivityPresenter : BasePresenter<IView>() {
    var onLoadCallback: ((title: String, data: String) -> Unit)? = null

    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }


    fun getNewsDetail(id: Int) {
        val api = Api.IMPL.getNewDetail(id)
        ApiCacheProvider.IMPL.getNewDetail(api, DynamicKey(id), EvictDynamicKey(ApiUtils.isRxCacheEvict))
                .defPolicy_Retry(this)
                .doOnSubscribe { view().showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onLoadCallback?.invoke(it.data.title, it.data.share_url) },
                        { it.parse({ error, message -> view().showMessageFromNet(error, message) }) })

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
