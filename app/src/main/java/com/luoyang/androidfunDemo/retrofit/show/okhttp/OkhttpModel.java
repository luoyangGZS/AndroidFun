package com.luoyang.androidfunDemo.retrofit.show.okhttp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.luoyang.androidfunDemo.bean.BaseHttpResult;
import com.luoyang.androidfunDemo.bean.ItemBean;
import com.luoyang.androidfunDemo.retrofit.BaseObserver;
import com.luoyang.androidfunDemo.retrofit.RetrofitFactory;
import com.luoyang.androidfunDemo.retrofit.RetrofitUtil;
import com.luoyang.androidfunDemo.retrofit.api.ApiService;
import com.luoyang.androidfunDemo.retrofit.api.ApiService1;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author luoyang
 * @date 2022/9/7
 */
public class OkhttpModel {

    public static final String TAG = "OkhttpModel";
    private ApiService mApiService = RetrofitFactory.INSTANCE.getApiService();
    private ApiService1 mApiService1;

    public OkhttpModel() {
        mApiService1 = RetrofitUtil.getApiService1();
    }

    public void getSearchResultData(String keyword, DataListener<BaseHttpResult> listener) {
        if (listener == null) {
            return;
        }
        Log.d(TAG, "getSearchResultData: " + mApiService1.toString());
        Log.d(TAG, "getSearchResultData (mApiService1 == null) : " + (mApiService1 == null));
        Log.d(TAG, "getSearchResultData keyword : " + keyword);
        Log.d(TAG, "getSearchResultData listener : " + listener);
        Log.d(TAG, "getSearchResultData (listener == null) : " + (listener == null));
//        mApiService.getSearchSuggest(keyword)
        mApiService1.getSearchSuggest(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseHttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(BaseHttpResult result) {
                        Log.d(TAG, "onNext: " + result.toString());
                        if (result.isSuccess()) {
                            listener.onLoadSuccess(result, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }
}
