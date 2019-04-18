package com.lengjiye.code.activity;


import android.util.Log;
import android.view.View;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.lengjiye.code.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
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
                test31();
                test32();
                break;
        }
    }

    private void test32() {
        // combineLatest 拼接最新发射的两个数据
        // 第一组数据发射的时候，第二个还没有发射，所以不能拼接，直到第一组数据发射到最后一个，第二组数据才发射，
        // 所以发射出的第一组数据是6*6，第二组数6*7...以此类推
        // 输出结果为 36 42 48 54 60
        Observable.combineLatest(Observable.range(1, 6), Observable.range(6, 5),
                (integer, integer2) -> {
                    Log.e("lz", "integer:" + integer);
                    Log.e("lz", "integer2:" + integer2);
                    return integer * integer2;
                })
                .subscribe(integer -> Log.e("lz", "integer222222222222:" + integer));
    }

    private void test31() {
        // zip 按照指定规则合并数据，两个数据源的数量不同，没有对应位置的数据源不参与运算
        Observable.zip(Observable.range(1, 6), Observable.range(6, 5),
                (integer, integer2) -> {
                    Log.e("lz", "integer:" + integer);
                    Log.e("lz", "integer2:" + integer2);
                    return integer * integer2;
                })
                .subscribe(integer -> Log.e("lz", "integer111111111111111:" + integer));
    }

    private void test30() {
        // concat 合并数据，会严格按照数据源的顺序发射
        Observable.concat(Observable.range(1, 5), Observable.range(3, 10)).subscribe(integer ->
                Log.e("lz", "integer:" + integer));

        // concat 合并多个数据源，会严格按照数据源的顺序发射
        Observable.concatArray(Observable.range(1, 5), Observable.range(3, 10)
                , Observable.range(3, 10), Observable.range(3, 10))
                .subscribe(integer -> Log.e("lz", "integer2:" + integer));

        // concatDelayError 合并多个数据源，不能保证发射的顺序，
        // 和 concat 的区别是 concatDelayError 等数据全部发射完毕才发射错误信息，而且无论出现多少错误，都只发射一次，
        // concat 是出现错误马上报错，并停止发射数据
        Log.e("lz", "开始:");
        Observable.concatDelayError(Observable.create(e -> {
            Thread.sleep(2000);
            e.onError(new Exception("error"));
        })).subscribe(integer -> Log.e("lz", "integer2:" + integer),
                throwable -> Log.e("lz", "throwable:" + throwable.getMessage()));
        Log.e("lz", "结束:");

        // concatArrayEager 和 concatArray 类似， concatArrayEager 如果有观察者订阅了他之后，
        // 就相当于订阅了他所有的 ObservableSource ,并且先缓存起来，然后按照顺序把他们发射出来
        Observable.concatArrayEager(Observable.range(1, 5), Observable.range(3, 10)
                , Observable.range(3, 10), Observable.range(3, 10))
                .subscribe(integer -> {
                    Log.e("lz", "integer:" + integer);
                });
    }

    private void test29() {
        // merge 合并两个数据源，不能保证发射的顺序
        Observable.merge(Observable.range(1, 5), Observable.range(3, 10))
                .subscribe(integer -> Log.e("lz", "integer1:" + integer));

        // merge 合并多个数据源，不能保证发射的顺序
        Observable.mergeArray(Observable.range(1, 5), Observable.range(3, 10)
                , Observable.range(3, 10), Observable.range(3, 10))
                .subscribe(integer -> Log.e("lz", "integer2:" + integer));


        // mergeDelayError 合并多个数据源，不能保证发射的顺序，
        // 和 merge 的区别是 mergeDelayError 等数据全部发射完毕才发射错误信息，而且无论出现多少错误，都只发射一次，
        // merge 是出现错误马上报错，并停止发射数据
        Log.e("lz", "开始:");
        Observable.mergeDelayError(Observable.range(1, 5),
                Observable.range(3, 10).repeat(2),
                Observable.create(e -> {
                    Thread.sleep(2000);
                    e.onError(new Exception("error"));
                }))
                .subscribe(integer -> Log.e("lz", "integer2:" + integer),
                        throwable -> Log.e("lz", "throwable:" + throwable.getMessage()));
        Log.e("lz", "结束:");
    }

    private void test28() {
        // startWith 在指定的数据源的前面插入数据
        Observable.range(1, 5)
                .startWith(0)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));

        Observable.range(1, 5)
                .startWithArray(0, -1)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }


    private void test27() {
        // sample  获取每个时间片最后一个数据进行发送
        Observable.interval(60, TimeUnit.MILLISECONDS)
                .sample(500, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }

    private void test26() {
        // debounce 限制发送数据过快，在指定的一段时间还没有发射数据时才发射数据
        Observable.interval(600, TimeUnit.MILLISECONDS)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }

    private void test25() {
        // 按照时间对数据源进行分片
        // throttleLast 获取每个时间片最后一个数据进行发送
        // throttleFirst 获取每个时间片第一个数据进行发送
        // throttleLatest 发射距离指定时间片最近的那个数据进行发送
        // throttleWithTimeout 在指定的一段时间内没有数据发射时会发射一个数据，
        // 如果在一个时间片达到之前，发射的数据之后又紧跟着发射了一个数据，那么这个时间片之内之前发射的数据会被丢掉
        Observable.interval(80, TimeUnit.MICROSECONDS)
                .throttleLast(500, TimeUnit.MICROSECONDS)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));

        Observable.interval(80, TimeUnit.MICROSECONDS)
                .throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));

        Observable.interval(501, TimeUnit.MICROSECONDS)
                .throttleWithTimeout(500, TimeUnit.MICROSECONDS)
                .subscribe(aLong -> Log.e("lz", "aLong:" + aLong));
    }

    private void test24() {
        // ignoreElements 过滤掉所有源产生的结果，只返回 Complete 和 error 回调
        Observable.range(1, 5).ignoreElements()
                .subscribe(() -> Log.e("lz", "Complete:"),
                        throwable -> Log.e("lz", "error:"));
    }

    private void test23() {
        // 与 skip 相对，只选择前面n项
        Observable.range(1, 5).take(2)
                .subscribe(integer -> Log.e("lz", "integer2:" + integer));

        // take 和 takeLast
        long sys = System.currentTimeMillis();
        // 计时5秒钟,只输出前2秒的数据然后结束
        long finalSys = sys;
        Observable.range(1, 5).repeatUntil(() ->
                System.currentTimeMillis() > TimeUnit.SECONDS.toMillis(5) + finalSys)
                .take(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("lz", "integer3:" + integer);
                    }
                });
        // 计时执行5秒钟，只输最后2秒的数据
        sys = System.currentTimeMillis();
        long finalSys1 = sys;
        Observable.range(1, 5).repeatUntil(() ->
                System.currentTimeMillis() > TimeUnit.SECONDS.toMillis(5) + finalSys1)
                .takeLast(2, TimeUnit.SECONDS)
                .subscribe(integer -> Log.e("lz", "integer4:" + integer));
    }

    private void test22() {
        // skip 过滤掉前面n项
        Observable.range(1, 5).skip(2)
                .subscribe(integer -> Log.e("lz", "integer1:" + integer));
        // 与 skip 相对，只选择前面n项
        Observable.range(1, 5).take(2)
                .subscribe(integer -> Log.e("lz", "integer2:" + integer));

        // 与 skip 相对，过滤掉后面几项
        Observable.range(1, 5).skipLast(2)
                .subscribe(integer -> Log.e("lz", "integer5:" + integer));

        // skip 和 skipLast
        long sys = System.currentTimeMillis();
        // 2秒钟之后开始执行,执行5秒钟之后停止
        long finalSys = sys;
        Observable.range(1, 5).repeatUntil(() ->
                System.currentTimeMillis() > TimeUnit.SECONDS.toMillis(5) + finalSys)
                .skip(2, TimeUnit.SECONDS)
                .subscribe(integer -> Log.e("lz", "integer3:" + integer));

        // 计时执行5秒钟，最后2秒不再输出数据，5秒钟之后执行结束
        sys = System.currentTimeMillis();
        long finalSys1 = sys;
        Observable.range(1, 5).repeatUntil(() ->
                System.currentTimeMillis() > TimeUnit.SECONDS.toMillis(5) + finalSys1)
                .skipLast(2, TimeUnit.SECONDS)
                .subscribe(integer -> Log.e("lz", "integer4:" + integer));
    }

    private void test21() {
        // distinct 过滤掉重复元素
        // 输出结果为1234567
        Observable.just(1, 2, 3, 4, 5, 5, 5, 6, 7, 6).distinct()
                .subscribe(integer -> Log.e("lz", "integer:" + integer));

        // distinctUntilChanged 和 distinct 类似，不同的是只能过滤掉相邻重复的元素
        // 输出结果为12345676
        Observable.just(1, 2, 3, 4, 5, 5, 5, 6, 7, 6).distinctUntilChanged()
                .subscribe(integer -> Log.e("lz", "integer:" + integer));
    }

    private void test20() {
        // element 根据条件获取数据源指定位置的数据
        // 会输出第二位数字
        Observable.range(1, 10).elementAt(1)
                .subscribe(integer -> Log.e("lz", "integer:" + integer));

        Observable.range(1, 10).lastElement()
                .subscribe(integer -> Log.e("lz", "integer:" + integer));

        Observable.range(1, 10).firstElement()
                .subscribe(integer -> Log.e("lz", "integer:" + integer));
    }

    private void test19() {
        // filter 指定条件过滤数据源，true返回，false过滤掉
        Observable.range(1, 10).filter(integer -> integer > 5)
                .subscribe(integer -> Log.e("lz", "integer:" + integer));
    }

    /******************************************变换型操作符***********************************************/

    private void test18() {
        // window 和 buffer 函数差不多，但是不是发送原始数据，而是发送分组的数据
        Observable.range(1, 10).window(3).subscribe(integerObservable -> {
            Log.e("lz", "integer:" + integerObservable.hashCode());
            integerObservable.subscribe(integer -> Log.e("lz", "integer:" + integer));
        });
    }

    private void test17() {
        // scan 累加函数，函数的结果作为第一个变量，继续和剩余变量进行运算，直到结束
        Observable.range(2, 5).map(integer -> Float.valueOf(integer)).scan((integer, integer2) -> {
            Log.e("lz", "integer:" + integer);
            Log.e("lz", "integer2:" + integer2);
            return (integer % integer2);
        }).subscribe(integer -> Log.e("lz", "结果:" + integer));
    }

    private void test16() {
        // groupBy 指定元素分组，可以用来根据指定的条件将元素分组。
        // 首先将两组元素合并成一个组元素，然后按照整数进行分组，然后再把分好组的元素进行输出
        Observable<GroupedObservable<Integer, Integer>> groupedObservableObservable =
                Observable.concat(Observable.range(1, 5), Observable.range(2, 8)).
                        groupBy(integer -> integer);

        Observable.concat(groupedObservableObservable).subscribe(integer ->
                Log.e("lz", "integer:" + integer));
    }

    private void test15() {
        // buffer 将整个数据流分组，buffer相当于缓存区，当缓存区慢了或者剩余不足填满缓存区时，发射数据
        Observable.range(1, 7).buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.e("lz", "integers:" + Arrays.toString(integers.toArray()));
                Observable.fromIterable(integers).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("lz", "integer:" + integer);
                    }
                });
            }
        });
    }

    private void test14() {
        // 将任意元素转化为Iterable对象，然后再进行处理
        Observable.range(1, 5).flatMapIterable((Function<Integer, Iterable<String>>) integer ->
                // 转化为String队列
                Collections.singletonList(String.valueOf(integer))).subscribe(s ->
                Log.e("lz", "s:" + s));
    }

    private void test13() {
        // flatMap 把多个请求合并成一个结果返回，不能保证结果的顺序和之前请求的顺序一定相同
        Observable.range(1, 50).flatMap((Function<Integer, ObservableSource<String>>) integer ->
                Observable.just(integer.toString())).subscribe(o -> Log.e("lz", "o:" + o));
        // concatMap 和 flatMap 对应，保证返回的顺序和请求的顺序一定相同
        Observable.range(1, 50).concatMap((Function<Integer, ObservableSource<String>>) integer ->
                Observable.just(String.valueOf(integer))).subscribe(o -> Log.e("lz", "o1111:" + o));
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
