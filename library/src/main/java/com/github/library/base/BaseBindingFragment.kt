package com.github.library.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.app.Fragment
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.library.base.interfaces.IView
import com.trello.rxlifecycle2.components.RxFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class BaseBindingFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseLazyFragment(), IView {

    protected lateinit var mBinding: B
    protected var mPresenter: T? = null


    override fun getContext(): Context = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) super.getContext() else activity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        mPresenter = createPresenter()
        return mBinding.root
    }

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
            val args = if (arguments != null) Bundle(arguments) else Bundle()
            var fragment = fragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
            if (fragment == null || fragment.isDetached) {
                fragment = Fragment.instantiate(context, presenterClass.canonicalName, args) as T

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
