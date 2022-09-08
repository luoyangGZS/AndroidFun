package com.luoyang.androidfunDemo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.luoyang.base.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class MainTabAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments = new ArrayList<>();

    public MainTabAdapter(List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        if (fragmentList != null) {
            mFragments = fragmentList;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
