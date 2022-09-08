package com.luoyang.androidfunDemo.retrofit.show.okhttp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int FAIL_QUEST = 406;//无法使用请求的内容特性来响应请求的网页
    private static final int BAD_REQUEST = 400;//

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
//        if (e instanceof HttpException) {
//            HttpException httpException = (HttpException) e;
//            ex = new ResponseThrowable(e, Constant.HTTP_ERROR);
//            switch (httpException.code()) {
//                case UNAUTHORIZED:
//                    break;
//                case FORBIDDEN:
//                    ex.message = "服务器已经理解请求，但是拒绝执行它";
//                    break;
//                case NOT_FOUND:
//                    ex.message = "服务器异常，请稍后再试(" + NOT_FOUND + ")";
//                    break;
//                case REQUEST_TIMEOUT:
//                    ex.message = "请求超时";
//                    break;
//                case BAD_REQUEST://request error
//                    ex.message = "错误的请求";
//                    break;
//                case GATEWAY_TIMEOUT:
//                case INTERNAL_SERVER_ERROR:
//                case BAD_GATEWAY:
//                case SERVICE_UNAVAILABLE:
//                case FAIL_QUEST:
//                    ex.message = "服务器无法完成对请求的处理(" + httpException.code() + ")";
//                    break;
//                default:
//                    ex.message = "网络错误";
//                    break;
//            }
//            return ex;
//        } else if (e instanceof JsonParseException
//                || e instanceof JSONException
//                || e instanceof ParseException) {
//            ex = new ResponseThrowable(e, Constant.PARSE_ERROR);
//            ex.message = "解析错误";
//            return ex;
//        } else if (e instanceof ConnectException) {
//            ex = new ResponseThrowable(e, Constant.NETWORD_ERROR);
//            ex.message = "连接失败";
//            return ex;
//        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
//            ex = new ResponseThrowable(e, Constant.SSL_ERROR);
//            ex.message = "证书验证失败";
//            return ex;
//        } else if (e instanceof java.net.SocketTimeoutException) {
//            ex = new ResponseThrowable(e, Constant.TIMEOUT_ERROR);
//            ex.message = "当前网络连接不顺畅，请稍后再试";
//            return ex;
//        } else if ((e instanceof java.net.UnknownHostException) || (e instanceof java.net.SocketException)) {
//            ex = new ResponseThrowable(e, Constant.NETWORD_ERROR);
//            ex.message = "网络异常，请检查网络状态";
//            return ex;
//        } else if (e instanceof javax.net.ssl.SSLException) {
//            ex = new ResponseThrowable(e, Constant.TIMEOUT_ERROR);
//            ex.message = "网络中断，请检查网络状态";
//            return ex;
//        } else if (e instanceof java.io.EOFException) {
//            ex = new ResponseThrowable(e, Constant.NUMBERFORMAT_ERROR);
//            ex.message = "io异常";
//            return ex;
//        } else if (e instanceof NullPointerException) {
//            ex = new ResponseThrowable(e, Constant.NUMBERFORMAT_ERROR);
//            ex.message = "数据为空,显示失败";
//            return ex;
//        } else {
            ex = new ResponseThrowable(e, -2);
            ex.message = "服务器错误";
            return ex;
//        }
    }


    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        public ResponseThrowable(String message, int code) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        @Nullable
        @Override
        public String getMessage() {
            return message;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseThrowable{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
