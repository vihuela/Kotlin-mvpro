package com.github.library.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.library.base.interfaces.IView
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Build Presenter with Fragment
 * Fragment has a life cycle
 */
abstract class BasePresenter<V : IView> : RxFragment() {
    protected var mView: V? = null
    override fun getContext(): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) super.getContext() else activity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = mView ?: return null
        onViewCreated(view, arguments, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(@NonNull view: V, arguments: Bundle?, savedInstanceState: Bundle?)

    //abandon
    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        mView = null
        super.onDestroyView()
    }

    fun view(): V? = mView
    fun setView(view: Any) {
        this.mView = view as V
    }

}
