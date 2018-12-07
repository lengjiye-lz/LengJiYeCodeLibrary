package com.lengjiye.tools;

import java.util.Calendar;

/**
 * 时间操作工具类
 * 创建人: lz
 * 创建时间: 2016/12/15
 * 修改备注:
 */
public class TimeTool {

    private TimeTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取当前毫秒数
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        long l = System.currentTimeMillis();
        return l;
    }

    /**
     * 获取当前年数
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月数
     * 月份需要+1
     *
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前天数
     *
     * @return
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前小时数
     *
     * @return
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前分钟数
     *
     * @return
     */
    public static int getMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前秒数
     *
     * @return
     */
    public static int getSecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当前星期数
     *
     * @return
     */
    public static int getWeek() {
        Calendar calendar = Calendar.getInstance();
        boolean b = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if (b) {
            i = i - 1;
            if (i == 0) {
                i = 7;
            }
        }
        return i;
    }
}
