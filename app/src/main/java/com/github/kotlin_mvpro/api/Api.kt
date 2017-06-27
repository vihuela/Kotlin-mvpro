package com.github.kotlin_mvpro.api

import com.github.kotlin_mvpro.api.request.ImageRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    companion object {
        val IMPL: Api = ApiUtils.api
        val pageSize = 10
    }

    @GET("福利/{size}/{page}")
    fun getImageList(@Path("size") size: Int, @Path("page") page: Int): Observable<ImageRequest.Res>
}