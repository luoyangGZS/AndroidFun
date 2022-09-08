package com.luoyang.androidfunDemo.retrofit.show;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;


import com.luoyang.androidfunDemo.R;
import com.luoyang.androidfunDemo.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by limingde1 on 2017/6/27.
 */

public class SearchBar extends FrameLayout implements View.OnClickListener,
        TextView.OnEditorActionListener {
    public SearchBar(@NonNull Context context) {
        super(context);
        init();
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View mRootView;

    private ImageView mIvClear;
    private ImageView mIvLeft;
    private TextView mIvRight;
    private View mStateBar;
    private EditText mEditext;
    private FrameLayout mFrLeft;
    private FrameLayout mFrRight;

    private void init() {
        mRootView = Utils.inflate(getContext(), R.layout.search_bar);
        addView(mRootView);

        mStateBar = findViewById(R.id.view_state_bar);
        initStateBar();

        mIvLeft = findViewById(R.id.iv_back);
        mFrLeft = findViewById(R.id.fr_left);
        mFrLeft.setOnClickListener(this);

        mIvClear = findViewById(R.id.iv_clear);
        mIvClear.setOnClickListener(this);
        mIvRight = findViewById(R.id.iv_search);
        mFrRight = findViewById(R.id.fr_right);
        mFrRight.setOnClickListener(this);

        mEditext = findViewById(R.id.edit_search);

        mEditext.setOnEditorActionListener(this);
        mEditext.setFocusable(true);
        mEditext.setFocusableInTouchMode(true);
        mEditext.requestFocus();
        hideClear();
        showSoftInput();
    }

    public String getEditStr() {
        return mEditext.getText().toString();
    }

    public String getEditHint() {
        return mEditext.getHint().toString().trim();
    }

    public EditText getEditText() {
        return mEditext;
    }

    private void initStateBar() {
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                Utils.getStatusBarHeight(getContext()));
//        mStateBar.setLayoutParams(lp);
//
//        mStateBar.setBackgroundResource(R.color.color_primary_background);
    }

    public void setHint(@StringRes int titlResId) {
        setHint(Utils.getString(titlResId));
    }

    public void setHint(String title) {
        if (!TextUtils.isEmpty(title)) {
            mEditext.setHint(title);
        } else {
            mEditext.setHint("");
        }
    }


    public void clear() {
        mEditext.setText("");
    }

    public void setEditext(String title) {
        if (!TextUtils.isEmpty(title)) {
            mEditext.setText(title);
            mEditext.setSelection(title.length());
        } else {
            mEditext.setText("");
        }
    }

    private void showClear() {
        mIvClear.setVisibility(VISIBLE);
    }

    private void hideClear() {
        mIvClear.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fr_left:
                if (mCallBack != null) {
                    mCallBack.onBack();
                }
                break;
            case R.id.fr_right:
                if (mCallBack != null) {
                    mCallBack.onClickSearch(mEditext.getText().toString().trim());
                }
                break;
            case R.id.iv_clear:
                clear();
                if (mCallBack != null) {
                    mCallBack.onClear();
                }
                break;
        }
    }

    public void showSoftInput() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) mEditext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(mEditext, 0);
                           }

                       },
                500);
    }

    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEditext.getWindowToken(), 0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mCallBack != null) {
            return mCallBack.onEditorAction(v, actionId, event);
        }
        return false;
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack cb) {
        mCallBack = cb;
    }

    public interface CallBack {

        boolean onEditorAction(TextView v, int actionId, KeyEvent event);

        void onClear();

        void onClickSearch(String keyWord);

        void onBack();
    }
}
