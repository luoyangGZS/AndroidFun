package com.luoyang.androidfunDemo.activity

import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.adapter.MainTabAdapter
import com.luoyang.androidfunDemo.animation.AnimationFragment
import com.luoyang.androidfunDemo.fragment.HomeFragment
import com.luoyang.androidfunDemo.fragment.MineFragment
import com.luoyang.androidfunDemo.ui.BottomBarLayout
import com.luoyang.androidfunDemo.util.LaunchTimer
import com.luoyang.androidfunDemo.util.ToastUtil
import com.luoyang.androidfunDemo.util.Utils
import com.luoyang.base.base.BaseActivity
import com.luoyang.base.base.BaseFragment
import com.luoyang.note.NoteFragment

/**
 *
 * 主页
 */
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    /**
     * view
     */
    private lateinit var mBottomBarLayout: BottomBarLayout

    /**
     * data
     */
    private lateinit var mFragments: ArrayList<BaseFragment>
    private lateinit var mTabAdapter: MainTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        LaunchTimer.endRecord("launchTimer")
    }

    private fun initView() {
        val vpContent: ViewPager = findViewById(R.id.vp_content)
        mBottomBarLayout = findViewById(R.id.bottom_bar)
        mFragments = ArrayList(4)
        mFragments.add(HomeFragment("第1页"))
        mFragments.add(AnimationFragment("第2页"))
        mFragments.add(NoteFragment())
        mFragments.add(MineFragment())
        mTabAdapter = MainTabAdapter(mFragments, supportFragmentManager)
        vpContent.adapter = mTabAdapter
        vpContent.offscreenPageLimit = mFragments.size
        mBottomBarLayout.setViewPager(vpContent)

        mBottomBarLayout.currentItem = 3
        //设置条目点击的监听
        mBottomBarLayout.setOnItemSelectedListener { bottomBarItem, previousPosition, currentPosition ->
            Log.i(
                TAG,
                "previousPosition--$previousPosition--currentPosition--$currentPosition--bottomBarItem--$bottomBarItem"
            )
        }
//        ThreadPoolUtil.post(TaskRunnable("1111111"))
//        ThreadPoolUtil.post(TaskRunnable("222222"))

        ToastUtil.toToast();

    }
}