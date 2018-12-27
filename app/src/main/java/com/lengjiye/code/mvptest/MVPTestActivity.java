package com.lengjiye.code.mvptest;


import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.lengjiye.code.R;
import com.lengjiye.code.mvptest.bean.PlayHomeFeedData;
import com.lengjiye.code.mvptest.contract.MvpTestContract;
import com.lengjiye.code.mvptest.presenter.MvpTestPresenter;
import com.lengjiye.tools.LogTool;

/**
 * 通知栏activity
 */
public class MVPTestActivity extends BasicMvpActivity<MvpTestContract.View, MvpTestContract.Presenter>
        implements MvpTestContract.View {

    private TextView textView;

    @Override
    public int getResourceId() {
        return R.layout.activity_mvp_test;
    }

    @Override
    protected void initViews() {
        super.initViews();
        textView = findViewById(R.id.text);
    }

    @Override
    public MvpTestContract.Presenter createPresenter() {
        return new MvpTestPresenter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button:
                getPresenter().getData();
                break;
        }
    }

    @Override
    public boolean isAlived() {
        return false;
    }

    @Override
    public void getDataSuc(PlayHomeFeedData o) {
        LogTool.e("o:" + o);
        textView.setText(Html.fromHtml(String.valueOf(o)));
    }

    @Override
    public void getDataFail() {

    }
}
