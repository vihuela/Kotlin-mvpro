package com.github.refresh;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CheckResult;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.refresh.interfaces.IRefreshDataView;
import com.github.refresh.interfaces.IRefreshStateView;
import com.github.refresh.util.CustomLoadMoreView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


/**
 * 通过接口联动View与ViewModel
 * <pre>
 *     RefreshListView
                 .setBaseView(this)
                 .setRestoreView(getViewModel())
                 .setPageStartOffset(1)
                 .setPageSize(Api.LIST_SIZE)
                 .setViewType(RefreshListView.Refresh_LoadMore)
                 .setListener(new RefreshListView.IRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {

                }

                @Override
                public void onLoadMore(int targetPage) {

                }
                })
                 .setAdapter(mAdapter);
 * </pre>
 */
public class RefreshCustomerLayout<T> extends FrameLayout implements IRefreshDataView {
    public final static int DEFAULT_SIZE = 10;
    public final static int Refresh = 0;
    public final static int LoadMore = 1;
    public final static int Refresh_LoadMore = 2;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private IRefreshListener mIRefreshListener;
    private Context mContext;
    private BaseQuickAdapter mAdapter;
    private boolean isLoadMore;//是否允许加载更多
    private int size = DEFAULT_SIZE;//每次加载条目
    private int totalPage = -1;//加载总页数
    private int pageStartOffset = 0;//起始页
    private int currentPage = pageStartOffset;//当前加载页
    private IRefreshStateView mIRefreshStateView;//与外部对接View的切换
    private IRestoreInstance mRestoreView;
    private int mRefreshType = Refresh;
    private boolean isInit;//已经请求过数据
    //onSave
    private int mOnSaveInstance_totalPage;
    private boolean mOnSaveInstance_loadMore;
    private Object mOnSaveInstance_error;
    private String mOnSaveInstance_content;

    public RefreshCustomerLayout(Context context) {
        this(context, null);
    }

    public RefreshCustomerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View content = View.inflate(context, R.layout.view_refresh_layout, null);
        addView(content);

        mRefreshLayout = (RefreshLayout) content.findViewById(R.id.mRefreshLayout);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.mRecycleView);

        mContext = context;

        initAttr(context, attrs);


    }

    private void initLogic() {
        switch (mRefreshType) {
            case Refresh:
                requireRefresh();
                break;
            case LoadMore:
                requireLoadMore();
                break;
            case Refresh_LoadMore:
                requireRefresh();
                requireLoadMore();
                break;
        }
    }

    private void requireLoadMore() {
        if (mAdapter != null) {
            isLoadMore = true;
            mAdapter.setLoadMoreView(new CustomLoadMoreView());//customer loadView
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (mIRefreshListener != null
                            && mRefreshLayout.getCurrentRefreshStatus() == RefreshLayout.RefreshStatus.IDLE) {
                        if (isLoadMore) {
                            mIRefreshListener.onLoadMore(currentPage + 1);
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                }
            }, mRecyclerView);
        }

    }

    private void requireRefresh() {
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mContext, false));
        mRefreshLayout.setDelegate(new RefreshLayout.RefreshLayoutDelegate() {
            @Override
            public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
                if (mIRefreshListener != null) {
                    mIRefreshListener.onRefresh(refreshLayout);
                }
            }

            @Override
            public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
                return false;
            }
        });
    }


    private void initAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.refreshLayout);
        //type

        mRefreshType = ta.getInt(R.styleable.refreshLayout_rl_refresh_type, Refresh);
        //...
        ta.recycle();
    }

    private void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    /**
     * --------------------------------------Setter----------------------------------------------------
     */

    /**
     * 起始加载偏移页
     */
    public RefreshCustomerLayout setPageStartOffset(int start) {
        this.pageStartOffset = start;
        return this;
    }

    /**
     * 刷新View的类型
     * @attr ref R.styleable#refreshLayout_rl_refresh_type
     */
    public RefreshCustomerLayout setViewType(@Type int type) {
        this.mRefreshType = type;
        return this;
    }

    /**
     * 设置回调
     */
    public RefreshCustomerLayout setRefreshListener(IRefreshListener mIRefreshListener) {
        this.mIRefreshListener = mIRefreshListener;
        return this;
    }

    /**
     * 每次加载条目数，与保持接口一致!用于在未传totalPage之后判断是否加载更多
     */
    public RefreshCustomerLayout setPageSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * 联动视图接口,调用视图的一些设置View状态的方法
     */
    public RefreshCustomerLayout setStateListener(IRefreshStateView IRefreshStateView){
        this.mIRefreshStateView = IRefreshStateView;
        return this;
    }

    /**
     * 联动【中间类】保存数据
     */
    public RefreshCustomerLayout setRestoreListener(IRestoreInstance restoreView){
        this.mRestoreView = restoreView;
        return this;
    }

    /**
     * 设置适配器
     */
    public void setAdapter(BaseQuickAdapter mAdapter) {
        this.mAdapter = mAdapter;
        initLogic();
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 加载总页数，通常由接口获取，若没有可传0（请求发送后）
     */
    @Override
    public void setTotalPage(int totalPage) {
        mOnSaveInstance_totalPage = totalPage;

        this.totalPage = totalPage;
    }

    private void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    /**
     * 设置数据源（请求发送后）
     */
    public void setData(List beanList, boolean loadMore) {

        isInit = true;

        mOnSaveInstance_loadMore = loadMore;

        if (beanList == null || beanList.size() == 0) {
            //初次加载为空
            if(mAdapter.getItemCount()==0 && mIRefreshStateView !=null) {
                mIRefreshStateView.showEmpty();
            }
            return;
        }
        if(mIRefreshStateView != null) {
            mIRefreshStateView.showContent();
        }
        //refresh
        if (!loadMore) {
            mAdapter.setNewData(beanList);
            currentPage = pageStartOffset;
            mRefreshLayout.endRefreshing();
            setLoadMore(true);
        } else {
            //没有传递totalPage，（验证发生在下次加载时）
            if (totalPage == -1 || totalPage == 0) {
                boolean valid = beanList.size() >= size;
                mAdapter.addData(beanList);
                currentPage++;
                setLoadMore(valid);
                if (valid) {
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreEnd();
                }
                return;
            }
            //有传递totalPage，（验证发生在这次加载后）
            if (currentPage < (totalPage + pageStartOffset) - 1) {
                mAdapter.addData(beanList);
                mAdapter.loadMoreComplete();
                currentPage++;
                setLoadMore(currentPage < (totalPage + pageStartOffset) - 1);
            } else {
                setLoadMore(false);
                mAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void setMessage(Object error, String content){

        isInit = true;

        mOnSaveInstance_error = error;
        mOnSaveInstance_content = content;

        if(mAdapter.getItemCount() == 0){
            if(mIRefreshStateView !=null) {
                mIRefreshStateView.showMessageFromNet(error, content);
                mRefreshLayout.endRefreshing();
            }
        }
        else{
            if(mIRefreshStateView !=null) {
                //错误是由 刷新 还是 加载更多 引发的
                if(mRefreshLayout.getCurrentRefreshStatus()!= RefreshLayout.RefreshStatus.IDLE){
                    mRefreshLayout.endRefreshing();
                    mIRefreshStateView.showMessage(content);
                }
                else{
                    mAdapter.loadMoreFail();
                }
            }
        }
    }

    public void startRequest(){
        mRefreshLayout.beginRefreshing();
    }
    public void endRequest(){
        mRefreshLayout.endRefreshing();
    }

    public boolean isEmpty(){
        return mAdapter.getItemCount() == 0;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if(mRestoreView!=null){
            SaveInstance saveInstance = mRestoreView.getSaveInstance();
            if(saveInstance!=null && saveInstance.isInit){
                if(saveInstance.isDataView){
                    setTotalPage(saveInstance.totalPage);
                    setData(saveInstance.beanList, saveInstance.loadMore);
                }
                else{  setMessage((T) saveInstance.error, saveInstance.content); }
                setCurrentPage(saveInstance.currentPageIndex);
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if(mRestoreView!=null && isInit){
            SaveInstance saveInstance =new SaveInstance();
            saveInstance.setDataView(mAdapter.getItemCount()!=0);
            saveInstance.setTotalPage(mOnSaveInstance_totalPage);
            saveInstance.setData(mAdapter.getData(),mOnSaveInstance_loadMore );
            saveInstance.setMessage(mOnSaveInstance_error, mOnSaveInstance_content);
            saveInstance.setCurrentPage(currentPage);
            saveInstance.setInit(isInit);
            mRestoreView.setSaveInstance(saveInstance);
        }
        return super.onSaveInstanceState();
    }

    /**---------------------------------------------getter---------------------------------------------*/

    public int getPageStartOffset() {
        return pageStartOffset;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public RefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }


    /**---------------------------------------------interface---------------------------------------------*/

    public static interface IRefreshListener {
        void onRefresh(RefreshLayout refreshLayout);

        void onLoadMore(int targetPage);
    }

    @IntDef(value = {Refresh, LoadMore, Refresh_LoadMore})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    /**
     * 利用中间类缓存RefreshListView的数据
     */
    public static interface IRestoreInstance {

        @CheckResult
        boolean isLoaded();

        void setSaveInstance(@NonNull RefreshCustomerLayout.SaveInstance saveInstance);

        RefreshCustomerLayout.SaveInstance getSaveInstance();
    }

    /**----------------------------------------onSaveInstance------------------------------------------*/

    public static class SaveInstance implements IRefreshDataView{

         boolean isInit;

         boolean isDataView;  int totalPage ;

         List beanList ; boolean loadMore ;

         Object error ;  String content ;

         int currentPageIndex;

        //已经请求过数据
        public boolean isLoaded(){  return isInit ;   }

        void setCurrentPage(int currentPageIndex){
            this.currentPageIndex =currentPageIndex;
        }

        void setInit(boolean isInit){
            this.isInit = isInit;
        }

        void setDataView(boolean isRequestNet){
            this.isDataView = isRequestNet;
        }

        @Override
        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        @Override
        public void setData(List beanList, boolean loadMore) {
            this.beanList = beanList;
            this.loadMore = loadMore;
        }

        @Override
        public void setMessage(Object error, String content) {
            this.error = error;
            this.content = content;
        }

    }
}
