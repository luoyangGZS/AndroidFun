package com.luoyang.androidfunDemo.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luoyang.androidfunDemo.R;

/**
 * @author luoyang
 */
public class LiveDataActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        initView();
        initData();
    }

    private void initView() {
//        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mTextView = findViewById(R.id.live_text);
        mButton = findViewById(R.id.live_button);
    }

    private void initData() {
//        final MutableLiveData<TestUserBean> listData = mUserViewModel.getMUserMutableLiveData();
        // 不绑定生命周期
        final MutableLiveData<TestUserBean> listData = UserViewModel.INSTANCE.getMUserMutableLiveData();
        mTextView.setText(println(listData));

        listData.observe(this, new Observer<TestUserBean>() {
            @Override
            public void onChanged(TestUserBean testUserBean) {
                mTextView.setText(println(listData));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listData.setValue(new TestUserBean("李斯", 23, 100));
            }
        });
    }

    private String println(MutableLiveData<TestUserBean> listData) {
        String str;
        if (listData.getValue() != null) {
            str = listData.getValue().toString();
        } else {
            str = "姓名无";
        }
        return str;
    }
}