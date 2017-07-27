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

package com.github.kotlin_mvpro.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import com.github.kotlin_mvpro.BR
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.databinding.ActivityNewsDetailBinding
import com.github.kotlin_mvpro.ui.base.BaseActivity
import com.github.kotlin_mvpro.ui.presenter.WebViewActivityPresenter
import com.github.kotlin_mvpro.utils.RouterImpl
import com.github.mzule.activityrouter.annotation.Router
import com.just.library.AgentWeb

@Router(RouterImpl.WebViewActivity)
class WebViewActivity : BaseActivity<WebViewActivityPresenter, ActivityNewsDetailBinding>() {

    lateinit var mAgentWeb: AgentWeb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.context, this)
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mBinding.contain, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback({ _, title -> mBinding.toolbar.title = title })
                .createAgentWeb()
                .ready().go("")
        mPresenter.onLoadCallback = { title, data ->
            run {
                showContent()
                mBinding.toolbar.title = title
                mAgentWeb.loader.loadUrl(data)
            }
        }
        val idStr = intent.getStringExtra("id") ?: return
        mPresenter.getNewsDetail(idStr.toInt())
    }

    override fun getLayoutId(): Int = R.layout.activity_news_detail

    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle?.onDestroy()
    }
}