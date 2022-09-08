package com.luoyang.androidfunDemo.compose.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * 关于我们页view的状态
 * showDialog-是否展示Dialog
 * changeText-在TextField中展示内容
 * changeValue-bar里展示的下载数量
 * countClick-图标的点击次数
 *
 * @author luoyang
 * @date 2022/7/17
 */
data class AboutUIState(
    var showDialog: MutableState<Boolean> = mutableStateOf(false),
    var changeText: MutableState<String> = mutableStateOf(""),
    var changeValue: MutableState<String> = mutableStateOf(""),
    var countClick: MutableState<Int> = mutableStateOf(0)
)
