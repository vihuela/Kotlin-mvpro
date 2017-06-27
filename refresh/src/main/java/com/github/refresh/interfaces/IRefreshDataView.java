package com.github.refresh.interfaces;

import java.util.List;


public interface IRefreshDataView {

    void setTotalPage(int totalPage);

    void setData(List beanList, boolean loadMore);

    void setMessage(Object error, String content);
}
