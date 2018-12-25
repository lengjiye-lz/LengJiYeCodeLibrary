package com.code.lengjiye.mvp.delegate;

import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.code.lengjiye.mvp.view.MvpView;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * 创建一个presenter实例，用于MvpDelegate中绑定Presenter和View用
     * 在MvpView的实现类中实现
     *
     * @return
     */
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();
}
