package com.lengjiye.code.activity;


import android.view.View;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.lengjiye.code.R;

/**
 * 方法测试
 */
public class MethodTestActivity extends BasicMvpActivity {

    @Override
    public int getResourceId() {
        return R.layout.activity_webp_animation;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    public boolean isAlived() {
        return false;
    }


}
