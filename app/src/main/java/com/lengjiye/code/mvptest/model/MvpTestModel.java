package com.lengjiye.code.mvptest.model;

import com.code.lengjiye.retrofit.HttpResultFunc;
import com.code.lengjiye.retrofit.model.BasicModel;
import com.code.lengjiye.retrofit.network.ServiceHolder;
import com.google.gson.Gson;
import com.lengjiye.code.mvptest.bean.PlayHomeFeedData;
import com.lengjiye.code.mvptest.service.MvpTestService;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/27
 * 修改备注:
 */
public class MvpTestModel extends BasicModel {

    private static class Instance {
        private static MvpTestModel model = new MvpTestModel();
    }

    private MvpTestModel() {

    }

    public static MvpTestModel getInstance() {
        return Instance.model;
    }

    private synchronized MvpTestService getService() {
        return ServiceHolder.getInstance().getService(MvpTestService.class);
    }

    /**
     * @param observer
     */
    public void sendCheckMobile(LifecycleProvider provider, Observer<PlayHomeFeedData> observer) {
        Map map = new HashMap();
        map.put("p", 1);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Observable observable = getService().sendCheckMobile(body).map(new HttpResultFunc<>());
        makeSubscribe(provider, observable, observer);
    }
}
