package com.luoyang.androidfunDemo.fragment

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.compose.SettingsAboutActivity
import com.luoyang.androidfunDemo.activity.ShowProviderActivity
import com.luoyang.androidfunDemo.adapter.ManageContentAdapter
import com.luoyang.androidfunDemo.bean.MineItemBean
import com.luoyang.androidfunDemo.compose.list.ComposeListActivity
import com.luoyang.androidfunDemo.ipaynowdemo.PayTestActivity
import com.luoyang.androidfunDemo.launch.LaunchTestActivity
import com.luoyang.androidfunDemo.livedata.LiveDataActivity
import com.luoyang.androidfunDemo.mvvm.view.MvvmActivity
import com.luoyang.androidfunDemo.retrofit.show.HttpURLActivity
import com.luoyang.androidfunDemo.retrofit.show.okhttp.OkhttpActivity
import com.luoyang.base.base.BaseFragment

/**
 * 我的页面
 *
 * @author luoyang
 * @date 2022/7/15
 */
class MineFragment : BaseFragment() {

    private lateinit var mRecycler: RecyclerView

    companion object {
        private var TAG = "MineFragment"
    }


    override fun initView() {
        mRecycler = findView(R.id.manage_recycler_content) as RecyclerView
        initContentData()
    }

    private fun initContentData() {
        Log.d(TAG, "initContentData");
        val dataList = ArrayList<MineItemBean>()
        var data = MineItemBean(
            "Compose",
            R.drawable.icon_manage_settings,
            mActivity,
            SettingsAboutActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean(
            "ContentResolver",
            R.drawable.icon_suggest_feedback,
            mActivity,
            ShowProviderActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean(
            "HttpURL",
            R.drawable.icon_my_welfare,
            mActivity,
            HttpURLActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean(
            "Okhttp_Retrofit",
            R.drawable.icon_my_welfare,
            mActivity,
            OkhttpActivity::class.java
        )
        dataList.add(data)


        data = MineItemBean(
            "ComposeList",
            R.drawable.icon_my_coupons,
            mActivity,
            ComposeListActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean(
            "launch",
            R.drawable.icon_my_coupons,
            mActivity,
            LaunchTestActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean("mvvm", R.drawable.icon_my_coupons, mActivity, MvvmActivity::class.java)
        dataList.add(data)

        data = MineItemBean(
            "livedata",
            R.drawable.icon_my_coupons,
            mActivity,
            LiveDataActivity::class.java
        )
        dataList.add(data)

        data = MineItemBean(
            "now_pay_test",
            R.drawable.icon_my_coupons,
            mActivity,
            PayTestActivity::class.java
        )
        dataList.add(data)

        var manageContentAdapter = ManageContentAdapter(context, dataList)
        mRecycler.layoutManager = LinearLayoutManager(mActivity)
        mRecycler.adapter = manageContentAdapter

    }

    override fun provideContentViewId(): Int {
        return R.layout.mine_fragment
    }

    override fun loadData() {
//        TODO("Not yet implemented")
    }
}