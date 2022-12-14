package com.luoyang.androidfunDemo.retrofit.rerquest;

import android.text.TextUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;


public class OtherRequest extends OkHttpRequest
{
    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private RequestBody requestBody;
    private final String method;
    private final String content;

    public OtherRequest(RequestBody requestBody, String content, String method, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;

    }

    @Override
    protected RequestBody buildRequestBody()
    {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method))
        {
            throw new IllegalArgumentException("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && !TextUtils.isEmpty(content))
        {
            requestBody = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        }

        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        if (method.equals(RequestManager.METHOD.PUT))
        {
            builder.put(requestBody);
        } else if (method.equals(RequestManager.METHOD.DELETE))
        {
            if (requestBody == null)
                builder.delete();
            else
                builder.delete(requestBody);
        } else if (method.equals(RequestManager.METHOD.HEAD))
        {
            builder.head();
        } else if (method.equals(RequestManager.METHOD.PATCH))
        {
            builder.patch(requestBody);
        }

        return builder.build();
    }

}
