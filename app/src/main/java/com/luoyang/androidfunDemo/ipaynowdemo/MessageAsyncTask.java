package com.luoyang.androidfunDemo.ipaynowdemo;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * 本质上也是一个线程池，所有的异步任务都会在这个线程池中的工作线程内执行，当工作线程需要跟Ui线程交互是，工作线程会通过向在ui线程创建的handler传递消息的方式，调用相关的回调函数，
 * 从而实现UI界面的更新。
 *
 * @author luoyang
 * @date 2022/9/7
 */
public class MessageAsyncTask extends AsyncTask<HashMap<String, String>, Integer, String> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(HashMap<String, String>... hashMaps) {
        if (callBack != null) {
            return callBack.inBackground(hashMaps);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (callBack != null) {
            callBack.postExecute(s);
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    private MessageCallBack callBack;

    void setCallBack(MessageCallBack callBack) {
        this.callBack = callBack;
    }

    interface MessageCallBack {
        String inBackground(HashMap<String, String>... hashMaps);

        void postExecute(String s);
    }
}
