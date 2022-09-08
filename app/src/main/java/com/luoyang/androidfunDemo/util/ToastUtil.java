package com.luoyang.androidfunDemo.util;

import android.os.Looper;
import android.widget.Toast;

import com.luoyang.androidfunDemo.MainApplication;

/**
 * @author luoyang
 * @date 2022/10/8
 */
public class ToastUtil {

    public static void toToast() {
        LogUtil.d("toToast");
        ThreadPoolUtils.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("toToast11111111111116");
                ThreadPoolUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d("toToast122222222226");
                        showShortToast("线程222222222222执行完成6");
                        LogUtil.d("toToast33333333333336");
                    }
                });
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showShortToast("线程11111111111执行完成6");
                LogUtil.d("toToast4444444444446");
            }
        });
    }

    public static void showShortToast(String msg) {
        ThreadPoolUtil.post(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(MainApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    public static void showLongToast(String msg) {
        ThreadPoolUtil.post(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(MainApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

}
