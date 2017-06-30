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

package com.github.kotlin_mvpro.api.request

class ImageRequest {

    data class Res(val error: Boolean, val results: List<Item>) {


        data class Item(val _id: String,
                        val createdAt: String,
                        val desc: String,
                        val publishedAt: String,
                        val source: String,
                        val type: String,
                        val url: String,
                        val used: Boolean,
                        val who: String)
    }
}