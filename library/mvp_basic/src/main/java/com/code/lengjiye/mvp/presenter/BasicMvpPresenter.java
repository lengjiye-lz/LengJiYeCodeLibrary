package com.code.lengjiye.mvp.presenter;

import android.support.annotation.UiThread;

import com.code.lengjiye.basic.BasicActivity;
import com.code.lengjiye.basic.BasicFragment;
import com.code.lengjiye.mvp.view.MvpView;

import java.lang.ref.WeakReference;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public abstract class BasicMvpPresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> mViewRef;


    @UiThread
    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public V getMvpView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    public BasicActivity getActivity() {
        if (getMvpView() != null && getMvpView() instanceof BasicActivity) {
            return (BasicActivity) getMvpView();
        } else {
            return null;
        }
    }


    public BasicFragment getFragment() {
        if (getMvpView() != null && getMvpView() instanceof BasicFragment) {
            return (BasicFragment) getMvpView();
        } else {
            return null;
        }
    }


    /**
     * 判断当前Presenter是否持有View
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @UiThread
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        cancelRequestOnDestroy();
    }

}
