package com.lengjiye.code;

import android.app.Application;
import android.content.Context;

import com.code.lengjiye.app.AppMaster;
import com.code.lengjiye.app.IApp;

/**
 * application 单利模式
 * <p>
 * application 比较特殊，不能new，但是onCreate方法一定会走，所以在这里赋值
 * 创建人: lz
 * 创建时间: 2018/12/12
 * 修改备注:
 */
public class LJYApplication extends Application implements IApp {
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
        AppMaster.getInstance().setApp(this);
    }

    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public String getApplicationId() {
        return BuildConfig.APPLICATION_ID;
    }

    @Override
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public long getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public String getServiceAddress() {
        return BuildConfig.SERVICE_ADDRESS;
    }

    @Override
    public boolean getIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public String getBuildType() {
        return BuildConfig.BUILD_TYPE;
    }
}
