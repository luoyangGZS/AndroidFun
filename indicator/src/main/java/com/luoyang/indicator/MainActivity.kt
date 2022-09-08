package com.luoyang.indicator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import java.util.*

/**
 * 点插件的使用展示
 *
 */
class MainActivity : AppCompatActivity(), OnPageChangeListener {
    private lateinit var mIndicatorView: SkyIndicatorImpl
    private lateinit var mPagerView: ViewPager

    //    private var mPagerAdapter =
//        object : MyViewPagerAdapter<PagerBean?>(
//            this,
//            R.layout.item_pager
//        ) {
//            override fun bindView(view: View?, data: PagerBean?, position: Int) {
//                val textView: TextView = view?.findViewById(R.id.pager_text) as TextView
//                textView.setBackgroundColor(Color.parseColor(data?.color))
//            }
//        }
    private var mCurrentIndex = 3
    private var mDefaultCounts = 7
    private var mPagerBeans = ArrayList<PagerBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPagerData(16) }

    private fun initView() {
        mIndicatorView = findViewById(R.id.sky_indicator)
        mPagerView = findViewById(R.id.viewpager)
        mIndicatorView.initIndicatorCount(mDefaultCounts, mCurrentIndex)
    }

    /**
     * 通过viewPager展示点控件的滑动
     */
    private fun initPagerData(page: Int) {
        mPagerBeans.clear()
        for (i in 0 until page) {
            var colorStr: String = getRandomColor()
            mPagerBeans.add(PagerBean(colorStr))
        }
        var mPagerAdapter =
            object : MyViewPagerAdapter<PagerBean?>(
                this,
                R.layout.item_pager
            ) {
                override fun bindView(view: View?, data: PagerBean?, position: Int) {
                    val textView: TextView = view?.findViewById(R.id.pager_text) as TextView
                    textView.setText("我是颜色值${data?.color}, 共${mPagerBeans.size}页，我在第${position}页")
                    try {
                        var colorInt = Color.parseColor(data?.color)
                        textView.setBackgroundColor(colorInt)
                    } catch (e: Exception) {
                        var colorInt = Color.parseColor("#FF0000")
                        textView.setBackgroundColor(colorInt)
                        textView.setText("抱歉，我是异常的颜色值${data?.color}, 共${mPagerBeans.size}页，我在第${position}页")
                    }
                }
            }
        mPagerAdapter.setData(mPagerBeans as List<PagerBean?>?)
        mPagerView.adapter = mPagerAdapter
        mPagerView.setOnPageChangeListener(this)

        //Viewpager绑定点控件
        mIndicatorView.onUpdateIndicator(mPagerBeans.size)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mCurrentIndex = position
        mIndicatorView.onPageSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    //随机color值
    private fun getRandomColor(): String {
        val random = Random()
        val ranColor = random.nextInt(0xffffff)
        return "#" + Integer.toHexString(ranColor)
    }

    /**
     * 通过Button点击，展示点控件的滑动
     */
    fun buttonClick(view: View) {
        mCurrentIndex += 1
        var countTotal: Int = mDefaultCounts
        if (mPagerBeans.size != 0) {
            countTotal = mPagerBeans.size
        }
        var index = (mCurrentIndex) % countTotal
        mIndicatorView.onPageSelected(index)
    }

    /**
     * 通过Button点击，展示点控件的滑动
     */
    fun buttonChangePagerViewData(view: View) {
        val random = Random()
        val ran = random.nextInt(30)
        initPagerData(ran)
    }

}