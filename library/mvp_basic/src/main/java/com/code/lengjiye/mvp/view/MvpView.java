package com.code.lengjiye.mvp.view;

import android.content.Context;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public interface MvpView {

    /**
     * 显示错误信息
     *
     * @param errMes
     */
    void showError(String errMes);

    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 隐藏loading
     */
    void dismissLoading();

    /**
     * view是否可用
     *
     * @return
     */
    boolean isAlived();


    Context getContext();
}
