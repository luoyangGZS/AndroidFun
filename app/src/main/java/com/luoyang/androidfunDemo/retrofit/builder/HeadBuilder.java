package com.luoyang.androidfunDemo.retrofit.builder;


import com.luoyang.androidfunDemo.retrofit.rerquest.OtherRequest;
import com.luoyang.androidfunDemo.retrofit.rerquest.RequestCall;
import com.luoyang.androidfunDemo.retrofit.rerquest.RequestManager;

public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, RequestManager.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
