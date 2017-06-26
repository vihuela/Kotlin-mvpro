package com.github.library.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.app.Fragment
import com.github.library.base.interfaces.IView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : RxAppCompatActivity(), IView {

    protected lateinit var mBinding: B
    protected var mPresenter: T? = null


    override fun getContext(): Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = createViewDataBinding()
        mPresenter = createPresenter()
    }


    private fun createViewDataBinding(): B = DataBindingUtil.setContentView(this, getLayoutId())

    @Suppress("UNCHECKED_CAST")
    private fun createPresenter(): T? {
        //not use fragmentManager under support.v4，cause the bug of findFragmentByTag()
        val trans = fragmentManager.beginTransaction()
        val presenterClass = try {
            (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        } catch (e: Exception) {
            print("${this.javaClass.simpleName} presenter is null")
            null
        }
        if (presenterClass != null) {
            val args = if (intent.extras != null) Bundle(intent.extras) else Bundle()
            var fragment = fragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
            if (fragment == null || fragment.isDetached) {
                fragment = Fragment.instantiate(this, presenterClass.canonicalName, args) as T

                trans.add(0, fragment, presenterClass.canonicalName)
            }
            fragment.setView(this)
            trans.commit()
            return fragment
        } else {
            return null
        }
    }

    abstract fun getLayoutId(): Int

}
