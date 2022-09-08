package com.luoyang.androidfunDemo.mvvm.model;

import android.os.SystemClock;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录业务m层的实现
 *
 * @author luoyang
 * @date 2022/8/31
 */


public class LoginModel {

    public void login(String name, String pwd, ILoginDataCallback loginDataCallback) {
        //viewModel持有view导致的view的内存泄漏问题
        new Thread(() -> {
            SystemClock.sleep(3000);// 模拟耗时操作
            LoginResponseBean<String> responseBean;
            if ("123".equals(name) && "123".equals(pwd)) {
                responseBean = new LoginResponseBean<>(200, "登录成功", "欢迎光临！");
            } else {
                responseBean = new LoginResponseBean<>(400, "登录失败", null);
            }
            loginDataCallback.onLoginState(responseBean);
        }).start();

        // 方式二
//        ReqUserBean reqUserBean = new ReqUserBean(name, pwd);
//        Disposable disposable1 = Observable.just(reqUserBean)
//                .map(new Function<ReqUserBean, LoginResponseBean>() {
//                    @Override
//                    public LoginResponseBean apply(ReqUserBean reqUserBean) throws Throwable {
//                        SystemClock.sleep(3000);// 模拟耗时操作
//                        LoginResponseBean<String> responseBean;
//                        if ("xtm".equals(reqUserBean.getName()) && "123".equals(reqUserBean.getPwd())) {
//                            responseBean = new LoginResponseBean<>(200, "登录成功", "欢迎光临！");
//                        } else {
//                            responseBean = new LoginResponseBean<>(400, "登录失败", null);
//                        }
//                        return responseBean;
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<LoginResponseBean>() {
//                    @Override
//                    public void accept(LoginResponseBean loginResponseBean) throws Throwable {
//                        loginDataCallback.onLoginState(loginResponseBean);
//                    }
//                });

        // 方式三
//        Disposable disposable2 = Flowable.just(reqUserBean)
//                .map(new Function<ReqUserBean, LoginResponseBean>() {
//                    @Override
//                    public LoginResponseBean apply(ReqUserBean reqUserBean) {
//                        SystemClock.sleep(3000);// 模拟耗时操作
//                        LoginResponseBean<String> responseBean;
//                        if ("xtm".equals(reqUserBean.getName()) && "123".equals(reqUserBean.getPwd())) {
//                            responseBean = new LoginResponseBean<>(200, "登录成功", "欢迎光临！");
//                        } else {
//                            responseBean = new LoginResponseBean<>(400, "登录失败", null);
//                        }
//                        return responseBean;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<LoginResponseBean>() {
//                    @Override
//                    public void accept(LoginResponseBean loginResponseBean) {
//                        loginDataCallback.onLoginState(loginResponseBean);
//                    }
//                });
    }

    /**
     * 用于把数据回传给p层
     */
    public interface ILoginDataCallback {
        void onLoginState(LoginResponseBean<String> baseResponse);
    }

}