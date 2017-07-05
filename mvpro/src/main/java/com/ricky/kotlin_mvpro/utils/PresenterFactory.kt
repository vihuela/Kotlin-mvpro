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

package com.ricky.kotlin_mvpro.utils

import android.app.Fragment
import android.os.Bundle
import com.ricky.kotlin_mvpro.base.BaseBindingActivity
import com.ricky.kotlin_mvpro.base.BaseBindingFragment
import com.ricky.kotlin_mvpro.base.BasePresenter
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
internal object PresenterFactory {


    fun <T : BasePresenter<*>> createPresenter(aty: BaseBindingActivity<*, *>): T {
        //not use fragmentManager under support.v4，cause the bug of findFragmentByTag()
        val trans = aty.fragmentManager.beginTransaction()
        val presenterClass = try {
            (aty::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        } catch (e: Exception) {
            throw IllegalArgumentException("${aty.javaClass.simpleName} create presenter ERROR，" +
                    "try EmptyPresenter If there is no presenter")
        }
        val args = if (aty.intent.extras != null) Bundle(aty.intent.extras) else Bundle()
        var presenter = aty.fragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
        if (presenter == null || presenter.isDetached) {
            presenter = Fragment.instantiate(aty, presenterClass.canonicalName, args) as T
            trans.add(0, presenter, presenterClass.canonicalName)
        }
        presenter.setView(aty)
        trans.commit()
        return presenter
    }

    fun <T : BasePresenter<*>> createPresenter(frg: BaseBindingFragment<*, *>): T {
        //not use fragmentManager under support.v4，cause the bug of findFragmentByTag()
        val trans = frg.fragmentManager.beginTransaction()
        val presenterClass = try {
            (frg::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        } catch (e: Exception) {
            throw IllegalArgumentException("${frg.javaClass.simpleName} create presenter ERROR，" +
                    "try EmptyPresenter If there is no presenter")
        }
        val args = if (frg.arguments != null) Bundle(frg.arguments) else Bundle()
        var presenter = frg.fragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
        if (presenter == null || presenter.isDetached) {
            presenter = Fragment.instantiate(frg.context, presenterClass.canonicalName, args) as T
            trans.add(0, presenter, presenterClass.canonicalName)
        }
        presenter.setView(frg)
        trans.commit()
        return presenter
    }
}