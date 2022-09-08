package com.luoyang.androidfunDemo.fragment;

import android.widget.TextView;

import com.luoyang.androidfunDemo.R;
import com.luoyang.base.base.BaseFragment;

/**
 * 首页
 *
 * @author luoyang
 * @date 2022/7/9
 */
public class HomeFragment extends BaseFragment {

    private String mText = "home ";
    private String mTitle;
    private TextView mTextView;

    public HomeFragment(String title) {
        mTitle = title;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void loadData() {
        mText = "你好，安卓，home " + mTitle;
    }

    @Override
    public void initView() {

        mTextView = (TextView) findView(R.id.test_text);
        mTextView.setText(mText + mTitle);

    }
}
