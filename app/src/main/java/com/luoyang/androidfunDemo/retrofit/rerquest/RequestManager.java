package com.luoyang.androidfunDemo.retrofit.rerquest;


import android.util.Log;

import com.luoyang.androidfunDemo.retrofit.builder.GetBuilder;
import com.luoyang.androidfunDemo.retrofit.builder.HeadBuilder;
import com.luoyang.androidfunDemo.retrofit.builder.OtherRequestBuilder;
import com.luoyang.androidfunDemo.retrofit.builder.PostFormBuilder;
import com.luoyang.androidfunDemo.retrofit.builder.PostStringBuilder;
import com.luoyang.androidfunDemo.retrofit.callback.Callback;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class RequestManager {

    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static RequestManager mInstance;
    private OkHttpClient mOkHttpClient;
    private final Platform mPlatform;
    private String TAG = RequestManager.class.getSimpleName();

    public RequestManager(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public static RequestManager initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new RequestManager(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static RequestManager getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }


    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        if (tag == null) {
            return;
        }

        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                Log.d(TAG, "cancel queuedCalls ");
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                Log.d(TAG, "cancel runningCalls ");
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

