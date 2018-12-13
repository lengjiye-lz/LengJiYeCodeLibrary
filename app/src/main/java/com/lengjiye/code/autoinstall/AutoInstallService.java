package com.lengjiye.code.autoinstall;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoInstallService extends BaseAccessibilityService {
    private static final String PACKAGE_INSTALL_1 = "com.android.packageinstaller";
    private static final String PACKAGE_INSTALL_2 = "com.google.android.packageinstaller";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (PACKAGE_INSTALL_1.equals(event.getPackageName()) || PACKAGE_INSTALL_2.equals(event.getPackageName())) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                    || event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED
                    || event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED
                    || event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                    ) {
                AccessibilityNodeInfo nodeInfo;
//                nodeInfo = findViewByText("下一步", true);
//                if (nodeInfo != null) {
//                    performViewClick(nodeInfo);
//                }

                // 华为充分了解风险，继续安装按钮
                nodeInfo = findViewByID("com.android.packageinstaller:id/decide_to_continue");
//                nodeInfo = findViewByText("安装", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

                // 华为继续安装按钮
                nodeInfo = findViewByID("android:id/button1");
//                nodeInfo = findViewByText("安装", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

                // 安装
                nodeInfo = findViewByID("com.android.packageinstaller:id/ok_button");
//                nodeInfo = findViewByText("安装", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

                // 三星继续安装
                nodeInfo = findViewByID("com.android.packageinstaller:id/continue_button");
//                nodeInfo = findViewByText("安装", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

//                nodeInfo = findViewByText("完成", true);
                nodeInfo = findViewByID("com.android.packageinstaller:id/done_button");
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

                // 兼容大部分手机
                nodeInfo = findViewByText("下一步", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }
                nodeInfo = findViewByText("安装", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }
                nodeInfo = findViewByText("完成", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }

                nodeInfo = findViewByText("G", true);
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
