package com.luoyang.androidfunDemo.bean;

import java.util.HashMap;

/**
 * Created by coolpad on 2015/12/28.
 */
public abstract class ItemBean {

    public static final int TYPE_HEADER = 9;
    public static final int TYPE_DATA = 10;
    public static final int ITEM_PERMISSION = 18;

    public String sourceId;
    public int isVerified;
    public String exParamters;//透传参数(用于华为，应用宝等打点，接口请求参数)
    public String apkSizeDesc;//app大小介绍
    public String downloadDesc;//app下载次数介绍

    protected String pageSource;
    protected HashMap<String, String> bdMeta;
    protected String appSource;
    protected String pageName;
    protected String widgetName;
    protected String locationIndex;

    public String getApkSizeDesc() {
        return apkSizeDesc;
    }

    public void setApkSizeDesc(String apkSizeDesc) {
        this.apkSizeDesc = apkSizeDesc;
    }

    public String getDownloadDesc() {
        return downloadDesc;
    }

    public void setDownloadDesc(String downloadDesc) {
        this.downloadDesc = downloadDesc;
    }

    public String getExParamters() {
        return exParamters;
    }

    public void setExParamters(String exParamters) {
        this.exParamters = exParamters;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public abstract String getViewType();

    public int getItemType() {
        return 0;
    }

    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }


    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(String locationIndex) {
        this.locationIndex = locationIndex;
    }
}
