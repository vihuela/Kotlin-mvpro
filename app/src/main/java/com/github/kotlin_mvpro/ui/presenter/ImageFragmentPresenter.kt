package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.api.ApiCacheProvider
import com.github.kotlin_mvpro.model.ImageItem
import com.github.kotlin_mvpro.ui.view.IImageFragment
import com.github.kotlin_mvpro.utils.Cons
import com.github.library.base.BasePresenter
import com.github.library.utils.defThread
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import github.library.parser.ExceptionParseMgr
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey

class ImageFragmentPresenter : BasePresenter<IImageFragment>() {


    override fun onViewCreated(view: IImageFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getImageList(page: Int = 1, loadMore: Boolean, resetCache: Boolean = true) {
        val api = Api.IMPL.getImageList(Api.pageSize, page)
        ApiCacheProvider.IMPL.getImageList(api, DynamicKey(page), EvictDynamicKey(Paper.book().read(Cons.NET_STATE, resetCache)))
                .defThread()
                .bindToLifecycle(this)
                .doOnSubscribe { view()!!.showLoading() }
                .observeOn(Schedulers.io())
                .flatMap {
                    val data_ok = !it.data.error && it.data.results.isNotEmpty()
                    if (data_ok) Observable.just(it) else Observable.error(IllegalArgumentException("server business error"))
                }
                .map { it.data.results.map { ImageItem(it.url) } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view()!!.setData(it, loadMore) },
                        {
                            ExceptionParseMgr.Instance.parseException(it, { error, message -> view()!!.setMessage(error, message) })
                        }, { view()!!.hideLoading() })
    }
}