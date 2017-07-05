/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.kotlin_mvpro.model.UserInfo
import com.ricky.kotlin_mvpro.base.BasePresenter
import com.ricky.kotlin_mvpro.base.interfaces.IView

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
