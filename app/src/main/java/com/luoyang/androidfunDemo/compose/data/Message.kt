package com.luoyang.androidfunDemo.compose.data

import androidx.annotation.StringRes

data class Message(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val type: ListType,
    val jumpData: String
)