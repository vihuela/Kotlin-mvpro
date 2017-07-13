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

package com.github.kotlin_mvpro.ui

import android.Manifest
import android.app.Fragment
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import com.blankj.utilcode.util.SnackbarUtils
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.ApiUtils
import com.github.kotlin_mvpro.databinding.ActivityMainBinding
import com.github.kotlin_mvpro.ui.base.BaseActivity
import com.github.kotlin_mvpro.ui.fragment.ImageFragment
import com.github.kotlin_mvpro.ui.fragment.NewsFragment
import com.github.kotlin_mvpro.utils.LIST_TOP
import com.github.library.utils.eventbus.Event
import com.github.library.utils.eventbus.sendEvent
import com.github.library.utils.requestPermission
import com.github.library.utils.toast
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.ricky.mvp_core.base.defaults.EmptyPresenter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<EmptyPresenter, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rxNetState
        ReactiveNetwork.observeNetworkConnectivity(applicationContext)
                .subscribeOn(Schedulers.io())
                .bindToLifecycle(this)
                .flatMap { Observable.just((it.isAvailable && it.state == NetworkInfo.State.CONNECTED)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it) {
                        SnackbarUtils.with(findViewById(android.R.id.content))
                                .setMessage("网络未连接")
                                .setDuration(0)
                                .showWarning()
                    }
                    ApiUtils.isRxCacheEvict = it
                })

        val title = arrayOf("美图", "知乎日报")
        val items = arrayOf(ImageFragment(), NewsFragment())
        mBinding.pager.adapter = object : FragmentPagerAdapter(fragmentManager) {

            override fun getItem(position: Int): Fragment = items[position] as Fragment
            override fun getCount(): Int = title.size
            override fun getPageTitle(position: Int): CharSequence = title[position]
        }
        mBinding.tab.setViewPager(mBinding.pager, title)
        mBinding.top.setOnClickListener {
            sendEvent(Event.obtain(LIST_TOP, it.tag))
//            requestPermission(mRxPermissions, {
//                toast("权限获取成功")
//            }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}
