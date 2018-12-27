package com.lengjiye.tools;

import android.util.Log;

import com.code.lengjiye.app.AppMaster;


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
    public static final String TAG_NAME = AppMaster.getInstance().getApplicationId();

    private LogTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @param message
     */
    public static void v(String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            logV(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void v(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            logV(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void logV(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            Log.v(tag, message);
        }
    }

    /**
     * @param message
     */
    public static void d(String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            logD(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void d(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            logD(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void logD(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            Log.d(tag, message);
        }
    }

    /**
     * @param message
     */
    public static void i(String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            logI(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void i(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            logI(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void logI(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            Log.i(tag, message);
        }
    }

    /**
     * @param message
     */
    public static void w(String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            logW(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void w(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            logW(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void logW(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            Log.w(tag, message);
        }
    }

    /**
     * @param message
     */
    public static void e(String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            logE(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void e(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            logE(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void logE(String tag, String message) {
        if (AppMaster.getInstance().getIsDebug()) {
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
