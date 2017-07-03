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
import com.github.kotlin_mvpro.utils.NET_STATE
import io.paperdb.Paper
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KProperty

object ApiUtils {
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    //retrofit
    val api: Api by lazy {
        val okClientBuilder = OkHttpClient.Builder().apply {
            //log
            this.addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
            //...
        }
        Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .client(okClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api::class.java)
    }
    //rxCache
    val apiProvider: ApiCacheProvider by lazy {
        RxCache.Builder()
                .persistence(this.context.filesDir, GsonSpeaker())
                .using(ApiCacheProvider::class.java)
    }

    var isRxCacheEvict: Boolean by RxCacheEvict()

    class RxCacheEvict {
        operator fun getValue(any: Any, property: KProperty<*>): Boolean {
            return Paper.book().read(NET_STATE, true)
        }

        operator fun setValue(any: Any, property: KProperty<*>, b: Boolean) {
            Paper.book().write(NET_STATE, b)
        }
    }


}