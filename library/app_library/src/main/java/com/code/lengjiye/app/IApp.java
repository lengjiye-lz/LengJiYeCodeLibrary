package com.code.lengjiye.app;

import android.content.Context;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public interface IApp {

    Context getAppContext();

    String getApplicationId();

    String getVersionName();

    String getPackageName();

    long getVersionCode();

    String getServiceAddress();

    boolean getIsDebug();

    /**
     * 编译环境
     *
     * @return
     */
    String getBuildType();


}
