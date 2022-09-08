package com.luoyang.androidfunDemo.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.luoyang.androidfunDemo.R;
import com.luoyang.androidfunDemo.util.LogUtil;
import com.luoyang.androidfunDemo.util.ToastUtil;

import java.util.HashMap;
import java.util.Set;

/**
 * @author luoyang
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mJsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initData();
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        mJsButton = findViewById(R.id.js_button);
    }

    private void initData(){

//        mWebView.loadUrl("http://www.baidu.com/");
//        //设置不用系统浏览器打开,直接显示在当前Webview，不设置无效
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/web.html");



        mJsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        // 注意调用的JS方法名要对应上
                        //第一种方法 通过WebView的loadUrl（）
                        // 调用javascript(不是方法名)的callJS()方法
//                        mWebView.loadUrl("javascript:callJS()");

                        //第二种方法 通过WebView的evaluateJavascript（）
                        // 只需要将第一种方法的loadUrl()换成下面该方法即可
                        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                              //  Toast.makeText(this, "android 调用js返回value:"+value, Toast.LENGTH_SHORT).show();
                                LogUtil.e("android 调用js返回value:"+value);
                            }
                        });
                    }
                });

            }
        });

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebView.addJavascriptInterface(new AndroidToJs(), "test");//AndroidtoJS类对象映射到js的test对象

        // 复写WebViewClient类的shouldOverrideUrlLoading方法
        mWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {

                                          // 步骤2：根据协议的参数，判断是否是所需要的url
                                          // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                                          //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                                          Uri uri = Uri.parse(url);
                                          // 如果url的协议 = 预先约定的 js 协议
                                          // 就解析往下解析参数
                                          if ( uri.getScheme().equals("js")) {

                                              // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                                              // 所以拦截url,下面JS开始调用Android需要的方法
                                              if (uri.getAuthority().equals("webview")) {

                                                  //  步骤3：
                                                  // 执行JS所需要调用的逻辑
                                                  System.out.println("shouldOverrideUrlLoading js调用了Android的方法");
                                                  ToastUtil.showShortToast("shouldOverrideUrlLoading js调用了Android的方法");
                                                  // 可以在协议上带有参数并传递到Android上
                                                  HashMap<String, String> params = new HashMap<>();
                                                  Set<String> collection = uri.getQueryParameterNames();
                                              }
                                              return true;
                                          }
                                          return super.shouldOverrideUrlLoading(view, url);
                                      }
                                  }
        );

        mWebView.setWebChromeClient(new WebChromeClient() {
                                        // 拦截prompt 输入框(原理同方式2)
                                        // 参数message:代表promt（）的内容（不是url）
                                        // 参数result:代表给js的返回值
                                        @Override
                                        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                                            // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                                            // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                                            //假定传入进来的 url = "js://demo?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                                            Uri uri = Uri.parse(message);
                                            // 如果url的协议 = 预先约定的 js 协议
                                            // 就解析往下解析参数
                                            if ( uri.getScheme().equals("js")) {

                                                // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                                                // 所以拦截url,下面JS开始调用Android需要的方法
                                                if (uri.getAuthority().equals("demo")) {

                                                    //
                                                    // 执行JS所需要调用的逻辑
                                                    System.out.println("onJsPrompt js调用了Android的方法");
                                                    ToastUtil.showShortToast("onJsPrompt js调用了Android的方法111111");


                                                    //参数result:代表返回值(输入值)
                                                    result.confirm("onJsPrompt js调用了Android的方法成功啦44445");
                                                }
                                                return true;
                                            }
                                            return super.onJsPrompt(view, url, message, defaultValue, result);
                                        }

// 通过alert()和confirm()拦截的原理相同，此处不作过多讲述

                                        // 拦截JS的所有的警告框
                                        @Override
                                        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                                            ToastUtil.showShortToast("onJsAlert js调用了Android的方法22222");
                                            return super.onJsAlert(view, url, message, result);

                                        }

                                        // 拦截JS的确认框
                                        @Override
                                        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                                            ToastUtil.showShortToast("onJsConfirm js调用了Android的方法3333");
                                            return super.onJsConfirm(view, url, message, result);
                                        }
                                    }
        );
    }

}