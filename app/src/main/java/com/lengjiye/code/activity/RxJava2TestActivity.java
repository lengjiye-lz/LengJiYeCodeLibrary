package com.lengjiye.code.activity;


import android.util.Log;
import android.view.View;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.lengjiye.code.R;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 自动测试activity
 */
public class RxJava2TestActivity extends BasicMvpActivity {


    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

    @Override
    public boolean isAlived() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_rxjava2;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button1));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button1:
                test12();
                break;
        }
    }


    private void test12() {
        // map 把一个数据类型转换成另外一个数据类型
        Observable.range(1, 5).map(integer -> {
            Log.e("lz", "integer:" + integer);
            return integer.toString();
        }).subscribe(s -> Log.e("lz", "s:" + s));

        // cast 将原始数据每一项强制装换为指定的数据，如果不能转换会报错，然后再发射数据
        Observable.just(new Date()).cast(Object.class).subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }


    /******************************************创建型操作符***********************************************/

    private void test11() {
        // 延迟执行任务
        Observable.timer(10, TimeUnit.SECONDS).subscribe(aLong ->
                Log.e("lz", "aLong:" + aLong));
    }


    int i = 0;

    private void test10() {
        // repeat 循环次数，不传为无线循环
        Observable.range(1, 100).repeat(10).subscribe(integer ->
                Log.e("lz", "integer:" + integer));
        // repeatUntil 返回fasle继续循环，返回true终止循环，类似do-while，最少执行一次
        AtomicBoolean b = new AtomicBoolean(false);
        Observable.just(1, 10).repeat(1).repeatUntil(() -> {
            Log.e("lz", "aaaaaaaaaa:" + b.get());
            i++;
            return b.get();
        }).subscribe(integer -> {
                    if (i == 10) {
                        b.set(true);
                    }
                    Log.e("lz", "integer:" + integer + "bbbbb:" + b.get() + "iiiiii:" + i);
                }
        );
    }

    private void test9() {
        // 调用的是fromArray方法
        Observable.just(1, 2, 3).repeat(10).subscribe(integer -> Log.e("lz", "integer:" + integer));
    }


    /**
     * from 系列
     * 用来从指定的数据源中提取Observable
     */
    private void test8() {
        // 从数组中提取
        Observable.fromArray(1, 2, 3, 4, 5, 6).subscribe(integer -> Log.e("lz", "integer:" + integer));
        // 从Callable中提取
        Observable.fromCallable((Callable<Object>) () -> "asdcasdcas")
                .subscribe(o -> Log.e("lz", "o:" + o));
        // 从future中提取，可以用来指定线程和超时信息
        Observable.fromFuture(new FutureTask<Object>(() -> "111111111111"))
                .subscribe(o -> Log.e("lz", "complete"));
        // 从iterable
        Observable.fromIterable(() -> new Iterator<Object>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return "22222222222";
            }
        }).subscribe(o -> Log.e("lz", "o:" + o));

        // 从publisher中提取
        Observable.fromPublisher(s -> s.onNext(1))
                .subscribe(o -> Log.e("lz", "o:" + o));

    }

    private void test7() {
        // 创建一个不发射任何数据但是正常终止的Observable
        Observable.empty().subscribe(o -> Log.e("lz", "next:" + o),
                throwable -> Log.e("lz", "error:" + throwable),
                () -> Log.e("lz", "complete:"));

        // 创建一个不发射任何数据也不终止的Observable
        Observable.never().subscribe(o -> Log.e("lz", "next:" + o),
                throwable -> Log.e("lz", "error:" + throwable),
                () -> Log.e("lz", "complete:"));

        // 创建一个不发射任何数据，以一个错误状态终止的Observable
        Observable.error(() -> new Throwable("eooro")).subscribe(o -> Log.e("lz", "next:" + o),
                throwable -> Log.e("lz", "error:" + throwable),
                () -> Log.e("lz", "complete:"));
    }

    /**
     * 创建型操作符
     * <p>
     * 订阅了才会执行，所以下面两个订阅的结果是不一样的
     */
    private void test6() {
        Observable observable = Observable.defer((Callable<ObservableSource<?>>) () -> Observable.just(System.currentTimeMillis()));
        observable.subscribe(o -> Log.e("lz", "o:" + o));

        observable.subscribe(o -> Log.e("lz", "o1111111:" + o));


    }

    /**
     * 创建型操作符
     * <p>
     * 从头开始创建，需要手动调用onNext,onComplete,onError 方法
     */
    private void test5() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
//            e.onError(new Throwable("cuowu"));
            e.onComplete();
            e.onNext(4);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> Log.e("lz", "next:" + o),
                        throwable -> Log.e("lz", "error:" + throwable.getMessage()),
                        () -> Log.e("lz", "complete:"));
    }

    /**
     * 简单demo
     */
    private void test1() {
        Disposable subscribe = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).subscribe(o -> Log.e("lz", "o:" + o));
    }

    /**
     * 创建型操作符
     * <p>
     * 用于周期性执行任务
     * <p>
     * long initialDelay 第一次延迟执行
     * long period 间隔周期
     * TimeUnit unit 间隔时间格式
     * Scheduler scheduler 指定线程，默认当前线程
     */
    private void test2() {
        // 无限次执行周期性任务
        Observable.interval(1, 3, TimeUnit.SECONDS, Schedulers.newThread()).subscribe(
                aLong -> Log.e("lz", "无限次 aLong:" + aLong)
        );

        // 执行周期性任务5次
        Observable.interval(1, 3, TimeUnit.SECONDS, Schedulers.newThread()).take(5).subscribe(
                aLong -> Log.e("lz", "次数限制 aLong:" + aLong)
        );

        // 执行周期性任务5秒钟
        Observable.interval(1, 3, TimeUnit.SECONDS, Schedulers.newThread()).take(5,
                TimeUnit.SECONDS).subscribe(
                aLong -> Log.e("lz", "时间限制 aLong:" + aLong)
        );
    }

    /**
     * 创建型操作符
     * <p>
     * 周期性执行任务
     * <p>
     * <p>
     * long start 从第几次开始
     * long count 一共执行多少次
     * long initialDelay 第一次执行延迟时间
     * long period 间隔时间
     * TimeUnit unit 时间单位
     * Scheduler scheduler 指定线程
     */
    private void test3() {
        Log.e("lz", "开始执行");
        Observable.intervalRange(3, 10, 1, 2, TimeUnit.SECONDS, Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("lz", "次数限制 aLong:" + aLong);
                    }
                });

        // 当take（）小于count时 take起作用。不然不起作用
        Observable.intervalRange(3, 20, 1, 2, TimeUnit.SECONDS, Schedulers.newThread())
                .take(11)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("lz", "次数限制11111111 aLong:" + aLong);
                    }
                });
    }

    /**
     * 产生一个范围的整数数列
     * <p>
     * long类型
     * <p>
     * final int start 开始数字
     * final int count 数列个数
     */
    private void test4() {
        Observable.range(0, Integer.MAX_VALUE).subscribeOn(Schedulers.newThread())
                .subscribe(integer -> Log.e("lz", "integer:" + integer));

        Observable.rangeLong(3, 10).subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }
}
