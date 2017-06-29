package com.github.kotlin_mvpro.ui.adapter

import com.github.kotlin_mvpro.BR
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.model.ImageItem

class NewsListAdapter : CommonListAdapter<ImageItem>(R.layout.item_nomal_story) {
    override fun convert(helper: BindHolder, item: ImageItem?) {
        val binding = helper.getBinding()

        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}