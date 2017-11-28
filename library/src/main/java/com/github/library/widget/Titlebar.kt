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
package com.github.library.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.github.library.R
import com.github.library.widget.java.popwindows.EasyPopup
import com.github.library.widget.java.popwindows.HorizontalGravity
import com.github.library.widget.java.popwindows.VerticalGravity


class Titlebar(context: Context, attr: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attr, defStyleAttr), View.OnClickListener {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)


    private lateinit var mTitleText: TextView
    private lateinit var mLeftText: TextView
    private lateinit var mRightText: TextView
    private var mContainView = LayoutInflater.from(context).inflate(R.layout.tb_layout_title, this)
    private var mLeftClick: (v: View) -> Unit = {}
    private var mRightClick: (v: View) -> Unit = {}
    private var mRightMenuPop: EasyPopup? = null
    private var mTitle: String? = null
    private var mRightIconDrawable: Drawable? = null

    init {
        val ta = context.obtainStyledAttributes(attr, R.styleable.Titlebar)
        mTitle = ta.getString(R.styleable.Titlebar_title)
        mRightIconDrawable = ta.getDrawable(R.styleable.Titlebar_rightIcon)
        ta.recycle()

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = getDp(2f)
        }
        mLeftText = (findViewById<TextView>(R.id.tv_title_left) as TextView).apply { setOnClickListener(this@Titlebar) }
        mRightText = (findViewById<TextView>(R.id.tv_title_right) as TextView).apply {
            setOnClickListener(this@Titlebar)
            setCompoundDrawablesWithIntrinsicBounds(null, null, mRightIconDrawable ?: return@apply, null)
        }
        mTitleText = (findViewById<TextView>(R.id.tv_title_center) as TextView).apply { text = mTitle ?: return@apply }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_title_left -> mLeftClick.invoke(v)
            R.id.tv_title_right -> mRightClick.invoke(v)
        }
    }

    /**
     * 设置中间标题
     */
    fun setTitle(text: String): Titlebar {
        mTitleText.text = text
        return this
    }

    /**
     * 设置左边箭头点击事件
     */
    fun setLeftClick(left: (v: View) -> Unit): Titlebar {
        mLeftClick = left
        return this

    }

    /**
     * 设置左边小点点击事件
     */
    fun setRightClick(right: (v: View) -> Unit): Titlebar {
        mRightClick = right
        return this

    }

    /**
     * 设置左边箭头是否显示
     */
    fun setLeftShow(isShow: Boolean): Titlebar {
        if (isShow) {
            mLeftText.visibility = View.VISIBLE
        } else {
            mLeftText.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置右边菜单是否显示
     */
    fun setRightShow(isShow: Boolean): Titlebar {
        if (isShow) {
            mRightText.visibility = View.VISIBLE
        } else {
            mRightText.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置右边点击
     */
    @Suppress("DEPRECATION")
    fun setRightClickWithMenu(vararg menus: RightMenuItem): Titlebar {
        //构造tb_layout_right_pop.xml这种布局
        val contentView = LinearLayout(context).apply {
            //init contain
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            background = resources.getDrawable(R.drawable.tb_right_pop_bg)
            dividerDrawable = resources.getDrawable(R.drawable.tb_divider_horizontal_bg)
            showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            gravity = Gravity.CENTER_HORIZONTAL
            orientation = LinearLayout.VERTICAL
            //add child
            menus.forEach {
                val menuItemView = TextView(context).apply {
                    val menuItem = it
                    gravity = Gravity.CENTER
                    text = menuItem.txt
//                    textSize = getSp(12f)
                    setOnClickListener { menuItem.txtClick.invoke(it) }
                }
                val menuItemViewLp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDp(35f).toInt()).apply {
                    leftMargin = getDp(50f).toInt()
                    rightMargin = getDp(50f).toInt()
                }
                addView(menuItemView, menuItemViewLp)
            }
        }

        mRightMenuPop = EasyPopup(context)
                .setContentView<EasyPopup>(contentView)
                .setAnimationStyle<EasyPopup>(R.style.QQPopAnim)
                .setFocusAndOutsideEnable<EasyPopup>(true)
//                .setBackgroundDimEnable<EasyPopup>(true)
//                .setDimValue<EasyPopup>(0.2f)
                .createPopup<EasyPopup>()

        mRightClick = {
            mRightMenuPop?.showAtAnchorView(it, VerticalGravity.BELOW, HorizontalGravity.LEFT, getDp(35f).toInt(), 0)
        }
        return this
    }

    private fun getDp(vals: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, vals, resources.displayMetrics)
    private fun getSp(vals: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, vals, resources.displayMetrics)


    fun getLeftView(): TextView {
        return mLeftText
    }

    fun getRightView(): TextView {
        return mRightText
    }

    fun getContentView(): View {
        return mContainView
    }

    fun getRightMenuPop(): EasyPopup? {
        return mRightMenuPop
    }

}

class RightMenuItem(txt: String, txtClickListener: (v: View) -> Unit) {
    val txt: String? = txt
    val txtClick: (v: View) -> Unit = txtClickListener
}

interface ITitlebar {
    fun isInitTitlebar() = false
}