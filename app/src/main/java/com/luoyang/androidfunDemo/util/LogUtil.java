package com.luoyang.androidfunDemo.util;

import android.util.Log;

/**
 * @author luoyang
 * @date 2022/10/8
 */
public class LogUtil {
    private static boolean isDebug = true;
    private final static String TAG = "lixiongjun";

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}
