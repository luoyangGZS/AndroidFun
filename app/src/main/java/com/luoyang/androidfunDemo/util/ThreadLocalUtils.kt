package com.luoyang.androidfunDemo.util

import java.lang.ThreadLocal.withInitial
import java.util.function.Supplier


/**
 *
 *
 * @author luoyang
 * @date 2022/8/28
 */
object ThreadLocalUtils {
    public var threadLocal: ThreadLocal<Int> = withInitial(Supplier {
        println("调用get()方法，当前线程共享变量未设置，自动调用initialValue()方法")
//        Random(100)
        0
    })
}