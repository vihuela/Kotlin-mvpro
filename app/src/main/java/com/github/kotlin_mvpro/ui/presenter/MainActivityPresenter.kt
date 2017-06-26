package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.kotlin_mvpro.model.UserInfo
import com.github.kotlin_mvpro.ui.view.IMainActivity
import com.github.library.base.BasePresenter

class MainActivityPresenter : BasePresenter<IMainActivity>() {
    override fun onViewCreated(view: IMainActivity, arguments: Bundle?, savedInstanceState: Bundle?) {
        val tv = arguments?.getString("abc") ?: "empty"
        view.setText(tv)
        val userInfo = savedInstanceState?.getParcelable<UserInfo>("person")
        print(userInfo ?: return)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val user = UserInfo("ricky", "vihuela")
        outState?.putParcelable("person", user)
        super.onSaveInstanceState(outState)
    }

}
