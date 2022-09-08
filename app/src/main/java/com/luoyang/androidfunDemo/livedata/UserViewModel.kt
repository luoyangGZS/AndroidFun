package com.luoyang.androidfunDemo.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 *
 * @author luoyang
 * @date 2022/9/4
 */
object UserViewModel : ViewModel() {
    val mUserMutableLiveData = MutableLiveData<TestUserBean>()
}