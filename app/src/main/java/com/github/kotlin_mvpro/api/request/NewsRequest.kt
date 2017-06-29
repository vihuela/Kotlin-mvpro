package com.github.kotlin_mvpro.api.request

class NewsRequest {

    data class ListRes(val date: String, val stories: List<StoriesBean>, val top_stories: List<TopStoriesBean>) {


        data class StoriesBean(val ga_prefix: String,
                               val id: Int,
                               val title: String,
                               val type: Int,
                               val multipic: Boolean,
                               val images: List<String>)

        data class TopStoriesBean(val ga_prefix: String,
                                  val id: Int,
                                  val image: String,
                                  val title: String,
                                  val type: Int)
    }

    data class DetailRes(val body: String,
                         val image_source: String,
                         val title: String,
                         val image: String,
                         val share_url: String,
                         val js: ArrayList<String>,
                         val ga_prefix: String,
                         val images: ArrayList<String>,
                         val type: Int,
                         val id: Int,
                         val css: ArrayList<String>)
}