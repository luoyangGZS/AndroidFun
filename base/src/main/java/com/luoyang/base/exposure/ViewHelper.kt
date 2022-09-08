
package com.luoyang.base.exposure

import android.view.View

fun View.setEventOnExposure(
    key: Int = hashCode(),
    exposureInterval: Long = ViewExposureConfig.exposureInterval,
    minExposureVisibleArea: Float = ViewExposureConfig.minExposureVisibleArea,
    minExposureVisibleDuration: Long = ViewExposureConfig.minExposureVisibleDuration,
    provider: () -> Unit
) {
    setOnExposureListener(
        key = key,
        exposureInterval = exposureInterval,
        minExposureVisibleArea = minExposureVisibleArea,
        minExposureVisibleDuration = minExposureVisibleDuration
    ) {
        provider()
    }
}