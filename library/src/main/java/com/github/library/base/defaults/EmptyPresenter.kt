package com.github.library.base.defaults

import android.os.Bundle
import com.github.library.base.BasePresenter
import com.github.library.base.interfaces.IView

class EmptyPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }
}