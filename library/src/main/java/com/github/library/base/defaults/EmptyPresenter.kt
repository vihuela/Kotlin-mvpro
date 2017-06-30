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

package com.github.library.base.defaults

import android.os.Bundle
import com.github.library.base.BasePresenter
import com.github.library.base.interfaces.IView

class EmptyPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }
}