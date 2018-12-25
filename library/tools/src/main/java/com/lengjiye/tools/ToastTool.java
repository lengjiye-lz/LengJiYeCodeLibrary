package com.lengjiye.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuz on 2017/9/18.
 *
 * @author lz
 */

public class ToastTool {
    private static ToastTool toastTool;

    private ToastTool() {
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static ToastTool getInstance() {
        if (toastTool == null) {
            toastTool = new ToastTool();
        }
        return toastTool;
    }

    /**
     * 显示消息
     *
     * @param mContext
     * @param message
     */
    public void show(Context mContext, String message) {
        if (StringTool.isBlank(message)) {
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
