package com.luoyang.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

class SkyIndicatorImpl : LinearLayout, SkyIndicator {
    // 默认圆点数
    private val DEFAULT_DOT_COUNT = 10

    // 默认位置
    private val DEFAULT_CURRENT_INDEX = 0

    //圆点总数数
    private var mDotCounts: Int = DEFAULT_DOT_COUNT
    private var curIndex: Int = DEFAULT_CURRENT_INDEX

    private lateinit var mLlDots: LinearLayout
    private lateinit var inflater: LayoutInflater

    constructor(context: Context) : super(context) {
        initAttr(null, context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        initAttr(attrs, context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttr(attrs, context)
    }

    override fun initIndicatorCount(dotCounts: Int, currentIndex: Int) {
        this.mDotCounts = dotCounts
        // 包含0不包含100
        if (curIndex in 0 until mDotCounts) {
            curIndex = currentIndex
        }
        visibility = if (dotCounts > 1) VISIBLE else GONE
        setDotLayout()
    }


    private fun initAttr(attrs: AttributeSet?, context: Context) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.SkyIndicatorView)
        mDotCounts = attr.getInt(R.styleable.SkyIndicatorView_indicatorCount, DEFAULT_DOT_COUNT)
        curIndex = attr.getInt(R.styleable.SkyIndicatorView_currentPage, DEFAULT_CURRENT_INDEX)

        inflater = LayoutInflater.from(context)
        var contentView = inflater.inflate(R.layout.dot_scroll_layout, this, true);
        mLlDots = contentView.findViewById(R.id.ll_dots)
        setDotLayout()
    }

    /**
     * 设置圆点
     */
    private fun setDotLayout() {
        mLlDots.removeAllViews()
        for (i in 0 until mDotCounts) {
            mLlDots.addView(inflater.inflate(R.layout.dotview, null))
        }
        // 默认显示第一页
        mLlDots.getChildAt(curIndex)?.isSelected = true
    }

    /**
     * 切换点，就是滑动点，也可以在这里面做炫酷的点切换动画
     */
    override fun onPageSelected(position: Int) {
        if (position in 0 until mDotCounts) {
            // 取消原点选中
            mLlDots.getChildAt(curIndex).isSelected = false
            // 原点选中
            mLlDots.getChildAt(position).isSelected = true
            curIndex = position
        } else {
            //异常点，数组越界
            throw IllegalArgumentException()
        }
    }

    /**
     * 更新点数量
     */
    override fun onUpdateIndicator(dotCounts: Int) {
        this.mDotCounts = dotCounts
        if (curIndex >= mDotCounts) {
            initIndicatorCount(mDotCounts, curIndex)
        } else {
            initIndicatorCount(mDotCounts, 0)
        }
    }
}