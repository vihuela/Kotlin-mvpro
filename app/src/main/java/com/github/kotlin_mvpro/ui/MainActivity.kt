package com.github.kotlin_mvpro.ui

import android.app.Fragment
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.view.WindowManager
import com.blankj.utilcode.util.SnackbarUtils
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.databinding.ActivityMainBinding
import com.github.kotlin_mvpro.ui.base.BaseActivity
import com.github.kotlin_mvpro.ui.fragment.ImageFragment
import com.github.kotlin_mvpro.utils.Cons
import com.github.library.base.defaults.EmptyPresenter
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<EmptyPresenter, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //rxNetState
        ReactiveNetwork.observeNetworkConnectivity(applicationContext)
                .subscribeOn(Schedulers.io())
                .bindToLifecycle(this)
                .flatMap { Observable.just((it.isAvailable && it.state == NetworkInfo.State.CONNECTED)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(!it){
                        SnackbarUtils.with(findViewById(android.R.id.content))
                                .setMessage("网络未连接")
                                .setDuration(0)
                                .showWarning()
                    }
                    Paper.book().write(Cons.NET_STATE, it)
                })

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
