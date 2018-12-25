package com.code.lengjiye.mvp.presenter;

import android.support.annotation.UiThread;

import com.code.lengjiye.mvp.view.MvpView;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * 为Presenter添加View
     *
     * @param view
     */
    @UiThread
    void attachView(V view);

    /**
     * 解除Presenter与View的绑定
     */
    @UiThread
    void detachView();

    /**
     * 在View destroy的时候，取消请求
     */
    void cancelRequestOnDestroy();
}

