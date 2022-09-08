//*************************************************************************/
/*
/* Copyright (c) 2000-2022 Coolpad Incorporated and its licensors.
/* All rights reserved.
/* 酷派集团及其附属公司 版权所有 2000-2022
/*
/* PROPRIETARY RIGHTS of Coolpad Incorporated are involved in the
/* subject matter of this material. All manufacturing, reproduction, use,
/* and sales rights pertaining to this subject matter are governed by the
/* license agreement. The recipient of this software implicitly accepts
/* the terms of the license.
/* 本软件文档资料是酷派集团公司的资产，任何人士阅读和使用本资料必须获得
/* 相应的书面授权，承担保密责任和接受相应的法律约束。
/* *************************************************************************/

package com.luoyang.indicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lixiongjun
 * @date 2021/07/01
 */
public abstract class MyViewPagerAdapter<T> extends PagerAdapter {

    private final List<T> mDataList = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final int mCoverId;

    /**
     * 需求变更，从无限横滑-> 正常显示
     * @return
     */
    @Override
    public int getCount() {
        return getRealCount();
    }

    public int getRealCount() {
        return mDataList.size();
    }

    public MyViewPagerAdapter(Context context, int convertId) {
        this.mCoverId = convertId;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % getRealCount();
        View view = getView(container, realPosition);
        container.addView(view);
        bindView(view, mDataList.get(realPosition), position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(getView(container, position));
    }

    public View getView(ViewGroup container, int position) {
        int realPosition = position % getRealCount();
        View view = mLayoutInflater.inflate(mCoverId, container, false);
        view.setTag(realPosition);
        return view;
    }

    public void setData(List<T> dataList) {
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public abstract void bindView(View view, T data, int position);

}
