package com.github.library.base.interfaces

interface IStateView {
    fun showLoading() = Unit

    fun hideLoading() = Unit

    fun showEmpty() = Unit

    fun showContent() = Unit

    fun showMessage(content: String) = Unit

    fun showMessageFromNet(error: Any, content: String) = Unit

}
