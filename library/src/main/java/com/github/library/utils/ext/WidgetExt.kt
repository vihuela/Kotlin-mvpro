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

package com.github.library.utils.ext

import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.interfaces.IRefreshStateView

//RefreshCustomerLayout
fun RefreshCustomerLayout.setViewStateListener(showEmpty: () -> Unit,
                                               showContent: () -> Unit,
                                               showLoading: () -> Unit,
                                               showMessage: (message: String) -> Unit,
                                               showMessageFromNet: (error: Any, content: String) -> Unit): RefreshCustomerLayout =
        this.setViewStateListener(object : IRefreshStateView {
            override fun showMessageFromNet(error: Any, content: String) {
                showMessageFromNet.invoke(error, content)
            }

            override fun showLoading() {
                showLoading.invoke()
            }

            override fun showContent() {
                showContent.invoke()
            }

            override fun showEmpty() {
                showEmpty.invoke()
            }

            override fun showMessage(content: String) {
                showMessage.invoke(content)
            }
        })


fun RefreshCustomerLayout.setRefreshListener(onRefresh: (rcl: RefreshCustomerLayout) -> Unit,
                                             onLoadMore: (rcl: RefreshCustomerLayout, targetPage: Int) -> Unit): RefreshCustomerLayout =
        setRefreshListener(object : RefreshCustomerLayout.IRefreshListener {
            override fun onLoadMore(rcl: RefreshCustomerLayout, targetPage: Int) {
                onLoadMore.invoke(rcl, targetPage)
            }

            override fun onRefresh(rcl: RefreshCustomerLayout) {
                onRefresh.invoke(rcl)
            }
        })



