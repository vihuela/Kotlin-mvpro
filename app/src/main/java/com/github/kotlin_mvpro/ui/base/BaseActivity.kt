package com.github.kotlin_mvpro.ui.base

import android.databinding.ViewDataBinding
import com.github.library.base.BaseBindingActivity
import com.github.library.base.BasePresenter

abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>() {
    override fun showLoading() {
        super.showLoading()
    }

    override fun showNetError(error: Any, content: String) {
        super.showNetError(error, content)
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
