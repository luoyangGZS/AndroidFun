package com.luoyang.androidfunDemo.singleton;

/**
 * 懒汉式单例
 *
 * @author luoyang
 * @date 2022/8/30
 */
public class SingletonHungry {
    private static SingletonHungry INSTANCE = new SingletonHungry();

    private SingletonHungry() {
    }

    public static SingletonHungry getInstance() {
        return INSTANCE;
    }
}

