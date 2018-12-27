package com.lengjiye.code.mvptest.contract;

import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.code.lengjiye.mvp.view.MvpView;
import com.lengjiye.code.mvptest.bean.PlayHomeFeedData;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/27
 * 修改备注:
 */
public interface MvpTestContract {
    interface View extends MvpView {
        /**
         * 请求数据成功
         */
        void getDataSuc(PlayHomeFeedData o);

        /**
         * 请求数据失败
         */
        void getDataFail();
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * 请求数据
         */
        void getData();
    }
}
