package com.luoyang.androidfunDemo.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.luoyang.androidfunDemo.util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通用参数生产者(例如UID、渠道号等等)
 */
public class CommonInfoProducer {
    private static final String TAG = "CommonInfoProducer";
    /**
     * cuId在系统设置中存储的Key(尽量复杂,避免于其他APP重名)
     */
    private static final String KEY_SYSTEM_SETTINGS_CUID = "coolpad_common@cuid#1212";

    private static final String CUID_PATH = "coolmart";

    private static final String CUID_FILE = "coolpad_cuid";

    /**
     * 默认的渠道号
     */
    private static final String DEFAULT_CHANNEL = "00001";
    /**
     * 网络类型
     */
    public static final String NET_WIFI = "WF";
    public static final String NET_4G = "4G";
    public static final String NET_3G = "3G";
    public static final String NET_2G = "2G";
    public static final String NET_UNKNOWN = "NET_UNKNOWN";
    public static final String NET_NONE = "NONE";
    /**
     * Settings中OAID的Key
     */
    private static final String SETTINGS_OAID_KEY = "coolos.oaid";
    /**
     * CommonInfoProducer单例
     */
    private static CommonInfoProducer mBuilder = null;

    /**
     * Context
     */
    private final Context mContext;

    /**
     * SDK渠道信息
     */
    private String mChannel = "";

    /**
     * 系统版本信息
     */
    private String mOsVersion = "";

    /**
     * 系统API版本
     */
    private int mOsAPI = 0;

    /**
     * 设备IMEI号
     */
    private String mIMEI = "";

    /**
     * 机型信息，如 hero。为空时值为NUL
     */
    private String mPhoneMode;

    /**
     * 手机品牌，为空时值为NUL
     */
    private String mPhoneBrand;


    private String mAppVersionName = "";

    /**
     * 客户端版本 versionCode
     */
    private int mAppVersionCode = 0;

    /**
     * 用户的唯一标示, clientId
     */
    private String mClientId = "";

    /**
     * 加密后的key
     */
    private String mSecretkey = "";

    /**
     * SN码
     */
    private String mSN = "";

    /**
     * 设备IMSI号
     */
    private String mIMSI = "";
    /**
     * 手机分辨率
     **/
    private String mResolution = "";
    /**
     * 屏幕密度
     **/
    private int mDpi = 0;
    /**
     * 网络环境 WF/4G/3G/2G
     **/
    private String mBear;
    /**
     * 客户端ip
     **/
    private String mIp = "";
    /**
     * wifi ssid
     **/
    private String mWifiSsid = "";
    /**
     * wifi bssid
     **/
    private String mwifiBssid = "";
    /**
     * mac地址
     **/
    private String mMac = "";

    /**
     * 包名
     */
    private String mPackageName = "";

    /**
     * Android 10的Andrroid ID
     */
    private String ANDROID_ID = "";
    /**
     * Cool OS设备信息
     */
//    private CoolDeviceInfo mCoolDeviceInfo;
    private String mSettingsOaid = "";

    /**
     * 私有构造函数
     *
     * @param ctx Context
     */
    private CommonInfoProducer(Context ctx) {
        mContext = ctx;
        init();
    }

    /**
     * 获取通用参数Producer的单例
     *
     * @param ctx Context
     * @return CommonInfoProducer单例
     */
    public static synchronized CommonInfoProducer getInstance(Context ctx) {

        if (mBuilder == null) {
            mBuilder = new CommonInfoProducer(ctx);
        }

        return mBuilder;
    }

    private synchronized void init() {
        // OS版本号
        mOsVersion = Build.VERSION.RELEASE;
        mOsAPI = Build.VERSION.SDK_INT;
        if (TextUtils.isEmpty(mOsVersion)) {
            mOsVersion = "0.0";
        } else {
            mOsVersion = mOsVersion.replace("_", "-");
        }


        if (TextUtils.isEmpty(mChannel)) {
            mChannel = DEFAULT_CHANNEL;
        }

        // IMEI号
        mIMEI = readIMEI();
        if (TextUtils.isEmpty(mIMEI)) {
            mIMEI = "0";
        }
        // IMSI
        mIMSI = getIMSI();
        if (TextUtils.isEmpty(mIMSI)) {
            mIMSI = "0";
        }
        //手机品牌
        mPhoneBrand = getPhoneBrand();
        // 机型信息
        mPhoneMode = getPhoneMode();

        mAppVersionName = getVersion(mContext);
        mAppVersionCode = getVersionCode(mContext);
        mSN = readSN();
        /**获取手机分辨率**/
        mResolution = getResolution();
        if (TextUtils.isEmpty(mResolution)) {
            mIMSI = "0_0";
        }
        mBear = getNetType();
        if (TextUtils.isEmpty(mBear)) {
            mBear = NET_UNKNOWN;
        }
        mIp = getIp();
        if (TextUtils.isEmpty(mIp)) {
            mIp = "0";
        }
        if (TextUtils.isEmpty(mMac)) {
            mMac = "0";
        } else {
            if (mMac.startsWith(":") || mMac.endsWith(":")) {
                mMac = "0";
            }
        }
        if (TextUtils.isEmpty(mwifiBssid)) {
            mwifiBssid = "0";
        }
        if (TextUtils.isEmpty(mWifiSsid)) {
            mWifiSsid = "Null";
        }
        mPackageName = mContext.getPackageName();
        //获取Android 10的Android 防止imei获取不到的情况
        ANDROID_ID = getAndroidID();
    }

    public String getPushClientID() {
        return "";
    }

    /**
     * 基于传入的URL，追加通用参数
     * 只有Url 说明是get请求
     *
     * @param url
     * @return 处理后的url
     */
    public String appendCommonParameter(String url) {
        return appendCommonParameter(url, "");
    }

    /**
     * 从URL中移除removeParams的Key
     * 加密得到sign 拼接的时候 移除removeParams对应的Key
     *
     * @param url
     * @param removeParams
     * @return
     */
    public String appendCommonParameter(String url, HashMap<String, String> removeParams) {
        return appendCommonParameter(url, "", removeParams);
    }

    public String appendCommonParameter(String url, String requestBody) {
        return appendCommonParameter(url, requestBody, null);
    }

    /**
     * 添加请求的Body参数
     *
     * @param url
     * @param requestBody  有requestBody说明是Post请求
     * @param removeParams 拼接的时候 移除removeParams对应的Key
     * @return
     */
    public synchronized String appendCommonParameter(String url, String requestBody, HashMap<String, String> removeParams) {
        String actionUrl;
        if (url.contains("?")) {
            //如果包含了? 需要提取url
            actionUrl = getAbsUrl(url);
        } else {
            actionUrl = url;
        }
        //拿到传入的url中已经拼接的参数 stringMap是参与加签的map
        Map<String, String> stringMap = getUrlList(url);
        //移除原有的sign签名
        stringMap.remove("sign");
        addMapParameter(stringMap, "clientId", mClientId);
        addMapParameter(stringMap, "osVersion", mOsVersion);
        addMapParameter(stringMap, "osApi", String.valueOf(mOsAPI));
        addMapParameter(stringMap, "phoneBrand", mPhoneBrand);
        addMapParameter(stringMap, "phoneModel", mPhoneMode);
        addMapParameter(stringMap, "channelId", mChannel);
        addMapParameter(stringMap, "versioName", mAppVersionName);
        addMapParameter(stringMap, "versionCode", String.valueOf(mAppVersionCode));
        addMapParameter(stringMap, "bear", mBear);
        addMapParameter(stringMap, "resolution", mResolution);
        addMapParameter(stringMap, "bdi_mac", mMac);
        addMapParameter(stringMap, "bdi_imsi", mIMSI);
        addMapParameter(stringMap, "dpi", String.valueOf(mDpi));
        addMapParameter(stringMap, "pkg", mPackageName);
        addMapParameter(stringMap, "android_id", ANDROID_ID);
        addMapParameter(stringMap, "android_version", Build.VERSION.RELEASE + "-" + Build.VERSION.INCREMENTAL);
        addMapParameter(stringMap, "cp_coolos", Build.ID);
        addMapParameter(stringMap, "requestId", UUID.randomUUID().toString());
        //添加Base基础公参
        addBaseParameter(stringMap);
        //标记次接口已加入CP9验签
        addMapParameter(stringMap, "signKey", "IR1qVdjO");
//        addMapParameter(stringMap, "appid", CPApiSignUtils.APP_SIGN_APP_ID);
        Map<String, String> signMap = new HashMap<>();
        //复制一份参与验签的 map 需要UTF-8解码
        mapCopy(stringMap, signMap);
        //将UTF-8解码之后的signMap进行参与加签
//        String cp9Sign = CPApiSignUtils.signApiHap(signMap, requestBody, actionUrl);
        //stringMap转StringBuffer 后拼到Url后面
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            //移除from表单的key
            if (removeParams != null && removeParams.get(entry.getKey()) != null) {
                continue;
            }
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
            buffer.append("&");
        }
        //把验签的加在最后
        buffer.append("sign=");//固定值
//        buffer.append(cp9Sign);
        //返回数据
        if (actionUrl.contains("?")) {
            actionUrl = actionUrl + "&" + new String(buffer);
        } else {
            actionUrl = actionUrl + "?" + new String(buffer);
        }
        return actionUrl;
    }


    /**
     * 添加基础公共参数
     * for 20210618
     *
     * @param baseMap
     */
    private void addBaseParameter(Map<String, String> baseMap) {

        //5.N verName  当前应用版本名
        addMapParameter(baseMap, "verName", mAppVersionName);
        //6.N verCode  当前应用版本号，建议是能转换为数字的值
        addMapParameter(baseMap, "verCode", String.valueOf(mAppVersionCode));
        //7.N cpOs  当前应用版本号，建议是能转换为数字的值 "CP03.210604.1S.AL"
        addMapParameter(baseMap, "cpOs", Build.ID);
        //8.N channel 当前包所属渠道号
        addMapParameter(baseMap, "channel", mChannel);
        //10.N mac地址
        addMapParameter(baseMap, "mac", mMac);
        //11.N bundle 包名
        addMapParameter(baseMap, "bundle", mPackageName);
        //12.N brand 手机品牌名
        addMapParameter(baseMap, "brand", mPhoneBrand);
        //12.N model 手机型号
        addMapParameter(baseMap, "model", mPhoneMode);
        //13.Y platform 平台 A I W (android ios web)
        addMapParameter(baseMap, "platform", "A");
        //14.N lat 纬度
        addMapParameter(baseMap, "lat", "");
        //15.N lon 经度
        addMapParameter(baseMap, "lon", "");
        //16.N cityCode 市id
        addMapParameter(baseMap, "cityCode", "");
        //17.N adCode 区、县id
        addMapParameter(baseMap, "adCode", "");
        //18.Y timestamp 请求时间戳，毫秒数
        addMapParameter(baseMap, "timestamp", String.valueOf(System.currentTimeMillis()));
        //19.Y appid 应用id，签名用
        //20.Y sign CP9接口签名
    }

    /**
     * 往Hap中添加一对Key value
     *
     * @param map
     * @param key
     * @param value
     */
    private void addMapParameter(Map map, String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, TextUtils.isEmpty(value) ? "" : value);
        }
    }

    /**
     * 获取url 中参数的Map;
     *
     * @param url
     * @return
     */
    public static Map<String, String> getUrlList(String url) {
        Map<String, String> result = new HashMap<>();
        if (url.contains("?")) {
            int index = url.indexOf("?");
            String temp = url.substring(index + 1);
            String[] keyValue = temp.split("&");
            for (int i = 0; i < keyValue.length; i++) {
                String[] val = keyValue[i].split("=");
                if (val.length == 1) {
                    result.put(val[0], "");
                }
                if (val.length == 2) {
                    result.put(val[0], val[1]);
                }
            }
        }
        return result;
    }


    /**
     * 提出url地址
     *
     * @param url
     * @return
     */
    public String getAbsUrl(String url) {
        int index = url.indexOf("?");
        return url.substring(0, index);
    }


    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        String brand = Build.MANUFACTURER;
        if (TextUtils.isEmpty(brand)) {
            brand = "NUL";
        } else {
            brand = brand.replace("_", "-");
        }

        return brand;
    }

    /**
     * 获取手机机型
     *
     * @return
     */
    public static String getPhoneMode() {
        String mode = Build.MODEL;
        if (TextUtils.isEmpty(mode)) {
            mode = "NUL";
        } else {
            mode = mode.replace("_", "-");
        }
        return mode;
    }

    /**
     * 提供给外部获取渠道号
     *
     * @return 渠道号
     */
    public String getChannel() {
        return mChannel;
    }

    //获取版本号
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取版本号(内部识别号)
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getProductBrand() {
        Class<?> classType = null;
        try {
            classType = Class.forName("com.yulong.android.coolmart.utils.SystemPropertiesReflect");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            String value = (String) getMethod.invoke(classType, "ro.product.brand");
            return value;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取系统的IMEI。
     *
     * @return IMEI
     */
    private String readIMEI() {
        String imei = null;
//
//        try {
//            final TelephonyManager tm = (TelephonyManager) mContext
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm != null) {
//                imei = tm.getDeviceId();
//            }
//
//        } catch (Exception e) {
////            e.printStackTrace();
//        }
//        // imei内容校验，如果有非法字符，返回默认值
//        imei = checkImei(imei);

        if (TextUtils.isEmpty(imei)) {
            imei = "";
        }
        return imei;
    }

    /**
     * 获取系统的IMSI
     *
     * @param
     * @return IMSI
     */
    private String getIMSI() {
        String imsi = null;
//        try {
//            final TelephonyManager tm = (TelephonyManager) mContext
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm != null) {
//                imsi = tm.getSubscriberId();
//            }
//
//        } catch (Exception e) {
//        }
//        imsi = checkImei(imsi);

        if (TextUtils.isEmpty(imsi)) {
            imsi = "";
        }
        return imsi;
    }

    /**
     * @description 获取网络类型 （WF/4G/3G/2G）
     * @version
     * @author zhang jia
     * @tags
     * @see
     * @since 2015-12-7
     */
    private String getNetType() {

        try {

            ConnectivityManager connectionManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectionManager != null) {

                NetworkInfo networkInfo = connectionManager
                        .getActiveNetworkInfo();
                if (networkInfo == null) {
                    return NET_NONE;
                }
                int type = networkInfo.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    return NET_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    return getMobileNetClass(networkInfo.getSubtype());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NET_NONE;
    }

    /**
     * 区分手机制式
     *
     * @param networkType
     * @return
     */
    private static String getMobileNetClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NET_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NET_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NET_4G;
            default:
                return NET_UNKNOWN;
        }
    }

    /**
     * 获取手机分辨率
     **/
    private String getResolution() {
        String resolution = "";
        try {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            resolution = size.x + "_" + size.y;
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            mDpi = dm.densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resolution;
    }

    private String getIp() {
        String ip = "";
//        try {
//            WifiManager wifiManager = (WifiManager) mContext
//                    .getSystemService(Context.WIFI_SERVICE);
//            if (wifiManager != null) {
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                if (wifiInfo != null) {
//                    mMac = wifiInfo.getMacAddress();
//                    if (mMac.equals("02:00:00:00:00:00")) {
//                        mMac = getMacAddr();
//                    }
//                    int ipInt = wifiInfo.getIpAddress();
//                    mWifiSsid = wifiInfo.getSSID();
//                    mwifiBssid = wifiInfo.getBSSID();
//                    StringBuffer sb = new StringBuffer();
//                    sb.append(ipInt & 0xFF).append(".");
//                    sb.append((ipInt >> 8) & 0xFF).append(".");
//                    sb.append((ipInt >> 16) & 0xFF).append(".");
//                    sb.append((ipInt >> 24) & 0xFF);
//                    ip = sb.toString();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return ip;
    }


    /**
     * 获取手机的sn码。
     *
     * @return sn码
     */
    private String readSN() {
//        String sn = Utils.getSN();
//        return sn;
        return "";
    }

    /**
     * imei校验
     * 如果取到的地址带：，替换为default id
     *
     * @param imei imei
     */
    private String checkImei(String imei) {
        if (null != imei && imei.contains(":")) {
            return "";
        }
        return imei;
    }


    /**
     * 检查权限(重量级)。如果没有声明对应的权限，直接抛出 runtime exception。
     *
     * @param context    Context
     * @param permission android.Manifest.permission.xxxx
     */
    private static void checkPermission(Context context, String permission) {
        boolean allowedByPermission = isPermissionAllowed(context, permission);
        if (!allowedByPermission) {
            throw new SecurityException(
                    "Permission Denial: requires permission " + permission);
        }
    }

    /**
     * 检查权限(轻量级)。
     *
     * @param context    Context
     * @param permission android.Manifest.permission.xxxx
     * @return 是否具备权限
     */
    private static boolean isPermissionAllowed(Context context,
                                               String permission) {
        int result = context.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Return the android id of device.
     *
     * @return the android id of device
     */
    @SuppressLint("HardwareIds")
    public String getAndroidID() {
        try {
            String id = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
            if ("9774d56d682e549c".equals(id)) {
                return "";
            } else {
                return id == null ? "" : id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * copy map
     *
     * @param paramsMap
     * @param resultMap
     */
    private void mapCopy(Map<String, String> paramsMap, Map<String, String> resultMap) {
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        if (paramsMap == null) {
            return;
        }
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                resultMap.put(entry.getKey(), Utils.decodeUTF8(value));
            } else {
                resultMap.put(entry.getKey(), "");
            }
        }
    }

}
