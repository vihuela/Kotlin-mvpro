package com.github.kotlin_mvpro.api

import com.github.kotlin_mvpro.api.request.ImageRequest
import io.reactivex.Observable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

interface ApiCacheProvider {
    companion object {
        val IMPL: ApiCacheProvider = ApiUtils.apiProvider
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getImageList(resObservable: Observable<ImageRequest.Res>, url: DynamicKey, evictDynamicKey: EvictDynamicKey): Observable<Reply<ImageRequest.Res>>
}