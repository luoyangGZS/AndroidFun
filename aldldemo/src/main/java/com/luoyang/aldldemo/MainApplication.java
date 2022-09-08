package com.luoyang.aldldemo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * @author luoyang
 * @date 2022/7/14
 */
public class MainApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getInstance() {
        if (mContext == null) {
            try {
                Log.d("AIDLNoteManager","MainApplication invoke");
                mContext = (Context) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null,(Object[])null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mContext;
    }
}
