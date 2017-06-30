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

package com.github.kotlin_mvpro.api

import com.github.kotlin_mvpro.api.request.ImageRequest
import com.github.kotlin_mvpro.api.request.NewsRequest
import io.reactivex.Observable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

interface ApiCacheProvider {
    companion object {
        val IMPL: ApiCacheProvider = ApiUtils.apiProvider
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getImageList(resObservable: Observable<ImageRequest.Res>, url: DynamicKey, evictDynamicKey: EvictDynamicKey): Observable<Reply<ImageRequest.Res>>

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getNewsList(O: Observable<NewsRequest.ListRes>, evictProvider: EvictProvider): Observable<Reply<NewsRequest.ListRes>>

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getNewsListForDate(O: Observable<NewsRequest.ListRes>, url: DynamicKey, evictDynamicKey: EvictDynamicKey): Observable<Reply<NewsRequest.ListRes>>

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getNewDetail(O: Observable<NewsRequest.DetailRes>, url: DynamicKey, evictDynamicKey: EvictDynamicKey): Observable<Reply<NewsRequest.DetailRes>>
}