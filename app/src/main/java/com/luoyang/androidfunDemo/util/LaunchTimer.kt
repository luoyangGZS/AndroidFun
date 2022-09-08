package com.luoyang.androidfunDemo.util

import android.util.Log

/**
 *
 *
 * @author luoyang
 * @date 2022/9/4
 */
object LaunchTimer {
    var mTime: Long = 0;

    fun startRecord() {
        mTime = System.currentTimeMillis()
    }

    fun endRecord(message: String) {
       val cost = System.currentTimeMillis()- mTime
        Log.i(message,"launchTimer cost: $cost")
    }

}