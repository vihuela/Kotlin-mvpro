package com.github.kotlin_mvpro.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.databinding.ActivityMainBinding
import com.github.kotlin_mvpro.ui.base.BaseActivity
import com.github.kotlin_mvpro.ui.fragment.ImageFragment
import com.github.library.base.defaults.EmptyPresenter

class MainActivity : BaseActivity<EmptyPresenter, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = arrayOf("美图")
        val items = arrayOf(ImageFragment())
        mBinding.pager.adapter = object : FragmentPagerAdapter(fragmentManager) {

            override fun getItem(position: Int): Fragment = items[position]
            override fun getCount(): Int = title.size
            override fun getPageTitle(position: Int): CharSequence = title[position]
        }
        mBinding.tab.setViewPager(mBinding.pager, title)
    }
}
