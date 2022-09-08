
package com.luoyang.base.exposure

import androidx.core.util.Pools
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClassifier

/**
 * Manage simple pools for small objects
 */
object ObjectPools : ConcurrentHashMap<KClassifier, Any>() {
    /**
     * Acquire and release an object from it's pool.
     */
    inline fun <reified T: Any> use(defaultFactory: () -> T, block: (T) -> Unit) {
        val pool = getOrPut(T::class) { Pools.SimplePool<T>(20) } as Pools.Pool<T>
        val instance = pool.acquire() ?: defaultFactory()
        block(instance)
        pool.release(instance)
    }
}