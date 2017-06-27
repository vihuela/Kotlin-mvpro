package com.github.refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 描述:慕课网下拉刷新风格
 */
public class MoocStyleRefreshViewHolder extends RefreshViewHolder {
    private MoocStyleRefreshView mMoocRefreshView;
    private int mUltimateColorResId = -1;
    private int mOriginalImageResId = -1;

    public MoocStyleRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_mooc_style, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }

            mMoocRefreshView = (MoocStyleRefreshView) mRefreshHeaderView.findViewById(R.id.moocView);
            if (mOriginalImageResId != -1) {
                mMoocRefreshView.setOriginalImage(mOriginalImageResId);
            } else {
                throw new RuntimeException("请调用" + MoocStyleRefreshViewHolder.class.getSimpleName() + "的setOriginalImage方法设置原始图片资源");
            }
            if (mUltimateColorResId != -1) {
                mMoocRefreshView.setUltimateColor(mUltimateColorResId);
            } else {
                throw new RuntimeException("请调用" + MoocStyleRefreshViewHolder.class.getSimpleName() + "的setUltimateColor方法设置最终生成图片的填充颜色资源");
            }
        }
        return mRefreshHeaderView;
    }

    /**
     * 设置原始的图片资源
     *
     * @param resId
     */
    public void setOriginalImage(@DrawableRes int resId) {
        mOriginalImageResId = resId;
    }

    /**
     * 设置最终生成图片的填充颜色资源
     *
     * @param resId
     */
    public void setUltimateColor(@ColorRes int resId) {
        mUltimateColorResId = resId;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        scale = 0.6f + 0.4f * scale;
        ViewCompat.setScaleX(mMoocRefreshView, scale);
        ViewCompat.setScaleY(mMoocRefreshView, scale);
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }

    @Override
    public void changeToRefreshing() {
        mMoocRefreshView.startRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mMoocRefreshView.stopRefreshing();
    }

}