
package com.luoyang.base.exposure

import android.os.SystemClock
import android.util.SparseLongArray
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

interface ExposureFilter {
    /**
     * Return true if we should report exposure this time.
     */
    fun onExposure(hashKey: Int, timeInterval: Long): Boolean
}

/**
 * Get a [ExposureFilter] related to a [LifecycleOwner]
 * The filter will be cleared when lifecycle destroyed.
 */
val LifecycleOwner.exposureFilter: ExposureFilter
    get() {
        return EXPOSURE_FILTERS.getOrPut(this) {
            newCounter(this)
        }
    }

private val EXPOSURE_FILTERS = mutableMapOf<LifecycleOwner, ExposureFilterImpl>()

private fun newCounter(lifecycleOwner: LifecycleOwner): ExposureFilterImpl {
    val lifecycle = lifecycleOwner.lifecycle
    val counter = ExposureFilterImpl()
    val observer = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                counter.clear()
                lifecycle.removeObserver(this)
                EXPOSURE_FILTERS.remove(lifecycleOwner)
            }
        }
    }
    lifecycle.addObserver(observer)
    return counter
}

/**
 * Filters exposure events.
 */
private class ExposureFilterImpl: ExposureFilter {
    //SparseArray 的内部是通过两个数组对数据进行存储的， key 为 int 类型数组， 而 value 为 Object 类型数组。
    // SparseArray 比 HashMap 更省内存、在某些条件下性能更好的主要原因是它避免了对 key 的自动装箱（int 转为 Integer 类型）
    private val mNextExposureTime = SparseLongArray(100)

    override fun onExposure(hashKey: Int, timeInterval: Long): Boolean {
        val currentTime = SystemClock.uptimeMillis()
        val result = mNextExposureTime.get(hashKey, 0) < currentTime
        mNextExposureTime.put(hashKey, currentTime + timeInterval)
        return result
    }

    fun clear() {
        mNextExposureTime.clear()
    }
}