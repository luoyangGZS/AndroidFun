package com.luoyang.androidfunDemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.luoyang.androidfunDemo.retrofit.NetInterceptor;
import com.luoyang.androidfunDemo.retrofit.RetrofitFactory;
import com.luoyang.androidfunDemo.retrofit.RetrofitUtil;
import com.luoyang.androidfunDemo.retrofit.rerquest.RequestManager;
import com.luoyang.androidfunDemo.util.LaunchTimer;
import com.luoyang.androidfunDemo.util.ThreadPoolUtil;
import com.luoyang.base.base.UMConfigUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author luoyang
 * @date 2022/7/14
 */
public class MainApplication extends Application {
    private static Context mContext;
    public Handler mMainHandler = new Handler(msg -> {
        if (msg.obj instanceof Runnable) {
            ThreadPoolUtil.post((Runnable) msg.obj);
        }
        return false;
    });

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        UMConfigUtil.INSTANCE.initUMConfigure(this);
        initOkHttp();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchTimer.INSTANCE.startRecord();
    }

    /**
     * 初始化tOkHttp
     */
    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(Constant.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constant.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new NetInterceptor(this, true, true))
                //https 证书信任
                .hostnameVerifier((hostname, session) -> true)
                .build();
        okHttpClient.dispatcher().setMaxRequests(Constant.MAX_REQUESTS);
        okHttpClient.dispatcher().setMaxRequestsPerHost(Constant.MAX_REQUESTS_PER_HOST);
//        RequestManager.initClient(okHttpClient);
//        OkGo.getInstance().init(this).setOkHttpClient(okHttpClient);
        //init Retrofit
//        RetrofitFactory.INSTANCE.initRetrofit(okHttpClient);
        RetrofitUtil.initRetrofit(okHttpClient);
    }

    public static Context getInstance() {
        if (mContext == null) {
            try {
                Log.d("AIDLNoteManager", "MainApplication invoke");
                mContext = (Context) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null, (Object[]) null);
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
