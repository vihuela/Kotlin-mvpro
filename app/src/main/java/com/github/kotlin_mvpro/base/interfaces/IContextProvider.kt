package com.github.kotlin_mvpro.base.interfaces

import android.content.Context

interface IContextProvider {

    fun getContext(): Context?
}