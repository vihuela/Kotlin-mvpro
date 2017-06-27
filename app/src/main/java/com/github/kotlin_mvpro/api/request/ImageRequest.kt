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