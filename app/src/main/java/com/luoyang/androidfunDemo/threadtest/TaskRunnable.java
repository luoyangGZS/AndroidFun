package com.luoyang.androidfunDemo.threadtest;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author luoyang
 * @date 2022/8/28
 */
public class TaskRunnable implements Runnable {

    private static final String TAG ="TaskRunnable";
    private String name;

    public TaskRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 6; i++) {
            // ThreadLocal.get方法获取本地线程变量
            if (null == ThreadLocalUtil.threadLocal.get()) {
                Random random = new Random();
                ThreadLocalUtil.threadLocal.set(random.nextInt(100));

                int num = (Integer) ThreadLocalUtil.threadLocal.get();
                String log = "线程:【" + name + "】当前值: " + num;
                System.out.println(log);
                Log.d(TAG,log);

                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                continue;

            }

            int num = (Integer) ThreadLocalUtil.threadLocal.get();
            num += 1;
            ThreadLocalUtil.threadLocal.set(num);
            String log = "线程:【" + name + "】当前值: " + num;
            System.out.println(log);
            Log.d(TAG,log);
            if (i == 3) {
                ThreadLocalUtil.threadLocal.remove();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
