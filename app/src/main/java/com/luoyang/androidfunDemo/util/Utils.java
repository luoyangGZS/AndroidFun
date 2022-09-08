package com.luoyang.androidfunDemo.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.luoyang.androidfunDemo.MainApplication;
import com.luoyang.base.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;

public class Utils {
    private static final String TAG = "Utils";

    public static final int VERSION_LOLLIPOP = 21;


    public static View inflate(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }


    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 连接网络是否有效
     *
     * @param context
     * @return 是否联网，是否有效
     */
    public static boolean isNetworkOnline(Context context) {
        //不影响之前的逻辑
        boolean isOnline = true;
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities capabilities = null;
            //Android 6.0之后有效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // need ACCESS_NETWORK_STATE permission
                capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                isOnline = (capabilities != null)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }
        } catch (Exception e) {
        }
        return isOnline;
    }

    public static boolean hasNetwork() {
        return getActiveNetworkInfo() != null && isNetworkOnline(MainApplication.getInstance());
    }

    private static Object getSystemService(String service) {
        return MainApplication.getInstance().getSystemService(service);
    }

    private static NetworkInfo getActiveNetworkInfo() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivity.getActiveNetworkInfo();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUTF8(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return URLEncoder.encode(str, "UTF-8");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * URLDecoder解码 UTF8
     *
     * @param param
     * @return
     */
    public static String decodeUTF8(String param) {
        if (!TextUtils.isEmpty(param)) {
            try {
                return URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return param;
            }
        }
        return "";
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * getDrawable
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 得到colors.xml中的颜色
     */
    public static int getColor(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }

    /**
     * sp to px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dip-->px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2Px(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }


    /**
     * Get BaseActivity from context object
     *
     * @param context something
     * @return object of BaseActivity or null if it is not Activity
     */
    public static BaseActivity scanForBaseActivity(Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof BaseActivity) {
            return (BaseActivity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForBaseActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    public static Resources getResources() {
        return MainApplication.getInstance().getResources();
    }

    public static String getSafeString(int resId) {
        try {
            return getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param tvContent
     * @param editorIntro   一句话推荐
     * @param compatibleMsg 版本兼容
     * @param warningTips   负面提示
     *                      <p>
     *                      安卓版本兼容性＞负面提示＞一句话推荐
     */
    public static void setContentText(TextView tvContent, String editorIntro, String compatibleMsg, String
            warningTips) {
        if (null == tvContent) {
            return;
        }

        if (null != compatibleMsg && !(compatibleMsg.isEmpty())) {
            tvContent.setText(compatibleMsg);
            return;
        }

        if (null != warningTips && !(warningTips.isEmpty())) {
            tvContent.setText(warningTips);
            return;
        }

        if (null != editorIntro && !(editorIntro.isEmpty())) {
            tvContent.setText(editorIntro);
            return;
        }

        tvContent.setText("");
    }

    private static final DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static final DecimalFormat fileDecimalFormat = new DecimalFormat("#0.##");
    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size == 0) {
            return "0M";
        }
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }


}