package com.github.refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * 描述:黏性下拉刷新风格、类似手机消息列表下拉刷新风格
 */
public class StickinessRefreshViewHolder extends RefreshViewHolder {
    private StickinessRefreshView mStickinessRefreshView;

    private int mRotateImageResId = -1;
    private int mStickinessColorResId = -1;

    public StickinessRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_stickiness, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }

            mStickinessRefreshView = (StickinessRefreshView) mRefreshHeaderView.findViewById(R.id.stickinessRefreshView);
            mStickinessRefreshView.setStickinessRefreshViewHolder(this);
            if (mRotateImageResId != -1) {
                mStickinessRefreshView.setRotateImage(mRotateImageResId);
            } else {
                throw new RuntimeException("请调用" + StickinessRefreshViewHolder.class.getSimpleName() + "的setRotateImage方法设置旋转图片资源");
            }
            if (mStickinessColorResId != -1) {
                mStickinessRefreshView.setStickinessColor(mStickinessColorResId);
            } else {
                throw new RuntimeException("请调用" + StickinessRefreshViewHolder.class.getSimpleName() + "的setStickinessColor方法设置黏性颜色资源");
            }
        }
        return mRefreshHeaderView;
    }

    /**
     * 设置旋转图片资源
     *
     * @param resId
     */
    public void setRotateImage(@DrawableRes int resId) {
        mRotateImageResId = resId;
    }

    /**
     * 设置黏性颜色资源
     *
     * @param resId
     */
    public void setStickinessColor(@ColorRes int resId) {
        mStickinessColorResId = resId;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        mStickinessRefreshView.setMoveYDistance(moveYDistance);
    }

    @Override
    public void changeToIdle() {
        mStickinessRefreshView.smoothToIdle();
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }

    @Override
    public void changeToRefreshing() {
        mStickinessRefreshView.startRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mStickinessRefreshView.stopRefresh();
    }

    @Override
    public boolean canChangeToRefreshingStatus() {
        return mStickinessRefreshView.canChangeToRefreshing();
    }

}