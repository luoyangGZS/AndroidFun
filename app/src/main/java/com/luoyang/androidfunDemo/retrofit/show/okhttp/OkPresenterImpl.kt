package com.luoyang.androidfunDemo.retrofit.show.okhttp

import android.util.Log
import com.luoyang.androidfunDemo.bean.BaseHttpResult

/**
 *
 *
 * @author luoyang
 * @date 2022/7/24
 */
class OkPresenterImpl(val view: IOkView) : IOkPresenter {

    private var mModel: OkhttpModel = OkhttpModel()

    override fun loadData(query: String) {
        mModel.getSearchResultData(query, object : DataListener<BaseHttpResult> {
            override fun onLoadSuccess(t: BaseHttpResult?, isCache: Boolean) {
                Log.d(OkhttpModel.TAG, "onLoadSuccess $t")
                if (t != null) {
                    view.loadOrderSuccess(t.data, false, false)
                }
            }

            override fun onLoadError(code: Int, msg: String?) {
                Log.d(OkhttpModel.TAG, "onSubscribe code $code , msg $msg")
            }

        })
    }

    override fun onRefreshData() {

    }

    override fun loadMoreData() {

    }
}