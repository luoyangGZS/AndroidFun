package com.luoyang.androidfunDemo.compose;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.luoyang.androidfunDemo.compose.theme.ComposeTheme
import com.luoyang.androidfunDemo.compose.ui.AboutView
import com.luoyang.androidfunDemo.compose.ui.ShowCustomDialog
import com.luoyang.androidfunDemo.compose.viewmodel.AboutViewModel
import com.luoyang.androidfunDemo.util.Utils
import com.luoyang.base.base.BaseActivity

/**
 * 关于我们页面
 *
 * @author lixiongjun
 * @date  2022/6/22
 */
class SettingsAboutActivity : BaseActivity() {

    private lateinit var mLocalBroadcastManager: LocalBroadcastManager
    private lateinit var mDownloadingNumReceiver: DownloadingNumReceiver

    private lateinit var mViewModel: AboutViewModel


    companion object {
        const val TAG: String = "SettingsAboutActivity"

        @JvmStatic
        fun actionStart(context: Context) {
            val intent = Intent(context, SettingsAboutActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Composable 根源view,一般用来设置主题
            ComposeTheme() {
                // data
                mViewModel = AboutViewModel()
//                initData()

                //view
                AboutView(this, mViewModel, goBack = { goBack() })
                ShowCustomDialog(mViewModel, this)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
//        mLocalBroadcastManager.unregisterReceiver(mDownloadingNumReceiver)
    }

    private fun initData() {
        // 下载数量获取
//        val num = PreferenceManager.getInt(Constant.SP_DOWNLOADING_NUM, 0)
//        initRedPoint(num)
//        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this)
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(Constant.BROADCAST_DOWNLOADING_NUM)
//        mDownloadingNumReceiver = DownloadingNumReceiver()
//        mLocalBroadcastManager.registerReceiver(mDownloadingNumReceiver, intentFilter)
    }

    private fun goBack() {
        val act = Utils.scanForBaseActivity(this)
        act?.back()
    }


    inner class DownloadingNumReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
//            val num = PreferenceManager.getInt(Constant.SP_DOWNLOADING_NUM, 0)
//            Log.d(TAG, "onReceive num: $num")
//            initRedPoint(num)
        }
    }


}

