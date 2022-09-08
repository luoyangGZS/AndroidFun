package com.luoyang.androidfunDemo.util;

import android.util.Log;

import com.luoyang.androidfunDemo.Constant;
import com.luoyang.androidfunDemo.MainApplication;
import com.luoyang.androidfunDemo.retrofit.CommonInfoProducer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * HTTP联网获取数据
 * @author luoyang
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";
    public static final String CONNECT_FAIL = "conn_fail";



    /**
     * get请求
     *
     * @param actionUrl
     * @return
     */
    public static String doGet(String actionUrl) {
        if (!Utils.hasNetwork()) {
            return CONNECT_FAIL;
        }
        HttpURLConnection conn = null;
        try {
            actionUrl = CommonInfoProducer.getInstance(
                    MainApplication.getInstance()).appendCommonParameter(actionUrl);
            Log.i(TAG, "doGet request url:" + actionUrl);
            URL url = new URL(actionUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(Constant.HTTP_TIME_OUT);
            conn.setReadTimeout(Constant.HTTP_TIME_OUT);
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                String content = getConnectResult(conn);
                return content;
            }
        } catch (Exception e) {
           Log.e(TAG, "doGet Exception:", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return CONNECT_FAIL;
    }





    /**
     * 从服务获取返回数据
     *
     * @param conn
     * @return
     */
    private static String getConnectResult(URLConnection conn) {
        InputStream finalInputStream = null;
        try {
            InputStream is = conn.getInputStream();
            if ("gzip".equals(conn.getContentEncoding())) {
                finalInputStream = new BufferedInputStream(new GZIPInputStream(is));
            } else {
                finalInputStream = is;
            }
            StringBuilder res = new StringBuilder();
            int ch;
            while ((ch = finalInputStream.read()) != -1) {
                res.append((char) ch);
            }
            return res.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (finalInputStream != null) {
                    finalInputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return CONNECT_FAIL;
    }
}



