package com.luoyang.androidfunDemo.retrofit.builder;

import com.luoyang.androidfunDemo.retrofit.rerquest.PostStringRequest;
import com.luoyang.androidfunDemo.retrofit.rerquest.RequestCall;

import okhttp3.MediaType;

public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
