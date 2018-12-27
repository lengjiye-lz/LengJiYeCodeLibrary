package com.lengjiye.code.mvptest.presenter;

import com.code.lengjiye.mvp.presenter.BasicMvpPresenter;
import com.code.lengjiye.retrofit.observer.LoadingObserver;
import com.lengjiye.code.mvptest.bean.PlayHomeFeedData;
import com.lengjiye.code.mvptest.contract.MvpTestContract;
import com.lengjiye.code.mvptest.model.MvpTestModel;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/27
 * 修改备注:
 */
public class MvpTestPresenter extends BasicMvpPresenter<MvpTestContract.View> implements MvpTestContract.Presenter {

    private LoadingObserver loadingObserver;

    @Override
    public void attachView(MvpTestContract.View view) {
        super.attachView(view);
        loadingObserver = new LoadingObserver<PlayHomeFeedData>(getMvpView(), value -> {
            if (!isViewAttached()) {
                return;
            }
            getMvpView().getDataSuc(value);
        }, e -> {
            if (!isViewAttached()) {
                return;
            }
            getMvpView().getDataFail();
        });
    }

    @Override
    public void getData() {
        if (loadingObserver != null) {
            loadingObserver.cancelRequest();
        }
        MvpTestModel.getInstance().sendCheckMobile(getActivity(), loadingObserver);
    }

    @Override
    public void cancelRequestOnDestroy() {
        if (loadingObserver != null) {
            loadingObserver.cancelRequest();
        }
    }

}
