package com.luoyang.androidfunDemo.mvvm.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.luoyang.androidfunDemo.R;
import com.luoyang.androidfunDemo.databinding.ActivityVmBinding;
import com.luoyang.androidfunDemo.mvvm.model.LoginResponseBean;
import com.luoyang.androidfunDemo.mvvm.viewmodel.LoginViewModel;

public class MvvmActivity extends AppCompatActivity {

    private ActivityVmBinding mBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_vm);
        createViewModel();
        bindViewModelToLayout();
//        registerViewModelObserver();
        initData();
    }

    private void initData() {
        // 屏幕旋转，activity销毁重建，需要重新获取下保存在viewModel中的数据
        mBinding.etUserName.setText("" + (TextUtils.isEmpty(loginViewModel.inputName) ? "" : loginViewModel.inputName));
        mBinding.etPwd.setText("" + (TextUtils.isEmpty(loginViewModel.inputPwd) ? "" : loginViewModel.inputPwd));
    }

    /**
     * 创建viewModel
     */
    private void createViewModel() {
//        loginViewModel = new LoginViewModel(getApplication());绝对不可以直接去创建ViewModel的实例，而是一定要通过ViewModelProvider来获取ViewModel的实例
        loginViewModel = new ViewModelProvider(this, new CommonViewModelFactory(new LoginViewModel(getApplication()))).get(LoginViewModel.class);
    }

    /**
     * ViewModelProvider及工厂是有个Map集合存放viewModel对象，相当于把viewModel存入内存中
     */
    class CommonViewModelFactory implements ViewModelProvider.Factory {

        ViewModel viewModel;

        public CommonViewModelFactory(ViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) viewModel;
        }
    }


    /**
     * 点击登录按钮
     *
     * @param view
     */
    public void startLogin(View view) {
        loginViewModel.login(mBinding.etUserName.getText().toString(), mBinding.etPwd.getText().toString());
    }


    /**
     * 布局与viewModel绑定
     * 例如：xml中，android:text="@{loginViewModel.success}"
     */
    private void bindViewModelToLayout() {
        mBinding.setVariable(1, loginViewModel);// 传递viewModel对象绑定到布局，当然也可以绑定其他对象，需要与布局中data标签下的类型匹配
        mBinding.setLifecycleOwner(this);// 这里的参数类型为LifecycleOwner，把自己的生命周期告诉给已绑定对象的布局
        mBinding.executePendingBindings();// setVariable时候立即更新所有绑定对象的UI
    }

    /**
     * 向viewModel注册观察者
     * 监听viewModel获取到的数据，来更新UI(数据是主角)
     * 如果不想bindViewModelToLayout()，直接可以用这种方式监听viewModel数据来更新view
     * liveData的observe与observeForever的区别：前者自动，后者需要手动removeObserver
     */
    private void registerViewModelObserver() {
        // 登录返回BaseResponse监听  这里第一个参数 LifecycleOwner类型，把自己的生命周期告诉给viewModel
        loginViewModel.result.observe(this, new Observer<LoginResponseBean<String>>() {
            @Override
            public void onChanged(LoginResponseBean<String> stringLoginResponseBean) {
                // do nothing
            }
        });
        // 登录成功监听
        loginViewModel.success.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                mBinding.tvLoginState.setText(s);// 可以放到xml中监听
            }
        });
        // 登录失败监听
        loginViewModel.failed.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                mBinding.tvLoginState.setText(s);// 可以放到xml中监听
            }
        });
    }
}