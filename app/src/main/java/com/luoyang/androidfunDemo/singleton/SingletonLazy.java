package com.luoyang.androidfunDemo.singleton;

/**
 * 懒汉式
 *
 * @author luoyang
 * @date 2022/8/30
 */
public class SingletonLazy {
    private static SingletonLazy INSTANCE;

    private SingletonLazy() {

    }

    public static SingletonLazy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonLazy();
        }
        return INSTANCE;
    }
}
