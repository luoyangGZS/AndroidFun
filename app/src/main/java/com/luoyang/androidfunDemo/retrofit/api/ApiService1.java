package com.luoyang.androidfunDemo.retrofit.api;

import com.luoyang.androidfunDemo.bean.BaseHttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author luoyang
 * @date 2022/9/8
 */
public interface ApiService1 {
    /**
     * 首页推荐推荐页面Tab
     */
    @GET(Api.SEARCH_SUGGEST_PATH)
    Observable<BaseHttpResult> getSearchSuggest(@Query("query") String query);
}
