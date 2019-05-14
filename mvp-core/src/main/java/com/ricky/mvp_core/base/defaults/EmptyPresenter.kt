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

package com.ricky.mvp_core.base.defaults

import android.os.Bundle
import com.ricky.mvp_core.base.BasePresenter
import com.ricky.mvp_core.base.interfaces.IView

class EmptyPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }
}