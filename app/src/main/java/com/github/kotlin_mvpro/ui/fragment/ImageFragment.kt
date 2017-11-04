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
import com.github.kotlin_mvpro.utils.LIST_TOP
import com.github.library.utils.eventbus.Event
import com.github.library.utils.ext.setRefreshListener
import com.github.library.utils.ext.setViewStateListener
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.util.GridItemDecoration

class ImageFragment : BaseFragment<ImageFragmentPresenter, CommonListBinding>(), IImageFragment {

    override fun onFirstUserVisible() {

        val imageListAdapter = ImageListAdapter()
        imageListAdapter.setOnItemClickListener { adapter, view, position ->
            val iv = view as ImageView
            //load thumb complete
            if (iv.drawable !is ColorDrawable) {
                mPresenter.openImageDetail(iv, (adapter.getItem(position) as ImageItem), position)
            }
        }

        mBinding.mRefreshLayout
                .setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
                .setItemDecoration(GridItemDecoration(2, SizeUtils.dp2px(5f), false))
                .setLoadSize(Api.pageSize)
                .setPageStartOffset(1)
                .setViewType(RefreshCustomerLayout.Refresh_LoadMore)
                .setViewStateListener(
                        { showEmpty() },
                        { showContent() },
                        { showLoading() },
                        { showMessage(it) },
                        { error, content -> showMessageFromNet(error, content) })
                .setRefreshListener(
                        { mPresenter.getImageList(mBinding.mRefreshLayout.pageStartOffset, false) },
                        { _, targetPage -> mPresenter.getImageList(targetPage, true) })
                .setAdapter(imageListAdapter)


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

    override fun isRegisterStateView(): Boolean = true

}