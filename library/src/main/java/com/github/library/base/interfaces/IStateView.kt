package com.github.library.base.interfaces

interface IStateView {
    fun showLoading() = Unit

    fun hideLoading() = Unit

    fun showNetError(error: Any, content: String = "") = Unit

    fun showEmpty() = Unit

    fun showContent() = Unit

    fun showMessage(content: String) = Unit
}
