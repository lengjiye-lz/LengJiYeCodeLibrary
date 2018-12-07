package com.lengjiye.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具类
 * 创建人: lz
 * 创建时间: 2016/12/15
 * 修改备注:
 */
public class DateFormatTool {

    /**
     * 私有构造方法
     */
    private DateFormatTool() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 格式化 yyyy-MM-dd hh:mm:ss EE
     *
     * @param date
     * @return
     */
    public static String formatDate1(Date date) {
        return dateFormat("yyyy-MM-dd hh:mm:ss EE", date);
    }

    /**
     * 格式化 yyyy年MM月dd日 hh时mm分ss秒 EE
     *
     * @param date
     * @return
     */
    public static String formatDate2(Date date) {
        return dateFormat("yyyy年MM月dd日 hh时mm分ss秒 EE", date);
    }

    /**
     * 格式化 yyyy/MM/dd hh:mm:ss EE
     *
     * @param date
     * @return
     */
    public static String formatDate3(Date date) {
        return dateFormat("yyyy/MM/dd hh:mm:ss EE", date);
    }

    /**
     * 格式化 yyyy-MM-dd hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDate4(Date date) {
        return dateFormat("yyyy-MM-dd hh:mm:ss", date);
    }

    /**
     * 格式化 yyyy年MM月dd日 hh时mm分ss秒
     *
     * @param date
     * @return
     */
    public static String formatDate5(Date date) {
        return dateFormat("yyyy年MM月dd日 hh时mm分ss秒", date);
    }

    /**
     * 格式化 yyyyMMddhhmmss
     *
     * @param date
     * @return
     */
    public static String formatDate6(Date date) {
        return dateFormat("yyyyMMddhhmmss", date);
    }

    /**
     * 格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate7(Date date) {
        return dateFormat("yyyy-MM-dd", date);
    }

    /**
     * 格式化 yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static String formatDate8(Date date) {
        return dateFormat("yyyy年MM月dd日", date);
    }

    /**
     * 格式化 yyyy/MM/dd
     *
     * @param date
     * @return
     */
    public static String formatDate9(Date date) {
        return dateFormat("yyyy/MM/dd", date);
    }

    /**
     * 格式化 hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDate10(Date date) {
        return dateFormat("hh:mm:ss", date);
    }

    /**
     * 格式化 hhmmss
     *
     * @param date
     * @return
     */
    public static String formatDate11(Date date) {
        return dateFormat("hhmmss", date);
    }

    /**
     * 格式化
     *
     * @param template 类型
     * @param date     时间
     * @return
     */
    public static String dateFormat(String template, Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
        simpleDateFormat.applyPattern(template);
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 格式化 yyyy-MM-dd hh:mm:ss EE
     *
     * @param date
     * @return
     */
    public static Date formatString1(String date) {
        return stringFormat("yyyy-MM-dd hh:mm:ss EE", date);
    }

    /**
     * 格式化 yyyy年MM月dd日 hh时mm分ss秒 EE
     *
     * @param date
     * @return
     */
    public static Date formatString2(String date) {
        return stringFormat("yyyy年MM月dd日 hh时mm分ss秒 EE", date);
    }

    /**
     * 格式化 yyyy/MM/dd hh:mm:ss EE
     *
     * @param date
     * @return
     */
    public static Date formatString3(String date) {
        return stringFormat("yyyy/MM/dd hh:mm:ss EE", date);
    }

    /**
     * 格式化 yyyy-MM-dd hh:mm:ss
     *
     * @param date
     * @return
     */
    public static Date formaString4(String date) {
        return stringFormat("yyyy-MM-dd hh:mm:ss", date);
    }

    /**
     * 格式化 yyyy年MM月dd日 hh时mm分ss秒
     *
     * @param date
     * @return
     */
    public static Date formatString5(String date) {
        return stringFormat("yyyy年MM月dd日 hh时mm分ss秒", date);
    }

    /**
     * 格式化 yyyyMMddhhmmss
     *
     * @param date
     * @return
     */
    public static Date formatString6(String date) {
        return stringFormat("yyyyMMddhhmmss", date);
    }

    /**
     * 格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date formatString7(String date) {
        return stringFormat("yyyy-MM-dd", date);
    }

    /**
     * 格式化 yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static Date formatString8(String date) {
        return stringFormat("yyyy年MM月dd日", date);
    }

    /**
     * 格式化 yyyy/MM/dd
     *
     * @param date
     * @return
     */
    public static Date formatString9(String date) {
        return stringFormat("yyyy/MM/dd", date);
    }

    /**
     * 格式化 hh:mm:ss
     *
     * @param date
     * @return
     */
    public static Date formatString10(String date) {
        return stringFormat("hh:mm:ss", date);
    }

    /**
     * 格式化 hhmmss
     *
     * @param date
     * @return
     */
    public static Date formatString11(String date) {
        return stringFormat("hhmmss", date);
    }

    /**
     * 格式化
     *
     * @param template 类型
     * @param date     时间
     * @return
     */
    public static Date stringFormat(String template, String date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
        simpleDateFormat.applyPattern(template);
        Date time = null;
        try {
            time = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
