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

package com.github.kotlin_mvpro.ui.adapter

import com.github.kotlin_mvpro.BR
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.request.NewsRequest
import com.github.kotlin_mvpro.model.ImageItem

class NewsListAdapter : CommonListAdapter<NewsRequest.ListRes.StoriesBean>(R.layout.item_nomal_story) {
    override fun convert(helper: BindHolder, item: NewsRequest.ListRes.StoriesBean?) {
        val binding = helper.getBinding()

        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}