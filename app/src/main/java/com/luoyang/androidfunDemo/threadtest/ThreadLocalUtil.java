package com.luoyang.androidfunDemo.threadtest;

/**
 * @author luoyang
 * @date 2022/8/28
 */

import java.util.Random;

public class ThreadLocalUtil {

    public static final ThreadLocal<Object> threadLocal = ThreadLocal.withInitial(() -> {
        System.out.println("调用get()方法，当前线程共享变量未设置，自动调用initialValue()方法");
//            return new Random().nextInt(100);
        return null;
    });

}