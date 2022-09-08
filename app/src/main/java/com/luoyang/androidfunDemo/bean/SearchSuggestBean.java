package com.luoyang.androidfunDemo.bean;

/**
 * Created by zhaobing on 16/1/8.
 */
public class SearchSuggestBean extends ItemBean {
    private String Sug;
    private int Weight;
    private String type;
    private String packageId;
    private String source;
    private String sourceId;

    public void setSug(String sug) {
        Sug = sug;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getSug() {

        return Sug;
    }

    public int getWeight() {
        return Weight;
    }

    public String getType() {
        return type;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getViewType() {
        return null;
    }
}
