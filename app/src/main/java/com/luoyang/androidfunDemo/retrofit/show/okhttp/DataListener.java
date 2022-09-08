package com.luoyang.androidfunDemo.retrofit.show.okhttp;


import com.luoyang.androidfunDemo.bean.BaseHttpResult;

/**
 * @author luoyang
 */
public interface DataListener<T> {
    void onLoadSuccess(BaseHttpResult t, boolean isCache);

    void onLoadError(int code, String msg);

}
