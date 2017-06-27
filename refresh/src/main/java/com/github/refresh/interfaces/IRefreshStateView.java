package com.github.refresh.interfaces;


public interface IRefreshStateView {

    void showError(Object error, String content);

    void showEmpty();

    void showContent();

    void showMessage(String content);
}
