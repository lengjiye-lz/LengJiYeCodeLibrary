package com.lengjiye.code.constants;

import android.view.View;

import com.lengjiye.tools.LogTool;

/**
 * 使用代理模式，处理多次点击
 *
 * @author lz
 */
public class NoDoubleClickProxy implements View.OnClickListener {

    private static final long MIN_DELAY_TIME = 800;
    private View.OnClickListener origin;
    private static long lastclick = 0;

    public NoDoubleClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= MIN_DELAY_TIME) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        } else {
            LogTool.e("lz", "重复点击");
        }
    }

    /**
     * 是否点击太快了
     *
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();

        if ((currentClickTime - lastclick) >= MIN_DELAY_TIME) {
            lastclick = currentClickTime;
            flag = false;
        }
        return flag;
    }
}