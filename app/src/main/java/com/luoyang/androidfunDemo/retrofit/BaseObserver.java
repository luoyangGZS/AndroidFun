package com.luoyang.androidfunDemo.retrofit;

import android.text.TextUtils;


import com.luoyang.androidfunDemo.bean.BaseHttpResult;
import com.luoyang.androidfunDemo.retrofit.show.okhttp.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 商店2.0 服务器返回的数据格式
 *
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseHttpResult> {
    private String TAG = "BaseObserver";
    //缓存的Key-->CacheKeyEnum.class
    private final String mCacheKey;
    //是否需要缓存数据
    private final boolean isNeedCache;
    //是否保存服务器返回数据到文件
    private final boolean isSaveResultData;
    //网络的请求数据是否返回
    private boolean isNetReturn = false;
    //设置服务器返回“1005” errorCode的时候 拉起登录
    private boolean isNeedLogin = false;

    public BaseObserver(String cacheKey, boolean isNeedCache, boolean isSaveResultData) {
        this.mCacheKey = cacheKey;
        this.isNeedCache = isNeedCache;
        this.isSaveResultData = isSaveResultData;
    }

    public BaseObserver<T> setNeedLogin(boolean needLogin) {
        this.isNeedLogin = needLogin;
        return this;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
//        CoolLogger.i(TAG, "onSubscribe");
        onDisposable(disposable);
        if (isNeedCache && !TextUtils.isEmpty(mCacheKey)) {
            //需要缓存数据
            returnCacheData();
        }
    }

    @Override
    public void onNext(BaseHttpResult result) {
//        CoolLogger.i(TAG, "onNext:" + result.isSuccess());
        if (result.isSuccess()) {
            onSuccess(result, false);
        } else {
//            if (isNeedLogin && (result.getErrCode().equals(String.valueOf(Constant.UNAUTHORIZED))
//                    || result.getErrCode().equals(String.valueOf(Constant.UNAUTHORIZED_TWO)))) {
//                login();
//            } else {
//                int errCode = CommonUtils.isInt(result.getErrCode()) ? Integer.parseInt(result.getErrCode()) : Constant.UNKNOWN;
//                onFail(new ExceptionHandle.ResponseThrowable(result.getErrMessage(), errCode));
//            }
        }
        isNetReturn = true;
        if (isSaveResultData && !TextUtils.isEmpty(mCacheKey) && result.isSuccess()) {
            saveResultData(result);
        }
    }

    @Override
    public void onError(Throwable t) {
//        CoolLogger.e(TAG, "onError:", t);
//        onFail(ExceptionHandle.handleException(t));
    }

    @Override
    public void onComplete() {
//        CoolLogger.i(TAG, "onComplete");
        onCompleted();
    }

    /**
     * 返回缓存数据
     */
    private void returnCacheData() {
//        ThreadPoolUtil.post(() -> {
//            try {
//                String cacheData = CoolmartCache.getAsString(mCacheKey);
//                CoolLogger.i(TAG, "returnCacheData cacheData:" + cacheData);
//                if (!TextUtils.isEmpty(cacheData)) {
//                    //如果请求网络的数据已经比缓存的先返回了 就不继续返回缓存数据了
//                    if (!isNetReturn) {
//                        Type responseType;
//                        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
//                            Type parameterizedType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//                            responseType = new GsonObjectTypeToken(BaseHttpResult.class, new Type[]{parameterizedType}, BaseHttpResult.class);
//                        } else {
//                            responseType = BaseHttpResult.class;
//                        }
//                        BaseHttpResult<T> cacheResponse = GsonUtils.fromJson(cacheData, responseType);
//                        MainApplication.getInstance().mMainHandler.post(() -> {
//                            //onCacheSuccess(cacheData);
//                            onSuccess(cacheResponse, true);
//                            CoolLogger.i(TAG, "returnCacheData ok:");
//                        });
//                    }
//                }
//            } catch (Exception e) {
//                CoolLogger.e(TAG, "returnCacheData Exception:", e);
//            }
//        });
    }

    /**
     * 保存请求成功的数据
     *
     * @param result
     */
    private void saveResultData(BaseHttpResult result) {
//        ThreadPoolUtil.post(() -> {
//            try {
//                CoolmartCache.putToCache(mCacheKey, GsonUtils.toJson(result));
//                CoolLogger.i(TAG, "saveResultData ok! key:" + mCacheKey);
//            } catch (Exception e) {
//                CoolLogger.e(TAG, "saveResultData Exception:", e);
//            }
//        });
    }

    /**
     * 登录
     */
    private void login() {
//        CPAccountUtils.getInstance().getLoginUserInfo(new CPAccountUtils.OnUserInfoNetListener() {
//            @Override
//            public void onGetUserInfoSuccess(String coolID, CPUserInfo userInfo) {
//                CPAccountUtils.getInstance().notifyAfreshLogin(coolID, userInfo);
//            }
//
//            @Override
//            public void onGetUserInfoError(String errorCode, String errorMessage) {
//
//            }
//
//            @Override
//            public void onUserCancelLogin() {
//                CPAccountUtils.getInstance().notifyLogStatusChanged(false, false);
//            }
//        });
    }

    /**
     * 开始
     */
    public abstract void onDisposable(Disposable d);

    /**
     * 请求成功 (isDataFromCache 数据来源: true:本地缓存 false:网络-服务器)
     */
    public abstract void onSuccess(BaseHttpResult result, boolean isDataFromCache);


    /**
     * 请求失败
     */
    public abstract void onFail(ExceptionHandle.ResponseThrowable e);

    /**
     * 请求完成
     */
    public abstract void onCompleted();
}
