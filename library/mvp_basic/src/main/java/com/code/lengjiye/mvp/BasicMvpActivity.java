package com.code.lengjiye.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.code.lengjiye.basic.BasicActivity;
import com.code.lengjiye.mvp.delegate.MvpDelegate;
import com.code.lengjiye.mvp.delegate.MvpDelegateCallback;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.code.lengjiye.mvp.view.MvpView;

/**
 * mvp 基类
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public abstract class BasicMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends BasicActivity implements MvpView, MvpDelegateCallback<V, P> {

    protected MvpDelegate<V, P> mMvpDelegate;
    protected P mPresenter;

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 初始化view
     */
    protected void initViews() {
        super.initViews();
    }

    @Override
    public abstract P createPresenter();

    protected MvpDelegate<V, P> getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getMvpDelegate() != null) {
            getMvpDelegate().onViewCreated();
        }
    }

    @Override
    public void showError(String errMes) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getMvpDelegate() != null) {
            getMvpDelegate().onDestroyView();
        }
    }
}
