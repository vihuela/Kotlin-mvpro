package com.github.kotlin_mvpro.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils

class CustomerImageView(context: Context, attrs: AttributeSet?) : ImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wrapperHeightSpec = View.MeasureSpec.makeMeasureSpec((ScreenUtils.getScreenHeight() /2) , View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, wrapperHeightSpec)

    }
}