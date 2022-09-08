package com.luoyang.androidfunDemo.retrofit.builder;

import com.luoyang.androidfunDemo.retrofit.rerquest.OkHttpRequest;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;


public class GetRequest extends OkHttpRequest
{
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.get().build();
    }


}
