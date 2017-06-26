package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.library.base.BasePresenter
import com.github.kotlin_mvpro.ui.view.IMainActivity

class MainActivityPresenter : BasePresenter<IMainActivity>() {
    override fun onViewCreated(view: IMainActivity, arguments: Bundle?, savedInstanceState: Bundle?) {
        val tv = arguments?.getString("abc") ?: "empty"
        view.setText(tv)
    }


}
