package com.github.kotlin_mvpro.ui.presenter

import android.os.Bundle
import com.blankj.utilcode.util.TimeUtils
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.api.ApiCacheProvider
import com.github.kotlin_mvpro.ui.view.INewsFragment
import com.github.kotlin_mvpro.utils.Cons
import com.github.library.base.BasePresenter
import com.github.library.utils.defThread
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import github.library.parser.ExceptionParseMgr
import io.paperdb.Paper
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.EvictProvider
import java.text.SimpleDateFormat
import java.util.*

class NewsFragmentPresenter : BasePresenter<INewsFragment>() {
    override fun onViewCreated(view: INewsFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getNewsList(resetCache: Boolean = true) {
        val api = Api.IMPL.getNewsList()
        ApiCacheProvider.IMPL.getNewsList(api, EvictProvider(Paper.book().read(Cons.NET_STATE, resetCache)))
                .defThread()
                .bindToLifecycle(this)
                .doOnSubscribe { view()!!.showLoading() }
                .map { it.data.stories }
                .subscribe({
                    view()!!.setData(it, false)
                },
                        { ExceptionParseMgr.Instance.parseException(it, { error, message -> view()!!.setMessage(error, message) }) },
                        { view()!!.hideLoading() })
    }

    fun getNewsListForDate(page: Int = 1,  resetCache: Boolean = true) {
        val dateString = getNextDay(-1 * page)
        val api = Api.IMPL.getNewsListForDate(dateString)
        ApiCacheProvider.IMPL.getNewsListForDate(api, DynamicKey(page), EvictDynamicKey(Paper.book().read(Cons.NET_STATE, resetCache)) )
                .defThread()
                .bindToLifecycle(this)
                .doOnSubscribe { view()!!.showLoading() }
                .map { it.data.stories }
                .subscribe({
                    view()!!.setData(it, true)
                },
                        {
                            ExceptionParseMgr.Instance.parseException(it, { error, message -> view()!!.setMessage(error, message) })
                        }, { view()!!.hideLoading() })
    }

    private fun getNextDay(delay: Int): String {
        try {
            val formatter = "yyyyMMdd"
            val format = SimpleDateFormat(formatter)
            var mdate = ""
            val d = TimeUtils.string2Date(TimeUtils.date2String(Date(), format), format)
            val myTime = d.time / 1000 + delay * 24 * 60 * 60
            d.time = myTime * 1000
            mdate = format.format(d)
            return mdate
        } catch (e: Exception) {
            return ""
        }

    }
}