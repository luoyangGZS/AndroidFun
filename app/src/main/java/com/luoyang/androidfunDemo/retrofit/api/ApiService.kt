package com.luoyang.androidfunDemo.retrofit.api

import com.luoyang.androidfunDemo.bean.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 *
 * @author luoyang
 * @date 2022/7/23
 */
interface ApiService {

    /**
     * 首页推荐推荐页面Tab
     */
    @GET(Api.SEARCH_SUGGEST_PATH)
    fun getSearchSuggest(@Query("query") query: String): Observable<BaseHttpResult>
}