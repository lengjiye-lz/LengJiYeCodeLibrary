package com.code.lengjiye.retrofit.model;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class BasicModel {

    /**
     * 控制线程，并发起订阅
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    protected <T> void makeSubscribe(LifecycleProvider provider, Observable<T> observable, Observer<T> observer) {
        observable = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        lifecycle(provider, observable)
                .subscribe(observer);
    }

    protected <T> Observable<T> lifecycle(LifecycleProvider provider, Observable<T> observable) {
        if (provider instanceof RxAppCompatActivity) {
            observable = observable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY));
        } else if (provider instanceof RxFragment) {
            observable = observable.compose(provider.<T>bindUntilEvent(FragmentEvent.DESTROY));
        }
        return observable;
    }
}
