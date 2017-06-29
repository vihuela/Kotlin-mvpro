package com.github.kotlin_mvpro.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.github.library.base.BaseBindingActivity
import com.github.library.base.BasePresenter

abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
    }

    override fun showEmpty() {
        super.showEmpty()
    }

    override fun showContent() {
        super.showContent()
    }

    override fun showMessage(content: String) {
        super.showMessage(content)
    }

    override fun hideLoading() {
        super.hideLoading()
    }
}
