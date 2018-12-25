package com.code.lengjiye.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.code.lengjiye.basic.BasicFragment;
import com.code.lengjiye.mvp.delegate.MvpDelegate;
import com.code.lengjiye.mvp.delegate.MvpDelegateCallback;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.code.lengjiye.mvp.view.MvpView;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public abstract class BasicMvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends BasicFragment implements MvpView, MvpDelegateCallback<V, P> {

    protected MvpDelegate<V, P> mMvpDelegate;
    protected P mPresenter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (getMvpDelegate() != null) {
            getMvpDelegate().onDestroyView();
        }
    }
}
