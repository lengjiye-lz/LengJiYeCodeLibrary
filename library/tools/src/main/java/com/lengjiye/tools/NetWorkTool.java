package com.lengjiye.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.code.lengjiye.app.AppMaster;

/**
 * 网络相关
 * 创建人: lz
 * 创建时间: 2018/12/26
 * 修改备注:
 */
public class NetWorkTool {

    private NetWorkTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 网络类型
     */
    public interface NetWorkType {
        // 没有网络连接
        int NETWORK_NONE = 0;
        // wifi连接
        int NETWORK_WIFI = 1;
        // 2G
        int NETWORK_2G = 2;
        // 3G
        int NETWORK_3G = 3;
        // 4G
        int NETWORK_4G = 4;
        // 手机流量
        int NETWORK_MOBILE = 5;
    }

    /**
     * 获取运营商名字
     *
     * @param context context
     * @return int
     */
    public static String getOperatorName(Context context) {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // getSimOperatorName就可以直接获取到运营商的名字
        if (telephonyManager == null) {
            return "";
        }
        return telephonyManager.getSimOperatorName();
    }

    /**
     * 判断是否是wifi
     */
    public static boolean isWifi() {
        ConnectivityManager connectMgr = (ConnectivityManager) AppMaster.getInstance().getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        int type = info.getType();
        if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_ETHERNET) {
            return info.isConnected();
        }
        return false;
    }

    /**
     * 判断是否是手机网络
     */
    public static boolean isMobile() {
        ConnectivityManager connectMgr = (ConnectivityManager) AppMaster.getInstance().getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return info.isConnected();
        }
        return false;
    }

    /**
     * 判断是否有网络
     */
    public static boolean isConnected() {
        ConnectivityManager connectMgr = (ConnectivityManager) AppMaster.getInstance().getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            return info.isConnected();
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型
     *
     * @param context context
     * @return int
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (null == connManager) { // 为空则认为无网络
            return NetWorkType.NETWORK_NONE;
        }
        // 获取网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NetWorkType.NETWORK_NONE;
        }
        // 判断是否为WIFI
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NetWorkType.NETWORK_WIFI;
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            /*
             GPRS : 2G(2.5) General Packet Radia Service 114kbps
             EDGE : 2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
             UMTS : 3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
             CDMA : 2G 电信 Code Division Multiple Access 码分多址
             EVDO_0 : 3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
             EVDO_A : 3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
             1xRTT : 2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
             HSDPA : 3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
             HSUPA : 3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
             HSPA : 3G (分HSDPA,HSUPA) High Speed Packet Access
             IDEN : 2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
             EVDO_B : 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
             LTE : 4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
             EHRPD : 3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
             HSPAP : 3G HSPAP 比 HSDPA 快些
             */
            // 2G网络
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetWorkType.NETWORK_2G;
            // 3G网络
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetWorkType.NETWORK_3G;
            // 4G网络
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetWorkType.NETWORK_4G;
            default:
                return NetWorkType.NETWORK_MOBILE;
        }
    }
}
