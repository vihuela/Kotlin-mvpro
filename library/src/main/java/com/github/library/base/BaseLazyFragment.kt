package com.github.library.base

import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.components.RxFragment

abstract class BaseLazyFragment : RxFragment() {

    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true
    private var isPrepared = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPrepare()
    }
    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return@onResume
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    //首次可见
    protected abstract fun onFirstUserVisible()

    //每次可见
    protected fun onUserVisible() {}

    //首次不可见
    private fun onFirstUserInvisible() {}

    //每次不可见
    protected fun onUserInvisible() {}

    @Synchronized private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }
}
