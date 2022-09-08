package com.luoyang.androidfunDemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobing on 15/12/31.
 * APP应用的详情实体类
 */
public class DetailBean extends ItemBean {
    String apkMD5;
    String downloadUrl;
    String source;
    String appFrom;
    String dlCallback;
    String icon;
    String versionName;
    String versionCode;
    String developer;
    String appName;
    String minSdkVerName;
    String releaseTime;
    String groupId;
    String brief;
    String size;
    String downloadNum;
    String packageId;
    @SerializedName("package")
    String packageName;
    String docId;
    String apkUrl;
    String apkTitle;
    int app_mark_isCoolpad;
    String app_mark_isSecurity;
    String app_mark_manual_test;
    ArrayList<String> oriImageUrls;
    ArrayList<String> miniImageUrls;
    String changeLog;
    float averageRating;
    int commentCount;
    int user_age;//适合用户玩的年龄

    String warnIcon;
    String warnWords;

    String pic;
    String bannerPic;
    String limitPerson;
    String firstPublishTime;
    String cate;
    String orderNum;
    String orderActNum;
    int orderType;

    String videoUrl;//介绍视频地址
    String headPic;//视频封面
    int showShareBtn;//是否显示分享按钮 1:显示 0不显示

    String privacyAgreement;//应用隐私政策地址
    List<String> labels;//app列表的标签

    public String getPrivacyAgreement() {
        return privacyAgreement;
    }

    public void setPrivacyAgreement(String privacyAgreement) {
        this.privacyAgreement = privacyAgreement;
    }

    public int getShowShareBtn() {
        return showShareBtn;
    }

    public void setShowShareBtn(int showShareBtn) {
        this.showShareBtn = showShareBtn;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    int level;

    public int getApp_mark_isCoolpad() {
        return app_mark_isCoolpad;
    }

    public void setApp_mark_isCoolpad(int app_mark_isCoolpad) {
        this.app_mark_isCoolpad = app_mark_isCoolpad;
    }

    public String getEditorIntro() {
        return editorIntro;
    }

    public void setEditorIntro(String editorIntro) {
        this.editorIntro = editorIntro;
    }

    String editorIntro;


    public int getCommentCount() {
        return commentCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<String> getOriImageUrls() {
        return oriImageUrls;
    }

    public ArrayList<String> getMiniImageUrls() {
        return miniImageUrls;
    }

    public void setMiniImageUrls(ArrayList<String> miniImageUrls) {

        this.miniImageUrls = miniImageUrls;
    }

    public void setOriImageUrls(ArrayList<String> oriImageUrls) {
        this.oriImageUrls = oriImageUrls;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setMinSdkVerName(String minSdkVerName) {
        this.minSdkVerName = minSdkVerName;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setDlCallback(String dlCallback) {
        this.dlCallback = dlCallback;
    }

    public String getDlCallback() {
        return dlCallback;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public void setApkTitle(String apkTitle) {
        this.apkTitle = apkTitle;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getIcon() {
        return icon;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getAppName() {
        return appName;
    }

    public String getMinSdkVerName() {
        return minSdkVerName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getBrief() {
        return brief;
    }

    public String getSize() {
        return size;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getDocId() {
        return docId;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public String getApkTitle() {
        return apkTitle;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public String getAppFrom() {
        return appFrom;
    }

    public void setAppFrom(String appFrom) {
        this.appFrom = appFrom;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getApkMD5() {
        return apkMD5;
    }

    public void setApkMD5(String apkMD5) {
        this.apkMD5 = apkMD5;
    }

    public String getApp_mark_isSecurity() {
        return app_mark_isSecurity;
    }

    public void setApp_mark_isSecurity(String app_mark_isSecurity) {
        this.app_mark_isSecurity = app_mark_isSecurity;
    }

    public String getApp_mark_manual_test() {
        return app_mark_manual_test;
    }

    public void setApp_mark_manual_test(String app_mark_manual_test) {
        this.app_mark_manual_test = app_mark_manual_test;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getWarnIcon() {
        return warnIcon;
    }

    public void setWarnIcon(String warnIcon) {
        this.warnIcon = warnIcon;
    }

    public String getWarnWords() {
        return warnWords;
    }

    public void setWarnWords(String warnWords) {
        this.warnWords = warnWords;
    }

    public String compatibleMsg;
    public String warningTips;

    public String getCompatibleMsg() {
        return compatibleMsg;
    }

    public void setCompatibleMsg(String compatibleMsg) {
        this.compatibleMsg = compatibleMsg;
    }

    public String getWarningTips() {
        return warningTips;
    }

    public void setWarningTips(String warningTips) {
        this.warningTips = warningTips;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getLimitPerson() {
        return limitPerson;
    }

    public void setLimitPerson(String limitPerson) {
        this.limitPerson = limitPerson;
    }

    public String getFirstPublishTime() {
        return firstPublishTime;
    }

    public void setFirstPublishTime(String firstPublishTime) {
        this.firstPublishTime = firstPublishTime;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderActNum() {
        return orderActNum;
    }

    public void setOrderActNum(String orderActNum) {
        this.orderActNum = orderActNum;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public String getViewType() {
        return null;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }


    public boolean hideLine = false;
}
