package com.github.kotlin_mvpro.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.kotlin_mvpro.ui.presenter.ImageFragmentPresenter

class MainPresenterFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val t = ImageFragmentPresenter.getInstance() as T
        return t
    }
}