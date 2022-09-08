package com.skyui.skydesign.indicator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.luoyang.indicator.PagerBean
import com.luoyang.indicator.R

class MyPagerAdapter(data: List<PagerBean>) : PagerAdapter() {
    private var mPagerBeans: List<PagerBean> = ArrayList()

    init {
        mPagerBeans = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mPagerBeans.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.item_pager, container, false)
        val textView: TextView = view.findViewById(R.id.pager_text) as TextView
        textView.setBackgroundColor(Color.parseColor(mPagerBeans[position].color))
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}