package com.lengjiye.tools;

import android.util.Log;


/**
 * 类描述: 日志工具类
 * 功能：1.release版本没有日志输出
 * 2.日志显示类名，方法名，代码行数等信息
 * 3.捕捉到崩溃日志保存在本地，每天一个文件，方便查看
 * 创建人: lz
 * 创建时间: 2016/11/22
 * 修改备注:
 */
public class LogTool {

    public static final String SEPARATOR = ",";
    public static final String TAG_NAME = "lengjiye";

    private LogTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @param message
     */
    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    /**
     * Get default tag name
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return TAG_NAME + "-" + tag;
    }

    /**
     * get stack info
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // thread name
        String threadName = Thread.currentThread().getName();
        // thread ID
        long threadID = Thread.currentThread().getId();
        // file name
        String fileName = stackTraceElement.getFileName();
        // class name
        String className = stackTraceElement.getClassName();
        // method
        String methodName = stackTraceElement.getMethodName();
        // code line
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[line:");
//        logInfoStringBuilder.append("threadID=" +
//                threadID).append(SEPARATOR);
//        logInfoStringBuilder.append("threadName=" +
//                threadName).append(SEPARATOR);
//        logInfoStringBuilder.append("fileName=" +
//                fileName).append(SEPARATOR);
//        logInfoStringBuilder.append("className="
//                + className).append(SEPARATOR);
//        logInfoStringBuilder.append("methodName=" +
//                methodName).append(SEPARATOR);
        logInfoStringBuilder.append(lineNumber);
        logInfoStringBuilder.append("] ");
        methodName = logInfoStringBuilder.toString();
        return methodName;
    }
}
