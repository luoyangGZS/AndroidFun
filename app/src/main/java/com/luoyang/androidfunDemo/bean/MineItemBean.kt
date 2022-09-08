package com.luoyang.androidfunDemo.bean

import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.Exception

/**
 *
 *
 * @author luoyang
 * @date 2022/7/15
 */
data class MineItemBean(
    var title: String,
    var imageId: Int,
    var context: Context,
    var activity: Class<*>
) {

    fun onClickItem() {
        try {
            val intent = Intent(context, activity);
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("MineItemBean", "Exception $e");
        }
    }

    //
//    private var mClickCallBack: ClickCallBack? = null
//
//    fun setClickCallBack(clickCallBack: ClickCallBack) {
//        mClickCallBack = clickCallBack
//    }


//    fun onClickItem() {
//        mClickCallBack?.onClick()
//    }

//    interface ClickCallBack {
//        fun onClick()
//    }
}