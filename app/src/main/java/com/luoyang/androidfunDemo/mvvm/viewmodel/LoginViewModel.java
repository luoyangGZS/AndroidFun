package com.luoyang.androidfunDemo.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.luoyang.androidfunDemo.mvvm.model.LoginModel;
import com.luoyang.androidfunDemo.mvvm.model.LoginResponseBean;

/**
 * viewModel层
 * 不持有view层对象（相对于mvp，有效防止内存泄漏）
 *
 * @author luoyang
 * @date 2022/8/31
 */
public class LoginViewModel extends AndroidViewModel {

    public String inputName="123";
    public String inputPwd="123";
    /**
     * 用于回调数据给v,整个登录返回实体
     */
    public MutableLiveData<LoginResponseBean<String>> result = new MutableLiveData<>();
    /**
     * 用于回调数据给v,成功
     */
    public MutableLiveData<String> success = new MutableLiveData<>();
    /**
     * 用于回调数据给v，失败
     */
    public MutableLiveData<String> failed = new MutableLiveData<>();
    private LoginModel loginModel;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        createModel();
    }

    /**
     * 创建model
     */
    private void createModel() {
        loginModel = new LoginModel();
    }

    /**
     * 登录操作
     *
     * @param name
     * @param pwd  LiveData的setValue()来更新UI只能在主线程中调用，postValue()可以在任何线程中调用（类比view的invalidate()与postInvalidate()的区别）
     */
    public void login(String name, String pwd) {
        loginModel.login(name, pwd, new LoginModel.ILoginDataCallback() {
            @Override
            public void onLoginState(LoginResponseBean<String> baseResponse) {
                result.postValue(baseResponse);// 通知v
                if (baseResponse.getCode() == 200) {
                    success.postValue(baseResponse.getData());// 通知v
                } else {
                    failed.postValue(baseResponse.getMsg());// 通知v
                }
            }
        });
    }
}

