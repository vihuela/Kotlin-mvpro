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
import com.github.kotlin_mvpro.ui.view.INewsFragment
import com.github.library.utils.ext.bindToBehavior
import com.github.library.utils.ext.defPolicy_Retry
import com.github.library.utils.ext.getBehavior
import com.github.library.utils.ext.parse
import com.ricky.mvp_core.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.EvictProvider
import java.text.SimpleDateFormat
import java.util.*

class NewsFragmentPresenter : BasePresenter<INewsFragment>() {


    override fun onViewCreated(view: INewsFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getNewsList() {

        val api = Api.IMPL.getNewsList()
        ApiCacheProvider.IMPL.getNewsList(api)
                .defPolicy_Retry(this)
                .map { it.data.stories }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view().setData(it, false) },
                        { it.parse({ error, message -> view().setMessage(error, message) }) })
    }

    fun getNewsListForDate(page: Int = 1) {

        val dateString = getNextDay(-1 * page)
        val api = Api.IMPL.getNewsListForDate(dateString)
        ApiCacheProvider.IMPL.getNewsListForDate(api, DynamicKey(page))
                .defPolicy_Retry(this)
                .map { it.data.stories }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view().setData(it, true) },
                        { it.parse({ error, message -> view().setMessage(error, message) }) })
    }

    private fun getNextDay(delay: Int): String {
        return try {
            val format = SimpleDateFormat("yyyyMMdd", Locale.CHINESE)
            val d = Date()
            d.let { it.time = (it.time / 1000 + delay * 24 * 60 * 60) * 1000 }
            format.format(d)
        } catch (e: Exception) {
            ""
        }

    }
}