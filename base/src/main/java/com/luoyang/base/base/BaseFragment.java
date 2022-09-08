package com.luoyang.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;

public abstract class BaseFragment extends LazyLoadFragment {

    private View rootView;
    protected BaseActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            initView(rootView);
            initView();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    protected abstract int provideContentViewId();

    protected abstract void loadData();


    public abstract void initView();

    public void initListener() {
    }


    /**
     * 通过View的ID  查找 view
     *
     * @param resId
     * @return
     */
    protected View findView(@IdRes int resId) {
        return rootView.findViewById(resId);
    }

}
