package com.code.lengjiye.app;

import android.content.Context;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class AppMaster implements IApp {

    private static class Instance {
        private static AppMaster mInstance = new AppMaster();
    }

    private AppMaster() {
    }

    public static AppMaster getInstance() {
        return Instance.mInstance;
    }

    private IApp iApp;

    @Override
    public Context getAppContext() {
        if (iApp == null) {
            return null;
        }
        return iApp.getAppContext();
    }

    @Override
    public String getApplicationId() {
        if (iApp == null) {
            return null;
        }
        return iApp.getApplicationId();
    }

    @Override
    public String getVersionName() {
        if (iApp == null) {
            return null;
        }
        return iApp.getVersionName();
    }

    @Override
    public String getPackageName() {
        if (iApp == null) {
            return null;
        }
        return iApp.getPackageName();
    }

    @Override
    public long getVersionCode() {
        if (iApp == null) {
            return 0;
        }
        return iApp.getVersionCode();
    }

    @Override
    public String getServiceAddress() {
        if (iApp == null) {
            return "";
        }
        return iApp.getServiceAddress();
    }

    @Override
    public boolean getIsDebug() {
        if (iApp == null) {
            return true;
        }
        return iApp.getIsDebug();
    }

    @Override
    public String getRunEnv() {
        if (iApp == null) {
            return "";
        }
        return iApp.getRunEnv();
    }
}
