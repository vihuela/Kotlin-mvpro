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

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.databinding.CommonListBinding
import com.github.kotlin_mvpro.model.ImageItem
import com.github.kotlin_mvpro.ui.adapter.ImageListAdapter
import com.github.kotlin_mvpro.ui.base.BaseFragment
import com.github.kotlin_mvpro.ui.presenter.ImageFragmentPresenter
import com.github.kotlin_mvpro.ui.view.IImageFragment
import com.github.kotlin_mvpro.ui.widget.GridItemDecoration
import com.github.kotlin_mvpro.utils.LIST_TOP
import com.github.library.utils.eventbus.Event
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.RefreshLayout
import com.github.refresh.interfaces.IRefreshStateView

class ImageFragment : BaseFragment<ImageFragmentPresenter, CommonListBinding>(), IImageFragment {

    override fun onFirstUserVisible() {

        mBinding.mRefreshLayout.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mBinding.mRefreshLayout.recyclerView.addItemDecoration(GridItemDecoration(2, SizeUtils.dp2px(5f), false))
        val imageListAdapter = ImageListAdapter()
        imageListAdapter.setOnItemClickListener { adapter, view, position ->
            val iv = view as ImageView
            //load thumb complete
            if (iv.drawable !is ColorDrawable) {
                mPresenter.openImageDetail(iv, (adapter.getItem(position) as ImageItem), position)
            }
        }
        mBinding.mRefreshLayout.setPageSize(Api.pageSize)
                .setPageStartOffset(Api.startOffset)
                .setViewType(RefreshCustomerLayout.Refresh_LoadMore)
                .setRefreshListener(object : RefreshCustomerLayout.IRefreshListener {
                    override fun onLoadMore(targetPage: Int) {
                        mPresenter.getImageList(targetPage, true)
                    }

                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        mPresenter.getImageList(mBinding.mRefreshLayout.pageStartOffset, false)
                    }
                })
                .setStateListener(object : IRefreshStateView {
                    override fun showMessage(content: String) {
                        this@ImageFragment.showMessage(content)
                    }

                    override fun showMessageFromNet(error: Any, content: String) {
                        this@ImageFragment.showMessageFromNet(error, content)
                    }

                    override fun showEmpty() {
                        this@ImageFragment.showEmpty()
                    }

                    override fun showContent() {
                        this@ImageFragment.showContent()
                    }
                }).setAdapter(imageListAdapter)

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
            LIST_TOP -> if (userVisibleHint) mBinding.mRefreshLayout.recyclerView.smoothScrollToPosition(0)
        }
    }

    override fun isRegisterEventBus(): Boolean = true
}