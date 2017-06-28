package com.github.refresh.interfaces;


/**
 * 用于传递处理之后的对象，在执行setMessage前提下
 */
public interface IRefreshStateView {

    void showMessageFromNet(Object error, String content);

    void showMessage(String content);

    void showEmpty();

    void showContent();
}
