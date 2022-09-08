package com.luoyang.androidfunDemo.retrofit

import com.luoyang.androidfunDemo.retrofit.api.Api
import com.luoyang.androidfunDemo.retrofit.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *
 * @author luoyang
 * @date 2022/7/22
 */
object RetrofitFactory {

    @Volatile
    private var mRetrofit: Retrofit? = null
    private var mApiService: ApiService? = null


    fun initRetrofit(client: OkHttpClient) {
        if (mRetrofit == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder()
                        .client(client)
                        .baseUrl(Api.BASE_API)
                        .addConverterFactory(StringConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }
            }
        }
    }

    /**
     * 获取商店2.0通用ApiService
     */
    fun getApiService(): ApiService? {
        if (mRetrofit == null) {
            return null
        }
        if (mApiService == null) {
            mApiService = mRetrofit?.create(ApiService::class.java)
        }
        return mApiService
    }
}