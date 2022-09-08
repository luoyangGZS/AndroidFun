package com.luoyang.base.base

import android.app.Application
import android.content.Context
import android.util.Log
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 *
 *
 * @author luoyang
 * @date 2022/7/20
 */
object UMConfigUtil {

    private const val TAG = "UMConfigUtil"

    /**
     * 初始化友盟统计
     * 需要在Application 的onCreate 使用
     *
     * @param application
     */
    fun initUMConfigure(application: Application) {
        Log.d(TAG, "initUMConfigure $application")
        UMConfigure.init(
            application,
            "62d7d8e288ccdf4b7ed8c639",
            "Umeng_fun_Demo",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        //设置组件化的Log开关  boolean 默认为false，如需查看LOG设置为true
//        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        UMConfigure.setLogEnabled(true)
        //统计的配置,自动采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        //interval 单位为毫秒，如果想设定为40秒，interval应为 40*1000. Session定义30s
        MobclickAgent.setSessionContinueMillis((30 * 1000).toLong())
        //当用户使用自有账号登录时，可以这样统计：
        //MobclickAgent.onProfileSignIn("userID","账号来源");
        //支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true)
    }

    /**
     * 友盟统计，代理onResume
     *
     * @param context
     */
    fun mobClickAgentOnResume(context: Context?) {
        Log.d(TAG, "mobClickAgentOnResume context $context")
        MobclickAgent.onResume(context)
    }

    /**
     * 友盟统计，代理onPause
     *
     * @param context
     */
    fun mobClickAgentOnPause(context: Context?) {
        Log.d(TAG, "mobClickAgentOnResume")
        MobclickAgent.onPause(context)
    }
}