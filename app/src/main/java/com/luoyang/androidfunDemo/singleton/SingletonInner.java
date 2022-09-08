package com.luoyang.androidfunDemo.singleton;

/**
 * 静态内部类单例
 * @author luoyang
 * @date 2022/8/30
 */
public class SingletonInner {
    private SingletonInner() {
    }

    private static class Inner {
        public static SingletonInner INSTANCE = new SingletonInner();
    }

    public static SingletonInner getInstance() {
        return Inner.INSTANCE;
    }

}
