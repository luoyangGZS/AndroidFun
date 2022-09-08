package com.luoyang.androidfunDemo.compose.data

enum class ListType(val jumpType: Int) {
    /**
     * 官网跳转
     */
    OFFICIAL_JUMP(0),

    /**
     * 剪切提示
     */
    CUT_TIPS(1),

    /**
     * 网页跳转
     */
    WEB_JUMP(2)

}