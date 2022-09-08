package com.luoyang.base.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.luoyang.base.R;

public abstract class BaseActivity extends FragmentActivity {

    protected boolean mFlagNotFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!mFlagNotFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        entryOverridePendingTransition();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //友盟
        UMConfigUtil.INSTANCE.mobClickAgentOnResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟
        UMConfigUtil.INSTANCE.mobClickAgentOnResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void finish() {
        super.finish();
        exitOverridePendingTransition();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitOverridePendingTransition() {
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void entryOverridePendingTransition() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    /**
     * 点击了返回按钮时的回调方法
     */
    public void back() {
        finish();
    }

}
