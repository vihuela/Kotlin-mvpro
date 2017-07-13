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

package com.github.kotlin_mvpro.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.api.request.NewsRequest
import com.github.kotlin_mvpro.databinding.CommonListBinding
import com.github.kotlin_mvpro.ui.adapter.NewsListAdapter
import com.github.kotlin_mvpro.ui.base.BaseFragment
import com.github.kotlin_mvpro.ui.presenter.NewsFragmentPresenter
import com.github.kotlin_mvpro.ui.view.INewsFragment
import com.github.kotlin_mvpro.utils.LIST_TOP
import com.github.library.utils.RouterImpl
import com.github.library.utils.eventbus.Event
import com.github.library.utils.router
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.RefreshLayout
import com.github.refresh.interfaces.IRefreshStateView

class NewsFragment : BaseFragment<NewsFragmentPresenter, CommonListBinding>(), INewsFragment {

    override fun onFirstUserVisible() {

        mBinding.mRefreshLayout.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NewsListAdapter()
        adapter.setOnItemClickListener { adapter, view, position ->
            val storiesBean = adapter.getItem(position) as NewsRequest.ListRes.StoriesBean
            router(RouterImpl.NewsDetailActivity, Pair("id", storiesBean.id), Pair("imageUrl", storiesBean.getImageUrl()))
        }
        mBinding.mRefreshLayout.setPageSize(Api.pageSize)
                .setPageStartOffset(0)
                .setViewType(RefreshCustomerLayout.Refresh_LoadMore)
                .setRefreshListener(object : RefreshCustomerLayout.IRefreshListener {
                    override fun onLoadMore(targetPage: Int) {
                        mPresenter.getNewsListForDate(targetPage)
                    }

                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        mPresenter.getNewsList()
                    }
                })
                .setStateListener(object : IRefreshStateView {
                    override fun showMessage(content: String) {
                        this@NewsFragment.showMessage(content)
                    }

                    override fun showMessageFromNet(error: Any, content: String) {
                        this@NewsFragment.showMessageFromNet(error, content)
                    }

                    override fun showEmpty() {
                        this@NewsFragment.showEmpty()
                    }

                    override fun showContent() {
                        this@NewsFragment.showContent()
                    }
                }).setAdapter(adapter)

        /*mBinding.mRefreshLayout.setTotalPage(20)*/
        mBinding.mRefreshLayout.startRequest()
    }

    //refreshLayout
    override fun setData(beanList: List<*>, loadMore: Boolean) {
        mBinding.mRefreshLayout.setData(beanList, loadMore)
    }

    //refreshLayout
    override fun setMessage(error: Any, content: String) {
        mBinding.mRefreshLayout.setMessage(error, content)
    }


    override fun showLoading() {
        if (mBinding.mRefreshLayout.isEmpty)
            super<BaseFragment>.showLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        if (mBinding.mRefreshLayout.isEmpty) {
            super<BaseFragment>.showMessageFromNet(error, content)
        } else {
            super<BaseFragment>.showMessage(content)
        }
    }


    override fun getLayoutId(): Int = R.layout.common_list

    override fun onStateViewRetryListener() {
        mBinding.mRefreshLayout.startRequest()
    }

    override fun <T> onEvent(event: Event<T>?) {
        super.onEvent(event)
        when (event?.code) {
            LIST_TOP -> mBinding.mRefreshLayout.recyclerView.smoothScrollToPosition(0)
        }
    }

    override fun isRegisterEventBus(): Boolean = true
}