
package com.luoyang.base.exposure

import android.graphics.Rect
import android.text.format.DateUtils
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.luoyang.base.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


object ViewExposureConfig {
    /**
     * Time interval between a view can fire another exposure event.
     */
    var exposureInterval = 30 * DateUtils.SECOND_IN_MILLIS

    /**
     * The minimum visible area ratio to determine a [View] exposure.
     */
    var minExposureVisibleArea = 0.5f

    /**
     * The minimum visible duration to determine a [View] exposure.
     */
    var minExposureVisibleDuration = 1 * DateUtils.SECOND_IN_MILLIS
}

/**
 * Set an exposure listener on view.
 * @param key Used to check exposure interval.
 * Set the data item's hashCode() if in RecyclerView/ListView.
 */
fun View.setOnExposureListener(
    key: Int = hashCode(),
    exposureInterval: Long = ViewExposureConfig.exposureInterval,
    minExposureVisibleArea: Float = ViewExposureConfig.minExposureVisibleArea,
    minExposureVisibleDuration: Long = ViewExposureConfig.minExposureVisibleDuration,
    block: () -> Unit
) {
    val tag = getTag(R.id.id_attached_exposure_listener)
    if (tag is ViewExposureListener) {
        tag.onViewDetachedFromWindow(this)
        removeOnAttachStateChangeListener(tag)
    }

    val listener = ViewExposureListener(
        this, key, exposureInterval, minExposureVisibleArea, minExposureVisibleDuration, block
    )
    setTag(R.id.id_attached_exposure_listener, listener)
    addOnAttachStateChangeListener(listener)
}

private class ViewExposureListener(
    private val view: View,
    private val key: Int,
    private val exposureInterval: Long,
    private val minExposureVisibleArea: Float,
    private val minExposureVisibleDuration: Long,
    private val onExposure: () -> Unit
) : View.OnAttachStateChangeListener {
    private val mVisibleAreaListener = ViewVisibleAreaListener(view)
    private var mMonitorExposureJob: Job? = null

    init {
        if (view.isAttachedToWindow) {
            onViewAttachedToWindow(view)
        }
    }

    override fun onViewAttachedToWindow(v: View?) {
        view.viewTreeObserver.addOnDrawListener(mVisibleAreaListener)
        val lifecycleOwner = view.findViewTreeLifecycleOwner() ?: return
        mMonitorExposureJob = lifecycleOwner.lifecycleScope.launchWhenCreated {
            var triggerExposureJob: Job? = null
            mVisibleAreaListener.visibleAreaRatio
                .map { it >= minExposureVisibleArea.coerceIn(0.01f, 1f) }
                .distinctUntilChanged()
                .collect { exposure ->
                    if (exposure) {
                        triggerExposureJob = launch(Dispatchers.Main) {
                            delay(minExposureVisibleDuration)
                            if (lifecycleOwner.exposureFilter.onExposure(key, exposureInterval)) {
                                onExposure()
                            }
                        }
                    } else {
                        triggerExposureJob?.cancel()
                    }
                }
        }
    }

    override fun onViewDetachedFromWindow(v: View?) {
        view.viewTreeObserver.removeOnDrawListener(mVisibleAreaListener)
        mMonitorExposureJob?.cancel()
    }
}

private class ViewVisibleAreaListener(
    private val view: View
) : ViewTreeObserver.OnDrawListener {
    private val mVisibleAreaRatio = MutableStateFlow(0f)
    val visibleAreaRatio: Flow<Float> = mVisibleAreaRatio.asStateFlow()

    override fun onDraw() = ObjectPools.use({ Rect() }) { rect ->
        val visible = view.isShown && view.getGlobalVisibleRect(rect)
        val ratio = if (!visible) {
            0f
        } else {
            (rect.width() / view.width.toFloat()) * (rect.height() / view.height.toFloat())
        }
        mVisibleAreaRatio.value = ratio
    }
}