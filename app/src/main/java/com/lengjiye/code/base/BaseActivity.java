package com.lengjiye.code.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.lengjiye.code.constants.NoDoubleClickProxy;

import java.util.Locale;


/**
 * 基类activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 第一次调用
     */
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        int layoutResID = getResourceId();
        if (layoutResID != 0) {
            setContentView(layoutResID);
        }

        initIntentData();
        initHandler();
        initLanguage();
        initTitleBar();
        initViews();
        initData();

        firstLoad();
        registerBroadcast();

        setListener();

    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getResourceId();

    /**
     * 注册监听事件
     */
    protected void registerBroadcast() {
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
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化view
     */
    protected void initViews() {

    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {

    }

    /**
     * intent数据处理
     */
    protected void initIntentData() {
    }

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
     * 初始化handler
     */
    protected void initHandler() {
    }

    /**
     * 初始化window
     */
    protected void initWindow() {
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
}
