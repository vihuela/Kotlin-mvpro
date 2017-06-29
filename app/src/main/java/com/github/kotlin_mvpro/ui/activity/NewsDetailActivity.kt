package com.github.kotlin_mvpro.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import com.github.kotlin_mvpro.BR
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.request.NewsRequest
import com.github.kotlin_mvpro.databinding.ActivityNewsDetailBinding
import com.github.kotlin_mvpro.ui.base.BaseActivity
import com.github.kotlin_mvpro.ui.presenter.NewsDetailActivityPresenter
import com.github.kotlin_mvpro.ui.view.INewsDetailActivity
import com.github.kotlin_mvpro.utils.RouterImpl
import com.github.mzule.activityrouter.annotation.Router
import com.just.library.AgentWeb


@Router(value = RouterImpl.NewsDetailTo)
class NewsDetailActivity : BaseActivity<NewsDetailActivityPresenter, ActivityNewsDetailBinding>(), INewsDetailActivity {
    override fun setData(res: NewsRequest.DetailRes) {

    }

    lateinit var mAgentWeb: AgentWeb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.context, this)
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mBinding.contain, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback({ view, title -> mBinding.toolbar.title = title })
                .createAgentWeb()
                .ready()
                .go("")
        mPresenter?.onLoadCallback = { title, data ->
            run {
                mBinding.toolbar.title = title
                mAgentWeb.loader.loadUrl(data)
            }
        }
        mPresenter?.getNewsDetail(intent.getStringExtra("id").toInt())

    }

    override fun getLayoutId(): Int = R.layout.activity_news_detail

    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle.onDestroy()
    }
}