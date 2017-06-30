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

import android.content.Context
import android.net.NetworkInfo
import com.github.library.utils.defThread
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {

    lateinit var retrofitClient: Retrofit
    lateinit var api: Api
    lateinit var apiProvider: ApiCacheProvider
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        this.retrofitClient = Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        api = this.retrofitClient.create(Api::class.java)
        //rxCache
        apiProvider = RxCache.Builder()
                .persistence(this.context.filesDir, GsonSpeaker())
                .using(ApiCacheProvider::class.java)
        //db
        Paper.init(context)
    }

}