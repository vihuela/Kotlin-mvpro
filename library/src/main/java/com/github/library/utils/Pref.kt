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

package com.github.library.utils

import io.paperdb.Paper
import kotlin.reflect.KProperty

//apply for Paper，use delegate properties
class Pref<T>(val key: String?, val default: T) {

    operator fun getValue(any: Any, property: KProperty<*>): T {
        return Paper.book().read(key ?: property.name, default)
    }

    operator fun setValue(any: Any, property: KProperty<*>, value: T) {
        Paper.book().write(key ?: property.name, value)
    }
}

inline fun <reified AnyClz, T> AnyClz.pref(default: T, key: String? = null) = Pref(key, default)