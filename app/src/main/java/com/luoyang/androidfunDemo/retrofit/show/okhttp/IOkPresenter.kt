package com.luoyang.androidfunDemo.retrofit.show.okhttp

/**
 *
 *
 * @author luoyang
 * @date 2022/7/24
 */
interface IOkPresenter {

    fun loadData(query: String)

    fun onRefreshData()

    fun loadMoreData()
}