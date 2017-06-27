package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.kotlin_mvpro.model.UserInfo
import com.github.library.base.BasePresenter
import com.github.library.base.interfaces.IView

class MainActivityPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
        val userInfo = savedInstanceState?.getParcelable<UserInfo>("person")
        print(userInfo ?: return)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val user = UserInfo("ricky", "vihuela")
        outState?.putParcelable("person", user)
        super.onSaveInstanceState(outState)
    }

}
