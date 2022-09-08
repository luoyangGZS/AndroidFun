package com.luoyang.androidfunDemo.launch

import android.util.Log
import androidx.lifecycle.*

/**
 *
 *
 * @author luoyang
 * @date 2022/9/4
 */
class MyLifecycleObserver : DefaultLifecycleObserver {

    companion object {
        const val TAG = "MyLifecycleObserver"
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "MyLifecycleObserver onCreate $owner")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "MyLifecycleObserver onStart $owner")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "MyLifecycleObserver onResume $owner")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "MyLifecycleObserver onPause $owner")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "MyLifecycleObserver onStop $owner")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "MyLifecycleObserver onDestroy $owner")
    }
}