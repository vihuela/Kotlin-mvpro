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

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.api.ApiCacheProvider
import com.github.kotlin_mvpro.api.ApiUtils
import com.github.kotlin_mvpro.api.request.BaseRequest
import com.github.kotlin_mvpro.model.ImageItem
import com.github.kotlin_mvpro.ui.view.IImageFragment
import com.github.library.utils.ext.bindToBehavior
import com.github.library.utils.ext.defPolicy_Retry
import com.github.library.utils.ext.getBehavior
import com.github.library.utils.ext.parse
import com.hitomi.glideloader.GlideImageLoader
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.ricky.mvp_core.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.Reply

class ImageFragmentPresenter : BasePresenter<IImageFragment>() {

    lateinit var transferConfig: TransferConfig
    lateinit var transferee: Transferee
    var bp: BehaviorProcessor<Boolean>? = null

    override fun onViewCreated(view: IImageFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
        transferee = Transferee.getDefault(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        transferee.destroy()
    }

    fun openImageDetail(iv: ImageView, item: ImageItem, position: Int) {
        val imageViewList = arrayListOf<ImageView>().apply { add(iv) }
        val imagesList = arrayListOf<ImageItem>().apply { add(item) }

        transferConfig = TransferConfig.build()
                .setSourceImageList(imagesList.map { it.url })//SourceImageList\OriginImageList size一致
                .setOriginImageList(imageViewList)
                .setMissDrawable(ColorDrawable(Color.parseColor("#DCDDE1")))
                .setErrorDrawable(ColorDrawable(Color.parseColor("#DCDDE1")))
                .setProgressIndicator(ProgressPieIndicator())
                .setNowThumbnailIndex(0)//仅一张
                .setImageLoader(GlideImageLoader.with(activity.applicationContext))
                .create()
        transferee.apply(transferConfig).show()
    }

    fun getImageList(page: Int = 1, loadMore: Boolean) {

        bp = getBehavior(bp)//cancel lastTime request

        val api = Api.IMPL.getImageList(Api.pageSize, page)
        val cacheApi = ApiCacheProvider.IMPL.getImageList(api, DynamicKey(page), EvictDynamicKey(ApiUtils.isRxCacheEvict))

        cacheApi//可以切换api和cacheApi
                .defPolicy_Retry(this)
                .bindToBehavior(bp!!)
                .compose(ApplyFilter())
                .map {
                    //cacheApi
                    it.data.results.map { ImageItem(it.url) }
                    //api
                    /*it.results.map { ImageItem(it.url) }*/
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view().setData(it, loadMore)
                },
                        {
                            it.parse({ error, message -> view().setMessage(error, message) })
                        })
    }

    //带业务性质的过滤数据
    class ApplyFilter<T> : ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            val a = upstream.flatMap {
                when (it) {
                //normal
                    is BaseRequest<*> -> {
                        val b = it as BaseRequest<*>
                        if (!b.error) {
                            Observable.just(it)
                        } else {
                            Observable.error(IllegalArgumentException("server business error"))
                        }
                    }
                //rxCache
                    is Reply<*> -> {
                        val b = (it as Reply<*>).data as BaseRequest<*>
                        if (!b.error) {
                            Observable.just(it)
                        } else {
                            Observable.error(IllegalArgumentException("server business error"))
                        }
                    }
                    else -> {
                        Observable.just(it)
                    }
                }
            }
            return a as ObservableSource<T>
        }
    }
}