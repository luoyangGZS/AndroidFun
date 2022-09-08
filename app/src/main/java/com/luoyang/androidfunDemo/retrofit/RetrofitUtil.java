package com.luoyang.androidfunDemo.retrofit;


import com.luoyang.androidfunDemo.retrofit.api.Api;
import com.luoyang.androidfunDemo.retrofit.api.ApiService;
import com.luoyang.androidfunDemo.retrofit.api.ApiService1;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2.0工具
 */
public class RetrofitUtil {
    private static volatile Retrofit sRetrofit;
    private static ApiService1 mApiService;

    public static void initRetrofit(OkHttpClient sClient) {
        if (sRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .client(sClient)
                            .baseUrl(Api.BASE_API)
                            .addConverterFactory(StringConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
    }

    /**
     * 获取商店2.0通用ApiService
     */
    public static ApiService1 getApiService1() {
        if (sRetrofit == null) {
            return null;
        }
        if (mApiService == null) {
            mApiService = sRetrofit.create(ApiService1.class);
        }
        return mApiService;
    }
}
