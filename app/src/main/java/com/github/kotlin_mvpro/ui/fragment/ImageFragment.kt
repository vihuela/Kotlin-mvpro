package com.github.kotlin_mvpro.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.kotlin_mvpro.R
import com.github.kotlin_mvpro.api.Api
import com.github.kotlin_mvpro.databinding.FragmentImageBinding
import com.github.kotlin_mvpro.model.ImageItem
import com.github.kotlin_mvpro.ui.base.BaseFragment
import com.github.kotlin_mvpro.ui.presenter.ImageFragmentPresenter
import com.github.kotlin_mvpro.ui.view.IImageFragment
import com.github.kotlin_mvpro.ui.widget.GridItemDecoration
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.RefreshLayout
import com.github.refresh.interfaces.IRefreshStateView
import github.library.utils.Error

class ImageFragment : BaseFragment<ImageFragmentPresenter, FragmentImageBinding>(), IImageFragment {
    override fun onFirstUserVisible() {

        val adapter: BaseQuickAdapter<ImageItem, BaseViewHolder?> = object : BaseQuickAdapter<ImageItem, BaseViewHolder?>(R.layout.image_item) {
            override fun convert(helper: BaseViewHolder?, item: ImageItem) {
                val iv = helper?.getView<ImageView>(R.id.iv)
                Glide.with(context).load(item.url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
                        .crossFade()
                        .into(iv)
            }
        }
        mBinding.mRefreshLayout.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mBinding.mRefreshLayout.recyclerView.addItemDecoration(GridItemDecoration(2, SizeUtils.dp2px(5f), false))
        mBinding.mRefreshLayout.setPageSize(Api.pageSize)
                .setPageStartOffset(1)
                .setViewType(RefreshCustomerLayout.Refresh_LoadMore)
                .setRefreshListener(object : RefreshCustomerLayout.IRefreshListener {
                    override fun onLoadMore(targetPage: Int) {
                        mPresenter?.getImageList(targetPage, true)
                    }

                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        mPresenter?.getImageList(mBinding.mRefreshLayout.pageStartOffset, false)
                    }
                })
                .setStateListener(object : IRefreshStateView {
                    override fun showMessage(content: String) {
                        this@ImageFragment.showMessage(content)
                    }

                    override fun showEmpty() {
                        this@ImageFragment.showEmpty()
                    }

                    override fun showError(error: Any, content: String) {
                        this@ImageFragment.showNetError(error, content)
                    }

                    override fun showContent() {
                        this@ImageFragment.showContent()
                    }
                }).setAdapter(adapter)

        /*mBinding.mRefreshLayout.setTotalPage(20)*/
        mBinding.mRefreshLayout.startRequest()

    }

    override fun setData(beanList: List<*>, loadMore: Boolean) {
        mBinding.mRefreshLayout.setData(beanList, loadMore)
    }

    override fun setMessage(error: Error, content: String) {
        this@ImageFragment.showNetError(error, content)
        mBinding.mRefreshLayout.stopRequest()
    }

    override fun showLoading() {
        if (mBinding.mRefreshLayout.isEmpty)
            super<BaseFragment>.showLoading()
    }

    override fun showNetError(error: Any, content: String) {
        if (mBinding.mRefreshLayout.isEmpty)
            super<BaseFragment>.showNetError(error, content)
    }

    override fun getLayoutId(): Int = R.layout.fragment_image

    override fun onRetryListener() {
        mBinding.mRefreshLayout.startRequest()
    }
}