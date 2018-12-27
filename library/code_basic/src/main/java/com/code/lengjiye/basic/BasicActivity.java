package com.code.lengjiye.basic;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;

import com.lengjiye.tools.proxy.NoDoubleClickProxy;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Locale;

/**
 * Activity 基类
 * 创建人: lz
 * 创建时间: 2018/12/13
 * 修改备注:
 */
public abstract class BasicActivity extends RxAppCompatActivity implements View.OnClickListener {

    /**
     * 第一次调用
     */
    private boolean isFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        int layoutResID = getResourceId();
        if (layoutResID != 0) {
            setContentView(layoutResID);
        }
        initIntentData();
        initLanguage();

        initViews();
        initDates();

        firstLoad();

        registerBroadcast();

        setListener();
    }

    /**
     * 初始化window
     */
    protected void initWindow() {

    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getResourceId();

    /**
     * 初始化语言
     */
    protected void initLanguage() {
        //设置应用语言类型
        Resources resources = getResources();
        if (null == resources) {
            return;
        }
        Configuration config = resources.getConfiguration();
        if (null == config) {
            return;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (config.locale != Locale.CHINA) {
            config.locale = Locale.CHINA;
            resources.updateConfiguration(config, dm);
        }
    }

    /**
     * intent数据处理
     */
    protected void initIntentData() {
    }

    /**
     * 初始化view
     */
    protected void initViews() {
    }

    /**
     * 初始化数据
     */
    protected void initDates() {
    }

    /**
     * 第一次调用
     */
    private void firstLoad() {
        if (isFirst) {
            return;
        }
        isFirst = true;
        onFirstLoad();
    }

    /**
     * 第一次调用
     * <p>
     * 只调用一次
     */
    protected void onFirstLoad() {
    }


    /**
     * 注册监听事件
     */
    protected void registerBroadcast() {
    }

    /**
     * 设置监听事件
     */
    protected void setListener() {
    }

    @Override
    public void onClick(View view) {
    }

    /**
     * 批量设置点击事件
     *
     * @param views
     */
    protected void setOnClickListener(View... views) {
        if (views == null || views.length <= 0) {
            return;
        }

        for (View v : views) {
            if (v == null)
                continue;
            v.setOnClickListener(new NoDoubleClickProxy(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
