package com.luoyang.androidfunDemo.webview;

import android.webkit.JavascriptInterface;

import com.luoyang.androidfunDemo.util.ToastUtil;

/**
 * @author luoyang
 * @date 2022/10/11
 */
public class AndroidToJs {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        String message = "JS调用了Android的hello方法";
        System.out.println(message);
        ToastUtil.showShortToast(message);
    }

}
