package com.luoyang.androidfunDemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BaseHttpResult {

    @SerializedName("result")
    private int result = -1;
    private String msg = null;

    @SerializedName("content")
    private List<ItemBean> content = null;

    public List<ItemBean> getData() {
        return content;
    }

    public void setData(List<ItemBean> data) {
        this.content = data;
    }

    public boolean isSuccess() {
        return result == 0;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
