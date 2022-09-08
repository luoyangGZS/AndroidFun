package com.luoyang.androidfunDemo.mvvm.model;

/**
 * 登录响应
 *
 * @author luoyang
 * @date 2022/8/31
 */

public class LoginResponseBean<T> {
    /**
     * 状态码
     */
    int code;
    /**
     * 状态消息
     */
    String msg;
    /**
     * 响应数据
     */
    T data;

    public LoginResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}