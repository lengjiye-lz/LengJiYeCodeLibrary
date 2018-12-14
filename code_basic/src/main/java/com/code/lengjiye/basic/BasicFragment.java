package com.code.lengjiye.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment 基类
 * 创建人: lz
 * 创建时间: 2018/12/13
 * 修改备注:
 */
public abstract class BasicFragment extends Fragment implements View.OnClickListener {

    protected boolean mIsViewCreated = false;
    protected boolean mIsVisibleToUser = false;
    protected boolean mIsFirstLoad = false;

    protected View layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(getResourceId(), container, false);
        initViews();
        return layout;
    }

    /**
     * 初始化View
     */
    protected void initViews() {
    }

    /**
     * 请求网络，数据处理写在这
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsViewCreated = true;
        initDates();
        //懒加载模式下生效
        if (mIsVisibleToUser) {
            firstLoad();
        }
    }

    /**
     * 初始化数据
     */
    protected void initDates() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewCreated = false;
        mIsFirstLoad = false;
        mIsVisibleToUser = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        //懒加载模式下生效
//        if (mIsViewCreated && mIsFirstLoad) {
//            onHiddenChanged(!isVisibleToUser);
//        }
        //懒加载模式下生效
        if (mIsVisibleToUser && mIsViewCreated) {
            firstLoad();
        }
    }

    /**
     * 懒加载模式下生效
     */
    private void firstLoad() {
        if (mIsFirstLoad) {
            return;
        }
        mIsFirstLoad = true;
        onFirstLoaded();
    }

    /**
     * 懒加载模式下生效
     * <p>
     * 只有在页面第一次可见的时候会调用
     */
    public void onFirstLoaded() {
    }

    /**
     * 获取BaseActivity
     *
     * @return
     */
    public BasicActivity getBaseActivity() {
        if (getActivity() instanceof BasicActivity) {
            return ((BasicActivity) getActivity());
        }
        return null;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getResourceId();

    /**
     * 根据Id获取控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected final <T extends View> T findViewById(int id) {
        return layout.findViewById(id);
    }

    /**
     * 设置点击事件
     *
     * @param views
     */
    protected void setOnClickListener(View... views) {
        if (views == null || views.length <= 0) {
            return;
        }

        for (View v : views) {
            if (v == null)
                continue;
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
    }

}
