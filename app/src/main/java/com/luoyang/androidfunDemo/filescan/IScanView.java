package com.luoyang.androidfunDemo.filescan;

import java.io.File;

/**
 * 扫描文件view
 *
 * @author lixiongjun
 * @date 2022/10/20
 */
public interface IScanView {

    /**
     * 扫描文件开始
     */
    void scanStart();

    /**
     * 添加音频文件
     *
     * @param newFile file
     */
    void addAudio(File newFile);

    /**
     * 扫描文件失败
     *
     * @param message 失败原因
     */
    void scanError(String message);

    /**
     * 扫描结束
     */
    void scanFinish();
}