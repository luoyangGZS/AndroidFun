package com.luoyang.androidfunDemo.retrofit.show.okhttp

import com.luoyang.androidfunDemo.bean.ItemBean

/**
 *
 *
 * @author luoyang
 * @date 2022/7/24
 */
interface IOkView {
    fun loadOrderSuccess(list: List<ItemBean>, hasMore: Boolean, isCache: Boolean)
}