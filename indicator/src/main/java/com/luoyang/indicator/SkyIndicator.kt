package com.luoyang.indicator

interface SkyIndicator {
    /**
     * 当数据初始化完成时调用
     *
     * @param dotCount 点数量
     */
    fun initIndicatorCount(dotCounts: Int, currentIndex: Int)


    /**
     * 切换点（滑动）
     *
     * @param position 切换的位置
     */
    fun onPageSelected(position: Int)

    /**
     * 数据刷新，点长度和默认点刷新、
     *
     * @param dotCounts 更新的数据量
     */
    fun onUpdateIndicator(dotCounts: Int)

}