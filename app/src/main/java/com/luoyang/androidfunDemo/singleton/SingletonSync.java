package com.luoyang.androidfunDemo.singleton;

import android.content.Context;

/**
 * 双重检查锁单例
 *
 * @author luoyang
 * @date 2022/8/30
 */
public class SingletonSync {
    private static SingletonSync INSTANCE;
    private Context mContext;

    private SingletonSync(Context context) {
        mContext = context;
    }

    public static SingletonSync getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SingletonSync.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonSync(context);
                }
            }
        }
        return INSTANCE;
    }
}
