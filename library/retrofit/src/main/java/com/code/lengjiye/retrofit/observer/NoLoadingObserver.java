/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.code.lengjiye.retrofit.observer;


import com.code.lengjiye.mvp.view.MvpView;
import com.code.lengjiye.retrofit.ApiException;
import com.code.lengjiye.retrofit.R;
import com.lengjiye.tools.NetWorkTool;
import com.lengjiye.tools.ResTool;

import io.reactivex.disposables.Disposable;

/**
 * 请求回调统一处理
 * <p>
 * 不显示dialog
 *
 * @param <T>
 */

public class NoLoadingObserver<T> extends LoadingObserver<T> {
    public NoLoadingObserver(MvpView mvpView, ObserverOnNextListener<T> onNextListener, ObserverOnErrorListener onErrorListener) {
        super(mvpView, onNextListener, onErrorListener, false);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;

        if (mMvpView != null && mMvpView.isAlived()) {

            if (!NetWorkTool.isConnected()) {
                String errorMsg = ResTool.getString(mMvpView.getContext().getApplicationContext(), R.string.a_0210);

                ApiException apiException = new ApiException(-10001, errorMsg, null);
                //如果设置了监听，通过监听处理
                if (mOnErrorListener != null) {
                    mOnErrorListener.observerOnError(apiException);
                } else {
                    mMvpView.showError(ResTool.getString(mMvpView.getContext().getApplicationContext(), R.string.a_0210));
                }

                mDisposable.dispose();
            } else {
                //显示loading
                if (mOnLoadingListener != null) {
                    mOnLoadingListener.onShowLoading();
                }
//                else {
//                    mMvpView.showLoading();
//                }
            }
        }
    }

}
