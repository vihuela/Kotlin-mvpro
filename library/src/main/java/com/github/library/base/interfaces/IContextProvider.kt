package com.github.library.base.interfaces

import android.content.Context

interface IContextProvider {

    fun getContext(): Context?
}