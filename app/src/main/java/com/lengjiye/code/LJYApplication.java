package com.lengjiye.code;

import android.app.Application;

/**
 * application 单利模式
 * <p>
 * application 比较特殊，不能new，但是onCreate方法一定会走，所以在这里赋值
 * 创建人: lz
 * 创建时间: 2018/12/12
 * 修改备注:
 */
public class LJYApplication extends Application {
    private static LJYApplication instance;

    /**
     * 获取单利
     */
    public static LJYApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
