package com.luoyang.androidfunDemo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luoyang
 * @date 2022/10/8
 */
public class ThreadPoolUtils {

    private static class ThreadPoolSingle {
        public static ExecutorService mThreadPool = Executors.newCachedThreadPool();
    }

    private static ExecutorService getThreadPool() {
        return ThreadPoolSingle.mThreadPool;
    }

    public static void post(Runnable task) {
        try {
            getThreadPool().execute(task);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }
}
