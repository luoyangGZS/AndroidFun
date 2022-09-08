package com.luoyang.androidfunDemo.retrofit

import android.content.Context
import android.text.TextUtils
import com.luoyang.androidfunDemo.Constant
import com.luoyang.androidfunDemo.util.LogUtil
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 *
 *
 * @author luoyang
 * @date 2022/7/23
 */
class NetInterceptor constructor(
    context: Context,
    showRequestLog: Boolean,
    showResponseLog: Boolean
) :
    Interceptor {
    val TAG = "NetShopInterceptor"

        //是否显示上传参数
    private var showRequestLog = showRequestLog

    //是否显示响应参数
    private var showResponseLog = showResponseLog
    private var context: Context = context


    override fun intercept(chain: Interceptor.Chain): Response? {
        var request = chain.request()
        var response: Response? = null
        //判断是get请求还是post请求
        val oldUrl = request.url().toString()
        LogUtil.d("oldUrl: " + oldUrl)
        var newUrl = ""
        var bodyStr: String? = ""
        if ("GET" == request.method()) {
            LogUtil.d("1111111111111111111")
            //get请求 添加请求的通用参数
            newUrl = CommonInfoProducer.getInstance(context).appendCommonParameter(oldUrl)
            LogUtil.d("newUrl" + newUrl)
            request = request.newBuilder().tag(Constant.VIEW_PATH).get().url(newUrl).build()
        } else {
            //拿到请求的body和url进行加密
            LogUtil.d("222222222222222222222222")
            val requestBody = request.body()
            LogUtil.d("333333333333333")
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        bodyStr = bodyToString(requestBody, mediaType)
                    }
                }
            }
            newUrl = if (!TextUtils.isEmpty(bodyStr)) {
                CommonInfoProducer.getInstance(context).appendCommonParameter(oldUrl, bodyStr)
            } else {
                CommonInfoProducer.getInstance(context).appendCommonParameter(oldUrl)
            }
            LogUtil.d("newUrl" + newUrl)
            request = request.newBuilder().url(newUrl).post(requestBody).build()
        }
        return try {

            response = chain.proceed(request)
            LogUtil.d(response.toString())
            var clone: Response? = null
            var content: String? = null
            if (response != null) {
                val code = response.code()
                val builder = response.newBuilder()
                clone = builder.build()
                if (clone.body() != null) {
                    content = clone.body()!!.string()
                }
            }
            //是否显示请求日志
            if (showRequestLog) {
                logForRequest(request)
            }
            LogForResponse(
                clone!!.newBuilder()
                    .body(ResponseBody.create(clone.body()!!.contentType(), content)).build()
            )
        } catch (e: Exception) {
            LogUtil.e(e.message)
            throw e
        }
    }

    private fun printResponseLog(msg: String) {
        //是否显示响应日志
        if (showResponseLog) {
            LogUtil.d(msg)
        }
    }

    /**
     * 响应参数
     * 只能使用 printResponseLog 输入日志
     *
     * @param response
     * @return
     */
    private fun LogForResponse(response: Response): Response? {
        try {
            printResponseLog("========Response(Start)=======")
            val builder = response.newBuilder()
            val clone = builder.build()
            printResponseLog("url : " + clone.request().url())
            printResponseLog("code : " + clone.code())
            printResponseLog("protocol : " + clone.protocol())
            if (!TextUtils.isEmpty(clone.message())) {
                printResponseLog("message : " + clone.message())
            }
            var body = clone.body()
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    printResponseLog("responseBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        val resp = body.string()
                        printResponseLog("responseBody's content: $resp")
                        body = ResponseBody.create(mediaType, resp)
                        printResponseLog("========Response(End)=======")
                        return response.newBuilder().body(body).build()
                    } else {
                        printResponseLog("responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            printResponseLog("========Response(End)=======")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return response
    }

    /**
     * 请求参数
     *
     * @param request
     */
    private fun logForRequest(request: Request) {
        val requestStr = StringBuffer()
        try {
            val url = request.url().toString()
            requestStr.append("\n========Request(Start)=======\n")
            requestStr.append(
                """
                Request->Method: ${request.method()}

                """.trimIndent()
            )
            requestStr.append("Request->URL:$url\n")
            val headers = request.headers()
            if (headers != null && headers.size() > 0) {
                requestStr.append("Request->headers:\n$headers")
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    //RequestStr.append("Request->mediaType:" + mediaType.toString() + "\n");
                    if (isText(mediaType)) {
                        requestStr.append(
                            """
                            Request->parameter:${bodyToString(requestBody, mediaType)}

                            """.trimIndent()
                        )
                    } else {
                        requestStr.append("Request->parameter: RequestBody's content : maybe [file part] , too large too print , ignored! \n\n")
                    }
                }
            }
            requestStr.append("========Request(End)=======")
            LogUtil.d(
                requestStr.toString()
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 响应数据的类型
     *
     * @param mediaType
     * @return
     */
    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() == "text") {
            return true
        }
        return mediaType.subtype() == "json" || mediaType.subtype() == "xml" || mediaType.subtype() == "html" || mediaType.subtype() == "webviewhtml"
    }

    /**
     * 获取请求内容
     *
     * @param requestBody
     * @param mediaType
     * @return
     */
    private fun bodyToString(requestBody: RequestBody, mediaType: MediaType): String? {
        return try {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = StandardCharsets.UTF_8
            charset = mediaType.charset(charset)
            buffer.readString(charset)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}