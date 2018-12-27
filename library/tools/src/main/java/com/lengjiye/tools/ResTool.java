package com.lengjiye.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.code.lengjiye.app.AppMaster;

/**
 * 获取资源文件
 * 创建人: lz
 * 创建时间: 2018/12/27
 * 修改备注:
 */
public class ResTool {

    private ResTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getString(int resId) {
        return AppMaster.getInstance().getAppContext().getString(resId);
    }

    public static int getColor(int resId) {
        return ContextCompat.getColor(AppMaster.getInstance().getAppContext(), resId);
    }

    public static String getString(Context context, int resId) {
        return context.getApplicationContext().getString(resId);
    }

    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getApplicationContext().getString(resId, formatArgs);
    }

    public static String getString(int resId, Object... formatArgs) {
        return AppMaster.getInstance().getAppContext().getString(resId, formatArgs);
    }

    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context.getApplicationContext(), resId);
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(AppMaster.getInstance().getAppContext(), resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context.getApplicationContext(), resId);
    }

    public static int getDimens(Context context, int resId) {
        return context.getApplicationContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取字体大小相同不同颜色的2个字符组成的字符串
     */
    public static Spannable getColorString(String font, String after, int fontColor, int afterColor) {
        String connectStr = new StringBuilder().append(font).append(after).toString();
        Spannable span = new SpannableString(connectStr);
        int indexOfFont = connectStr.indexOf(font);
        int indexOfAfter = connectStr.indexOf(after);
        span.setSpan(new ForegroundColorSpan(fontColor), indexOfFont, indexOfFont + font.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(afterColor), indexOfAfter, indexOfAfter + after.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}
