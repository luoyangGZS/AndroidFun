package com.luoyang.androidfunDemo.filescan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.luoyang.androidfunDemo.MainApplication;
import com.luoyang.androidfunDemo.R;


public class DialogUtil {

    public static Dialog createDialog(Activity activity, String title, String message,
                                      int posTextId, int negaTextId, boolean cancelable,
                                      DialogInterface.OnClickListener onClickListenerPositive,
                                      DialogInterface.OnClickListener onClickListenerPositiveNegative) {

        AlertDialog.Builder ab = new AlertDialog.Builder(activity);

        if (null != title) {

            ab.setTitle(title);
        }

        if (null != message) {

            ab.setMessage(message);
        }

        ab.setCancelable(cancelable);

        if (null != onClickListenerPositive) {

            ab.setPositiveButton(posTextId, onClickListenerPositive);
        }

        if (null != onClickListenerPositiveNegative) {

            ab.setNegativeButton(negaTextId, onClickListenerPositiveNegative);
        }

        return ab.create();
    }


    public static ProgressDialog showProgressDialog(Activity activity, String text) {

        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static void showDialog(Context context, String title, String content) {
        if (context == null) {
            Log.e("DialogUtil", "showDialog activity is null ");
            return;

        }
        AlertDialog dialog = new AlertDialog.Builder(context)
                //设置标题的图片
//                .setIcon(R.mipmap.icon)
                //设置对话框的标题
                .setTitle(title)
                //设置对话框的内容l
                .setMessage(content)
                //设置对话框的按钮
                .setNegativeButton(MainApplication.getInstance().getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(MainApplication.getInstance().getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

}