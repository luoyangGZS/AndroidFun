package com.luoyang.androidfunDemo.notification

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.util.ToastUtil


class NotificationActivity : AppCompatActivity() {

    private lateinit var mParentConstrain: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        mParentConstrain = findViewById(R.id.parentConstrain)
    }

    /**
     * 验证结果：页面切换不会打断Toast
     * 验证结果：页面切换不会打断Toast
     * 验证结果：页面切换不会打断Toast
     * 验证结果：不能设置时间长短
     */
    @SuppressLint("WrongConstant")
    fun onToastClick(view: View) {
        when (view.id) {
            R.id.toast_one -> Toast.makeText(this, "我是一个简单的activity toast", Toast.LENGTH_LONG)
                .show()

            R.id.toast_two -> {
                var toast = Toast.makeText(this, "我是一个绑定activity，但切换界面的10s Toast", Toast.LENGTH_SHORT)
                toast.duration = 20000
                toast.show()
            }

            R.id.toast_three -> {
                ToastUtil.showShortToast("我是一个全局绑定的Toast toast_three")
            }
            R.id.toast_four -> {
                ToastUtil.showShortToast("我是一个全局绑定的Toast toast_four")
                Snackbar.make(mParentConstrain,"展示Snackbar",Snackbar.LENGTH_SHORT).show();
            }
            R.id.toast_five -> {
               Snackbar.make(mParentConstrain,"展示SnackBar的特点",Snackbar.LENGTH_SHORT).setAction("关闭",View.OnClickListener {
                   ToastUtil.showShortToast("点击了SnackBar的关闭按钮")
               }).show()
            }
            R.id.toast_six -> {
                showSnackBar()
            }

        }
    }

    @SuppressLint("RestrictedApi")
    fun showSnackBar(){
        val rootView = this.window.decorView
        val coordinatorLayout = rootView.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_SHORT)
// 上面的三行代码给咱们上面说过的是一样的，这里也就不添加注释了
// 获取到Snackbar.getView获取的Snackbar的view
// 上面的三行代码给咱们上面说过的是一样的，这里也就不添加注释了
// 获取到Snackbar.getView获取的Snackbar的view
        val snackbarView = snackbar.view as SnackbarLayout

        // 设置SnackbarView的padding都为0，避免上图中出现黑色边框背景的情况
        snackbarView.setPadding(0,0,0,0);
// 将SnackbarView的背景颜色设置为透明，避免在自定义布局中有圆角或者自适应宽度的时候显示一块黑色背景的情况
        snackbarView.setBackgroundColor(Color.TRANSPARENT);


        val layoutParams = snackbarView.layoutParams
// 新建一个LayoutParams将SnackbarView的LayoutParams的宽高传入
// 新建一个LayoutParams将SnackbarView的LayoutParams的宽高传入
        val fl = FrameLayout.LayoutParams(layoutParams.width, layoutParams.height)
// 设置新的元素位置
// Gravity有许多属性，基本上可以满足大众需求， 我们这里设置了处于屏幕的中央
// 设置新的元素位置
// Gravity有许多属性，基本上可以满足大众需求， 我们这里设置了处于屏幕的中央
        fl.gravity = Gravity.TOP
// 将新的LayoutParams设置给SnackbarView
// 将新的LayoutParams设置给SnackbarView
        snackbarView.layoutParams = fl


// 加载咱们自定义的布局
// 加载咱们自定义的布局
        val inflate: View =
            LayoutInflater.from(snackbar.view.context).inflate(R.layout.snacbar_layout, null)
// 通过自定义布局中的控件
// 通过自定义布局中的控件
        val text = inflate.findViewById<TextView>(R.id.textView)
// 设置自定义的文案
// 设置自定义的文案
        text.text = "自定义布局的Snackbar啦啦啦啦啦"
// 通过自定义布局中的控件
// 通过自定义布局中的控件
        val imageView = inflate.findViewById<ImageView>(R.id.imageView)
// 设置点击事件
// 设置点击事件
        imageView.setOnClickListener { v1: View? ->
            Log.d(
                "TAG",
                "点击了自定义布局中的控件"
            )
        }
// 将获取的自定义布局view添加到SnackbarView中
// 将获取的自定义布局view添加到SnackbarView中
        snackbarView.addView(inflate)
// 展示
// 展示
        snackbar.show()


    }
}