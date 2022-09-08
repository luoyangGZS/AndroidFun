package com.luoyang.androidfunDemo.filescan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.luoyang.androidfunDemo.R;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 扫描音频文件界面
 *
 * @author lixiongjun
 * @date 2022/10/20
 */
public class FileScanActivity extends Activity implements View.OnClickListener, IScanView, AudioFileAdapter.OnSelectAudioListener {

    public static final String PATHS = "paths";
    private ImageButton mBtnLeft;
    private Button mBtnRight;
    private Button mSmartButton;
    private RecyclerView mRecyclerContent;
    private ScanPresenterImpl mPresenter;
    private AudioFileAdapter mAudioAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_scan);
        initView();
        iniData();
    }

    private void initView() {
        mBtnLeft = findViewById(R.id.btn_title_left);
        mBtnRight = findViewById(R.id.btn_title_right);
        mSmartButton = findViewById(R.id.smart_button);
        mRecyclerContent = findViewById(R.id.content_recycler);
        mBtnLeft.setOnClickListener(this);

        mBtnRight.setOnClickListener(this);
        mBtnRight.setVisibility(View.INVISIBLE);

        mSmartButton.setOnClickListener(this);
        mRecyclerContent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void iniData() {
        mPresenter = new ScanPresenterImpl(this);
        mAudioAdapter = new AudioFileAdapter(this, this);
        mRecyclerContent.setAdapter(mAudioAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_left:
                finish();
                break;
            case R.id.btn_title_right:
                // 选中录音文件
                List<File> files = mAudioAdapter.getSelectData();
                finishWithResults(files);
                String text = String.format(getString(R.string.ready_sync_file), files.size());
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                break;
            case R.id.smart_button:
                //校验扫描录音文件
                checkPermissionsResult();
            default:
                break;
        }
    }

    /**
     * Finish this Activity with a result code and URI of the selected file.
     *
     * @param files The file selected.
     */
    private void finishWithResults(List<File> files) {
        Intent intent = new Intent();
        intent.putExtra(PATHS, (Serializable) files);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onShowProgressDialog(String msg) {
        progressDialog = DialogUtil.showProgressDialog(this, msg);
    }

    public void onCloseProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void scanStart() {
        onShowProgressDialog(getString(R.string.scanning_audio_file));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void addAudio(File newFile) {
        mAudioAdapter.addData(newFile);
        String text = String.format(getString(R.string.scan_result), mAudioAdapter.getItemCount());
        mSmartButton.setText(text);
    }

    @Override
    public void scanError(String message) {
        onCloseProgressDialog();
        Toast.makeText(this, getString(R.string.error_scan_audio_file), Toast.LENGTH_LONG).show();
    }

    @Override
    public void scanFinish() {
        onCloseProgressDialog();
        if (mSmartButton != null) {
            mSmartButton.setBackgroundColor(Color.WHITE);
            mSmartButton.setClickable(false);
        }
        mAudioAdapter.setCanCheck(true);
    }


    @Override
    public void selectAudioSize(int count) {
        if (count > 0) {
            mBtnRight.setText(String.format(getString(R.string.add_audio_count), count));
            mBtnRight.setVisibility(View.VISIBLE);
        } else {
            mBtnRight.setVisibility(View.INVISIBLE);
        }
    }

    public void checkPermissionsResult() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //已授权 扫描录音文件
            mPresenter.searchLocationAudioFile();
        }
    }

    /**
     * 无论是否同意，都会回调到onRequestPermissionsResult()方法中，授权结果封装在grantResults参数中，requestCode表示唯一的请求码
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫描录音文件
                    mPresenter.searchLocationAudioFile();
                } else {
                    Toast.makeText(this, getString(R.string.no_storage_permission), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

//    @Override
//    public void onBackPressed() {
//        // 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
//        onCloseProgressDialog();
//        finish();
//    }
}