package com.luoyang.androidfunDemo.filescan;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件扫描presenter
 *
 * @author lixiongjun
 * @date 2022/10/20
 */
public class ScanPresenterImpl {
    private static final String TAG = "ScanPresenterImpl";
    private IScanView mView;

    public ScanPresenterImpl(IScanView view) {
        mView = view;
    }

    /**
     * 扫描音频文件
     */
    public void searchLocationAudioFile() {
        if (mView != null) {
            mView.scanStart();
        }
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    searchAudio(e, new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
                }
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<File>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(File value) {
                mView.addAudio(value);
            }

            @Override
            public void onComplete() {
                mView.scanFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.scanError(e.getMessage());
                Log.e(TAG, "onError Throwable: " + e.getMessage());
            }
        });
    }

    private void searchAudio(ObservableEmitter<File> e, File parentFile) {
        if (null != parentFile && parentFile.listFiles().length > 0) {
            File[] childFiles = parentFile.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].isFile() && checkAudio(childFiles[i])) {
                    e.onNext(childFiles[i]);
                    continue;
                }
                if (childFiles[i].isDirectory() && childFiles[i].listFiles().length > 0) {
                    searchAudio(e, childFiles[i]);
                }
            }
        }
    }


    private boolean checkAudio(File file) {
        // 是否文件且大于10kb
        if (file == null || file.length() < 10 * 1024) {
            return false;
        }
        String fileName = file.getName();
        return fileName.endsWith(".mp3")
                || fileName.endsWith(".wav")
                || fileName.endsWith(".wma")
                || fileName.endsWith(".mp2")
                || fileName.endsWith(".flac")
                || fileName.endsWith(".midi")
                || fileName.endsWith(".ra")
                || fileName.endsWith(".ape")
                || fileName.endsWith(".aac")
                || fileName.endsWith(".cda")
                || fileName.endsWith(".mov")
                || fileName.endsWith(".m4a");
    }

    public void onDestroy() {
        mView = null;
    }
}