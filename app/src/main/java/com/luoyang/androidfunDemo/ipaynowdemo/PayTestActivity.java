package com.luoyang.androidfunDemo.ipaynowdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ipaynow.plugin.api.IpaynowPlugin;
import com.ipaynow.plugin.manager.route.dto.ResponseParams;
import com.ipaynow.plugin.manager.route.impl.ReceivePayResult;
import com.ipaynow.plugin.view.IpaynowLoading;
import com.luoyang.androidfunDemo.R;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author luoyang
 */
public class PayTestActivity extends AppCompatActivity implements View.OnClickListener, ReceivePayResult {

    private static String TAG = "PayTestActivity";
    private Button mAliPay;
    private IpaynowPlugin mIPayNowPlugin;
    private IpaynowLoading mIPayNowLoading;
    private MessageAsyncTask mMessageAsyncTask;
    private HashMap<String, String> mReqParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_test);
        initView();
        initPaySdk();
    }

    private void initPaySdk() {
        //初始化插件，每次Activity初始化是调用
        mIPayNowPlugin = IpaynowPlugin.getInstance().init(this);
        // 获取进度条
        mIPayNowLoading = mIPayNowPlugin.getDefaultLoading();
    }

    private void showLoading() {
        if (mIPayNowLoading != null) {
            mIPayNowLoading.setLoadingMsg("正在生成订单");
            mIPayNowLoading.show();
        }
    }

    private void initView() {
        mAliPay = findViewById(R.id.ali_pay);
        mAliPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showLoading();
        String orderNo = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        createPayMessage(orderNo, "小白菜", "10", orderNo);

        mMessageAsyncTask = new MessageAsyncTask();
        mMessageAsyncTask.setCallBack(new MessageAsyncTask.MessageCallBack() {
            @Override
            public String inBackground(HashMap<String, String>... hashMaps) {
                Log.d(TAG, "inBackground hashMaps" + Arrays.toString(hashMaps));
//                mReqParams.put("payChannelType", "12");
                mReqParams.put("payChannelType", "13");
                String preSignStr = createFormString(mReqParams, true, false);
                String needCheckMsg = "mhtSignature=" + Md5Util.md5(preSignStr + "&" + Md5Util.md5(PayConstant.APP_KEY));
                needCheckMsg = preSignStr + "&" + needCheckMsg;
                return needCheckMsg;
            }

            @Override
            public void postExecute(String requestMessage) {
                Log.d(TAG, "postExecute requestMessage" + requestMessage);
                mIPayNowLoading.dismiss();
                // 如商户保留域mhtReserved包含特殊字符，在调起插件前对value做一次utf8编码
                String mhtReserved = mReqParams.get("mhtReserved");
                if (!TextUtils.isEmpty(mhtReserved) && requestMessage.contains(mhtReserved)) {
                    requestMessage = requestMessage.replace(mhtReserved, URLEncoder.encode(mhtReserved));
                }
                // 设置支付结果回调接口，同时调起支付请求
                mIPayNowPlugin
//                    .setMiniProgramEnv(0)
                        .setCustomLoading(mIPayNowLoading)
                        .setCallResultReceiver(PayTestActivity.this)
                        .pay(requestMessage);
            }
        });
        mMessageAsyncTask.execute(mReqParams);
    }

    private static String createFormString(Map<String, String> para, boolean sort, boolean encode) {
        List<String> keys = new ArrayList<String>(para.keySet());
        if (sort) {
            Collections.sort(keys);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = para.get(key);
            if (encode) {
                value = UrlEncodeUtils.urlEncode(value);
            }
            if (i == keys.size() - 1) {
                sb.append(key).append("=").append(value);
            } else {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.toString();
    }

    private void createPayMessage(String mhtOrderNo, String orderName, String orderAmt, String mhtOrderStartTime) {
        mReqParams.put("funcode", "WP001");
        mReqParams.put("version", "1.0.3");
        mReqParams.put("appId", PayConstant.APP_ID);
        mReqParams.put("mhtOrderNo", mhtOrderNo);
        mReqParams.put("mhtOrderName", orderName);
        mReqParams.put("mhtOrderType", "01");
        mReqParams.put("mhtCurrencyType", "156");
        mReqParams.put("mhtOrderAmt", orderAmt);
        mReqParams.put("mhtOrderDetail", "test");
        mReqParams.put("mhtOrderTimeOut", "3600");
        mReqParams.put("mhtOrderStartTime", mhtOrderStartTime);
        mReqParams.put("notifyUrl", PayConstant.notifyUrl);
        mReqParams.put("mhtCharset", "UTF-8");
        mReqParams.put("deviceType", "01");
//        mReqParams.put("mhtSubAppId", PayConstant.wxSubAppId);
        mReqParams.put("mhtLimitPay", "0");
        mReqParams.put("mhtSignType", "MD5");
    }

    @Override
    public void onIpaynowTransResult(ResponseParams responseParams) {
        String respCode = responseParams.respCode;
        String errorCode = responseParams.errorCode;
        String errorMsg = responseParams.respMsg;
        StringBuilder temp = new StringBuilder();
        if (respCode.equals("00")) {
            temp.append("交易状态:成功");
        } else if (respCode.equals("02")) {
            temp.append("交易状态:取消");
        } else if (respCode.equals("01")) {
            temp.append("交易状态:失败").append("\n").append("错误码:").append(errorCode).append("原因:" + errorMsg);
        } else if (respCode.equals("03")) {
            temp.append("交易状态:未知").append("\n").append("原因:" + errorMsg);
        } else {
            temp.append("respCode=").append(respCode).append("\n").append("respMsg=").append(errorMsg);
        }
        Toast.makeText(this, "onIpaynowTransResult:" + temp.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "onIpaynowTransResult temp:" + temp);
    }

    @Override
    public void onPluginError(Throwable throwable) {
        Log.d(TAG, "onPluginError throwable:" + throwable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPayNowPlugin.onActivityDestroy();
        if (mMessageAsyncTask != null) {
            mMessageAsyncTask.cancel(true);
        }
    }
}