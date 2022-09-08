package com.luoyang.androidfunDemo.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import com.luoyang.androidfunDemo.R;
import com.luoyang.base.base.BaseFragment;

/**
 * 首页
 *
 * @author luoyang
 * @date 2022/7/9
 */
public class AnimationFragment extends BaseFragment {

    private static String TAG = "AnimationFragment";
    private String mText = "home ";
    private String mTitle;
    private Button mStartButton;
    private ImageView imageView;
    AnimationDrawable mDrawable;
    private Button mStopButton;
    private Button mTranslateButton;
    private Button mScaleButton;
    private Button mRotateButton;
    private Button mAlphaButton;
    private Button mSetButton;
    private Button mInterpolatorButton;
    private Button mValueButton;
    private Button mObjectButton;


    public AnimationFragment(String title) {
        mTitle = title;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.animation_fragment;
    }

    @Override
    protected void loadData() {
        mText = "你好，安卓，home " + mTitle;
    }

    @Override
    public void initView() {

        mStartButton = (Button) findView(R.id.start_button);
        mStopButton = (Button) findView(R.id.stop_button);
        imageView = (ImageView) findView(R.id.image);
        mTranslateButton = (Button) findView(R.id.translate);
        mScaleButton = (Button) findView(R.id.scale);
        mRotateButton = (Button) findView(R.id.rotate);
        mAlphaButton = (Button) findView(R.id.alpha);
        mSetButton = (Button) findView(R.id.set);
        mInterpolatorButton = (Button) findView(R.id.interpolator);
        mValueButton = (Button) findView(R.id.value_animation);
        mObjectButton = (Button) findView(R.id.object_animation);

        // 使用ObjectAnimator
        toObjectAnimator(600);
        // 使用valueAnimator
        toValueAnimator(500);

        // 模拟插值动画
        toInterpolator();

        //组合动画
        toSetAnimation();
        //透明动画
        toAlphaAnimation();
        //旋转动画
        toRotateAnimation();
        //伸缩动画
        toScaleAnimation();
        // 平移动画
        toTranslateAnimation();
        //设置帧动画
        toDrawableAnimation();
    }

    private void toObjectAnimator(int widthValue) {
        //ObjectAnimator动画原理：如上图最后一步，根据属性值拼装成对应的set函数的名字
        // ，比如”scaleY”的拼接方法就是将属性的第一个字母强制大写后，与set拼接，也就是setScaleY,
        // 然后通过反射找到对应控件的setScaleY(float scaleY)函数，
        // 将当前数字值作为setScaleY(float scale)的参数将其传入。
        // 属性值得首字母大小写都可以，最终都会被强转成大写。View中都已经实现了相关的alpha rotation translate scale相关的set方法。

        ObjectAnimator objectAnimator = (ObjectAnimator) ObjectAnimator.ofInt(mObjectButton,"width", mObjectButton.getLayoutParams().width, widthValue);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofInt(mObjectButton,"backgroundColor",0xfff10f0f,0xff0f94f1,0xffeaf804,0xfff92a0f);
        objectAnimator2.setDuration(5000);
//        objectAnimator2.setEvaluator(new ArgbEvaluator());
        objectAnimator2.start();

    }

    private void toValueAnimator(int widthValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mValueButton.getLayoutParams().width, widthValue);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                mValueButton.getLayoutParams().width = currentValue;
                //刷新视图，重写绘制，从而实现动画效果
                mValueButton.requestLayout();
            }
        });
        valueAnimator.start();
    }

    private void toInterpolator() {
        // 2. 获得当前按钮的位置
        float curTranslationX = mInterpolatorButton.getTranslationX();

// 3. 创建动画对象 & 设置动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(mInterpolatorButton, "translationX", 150, 500,150);
// 表示的是:
// 动画作用对象是mButton
// 动画作用的对象的属性是X轴平移
// 动画效果是:从当前位置平移到 x=1500 再平移到初始位置

// 4. 设置步骤1中设置好的插值器：先减速后加速
        animator.setInterpolator(new DecelerateAccelerateInterpolator());

// 5. 启动动画
        animator.start();
//        mInterpolatorButton.startAnimation(animator);
    }


    private void toSetAnimation() {
        // 组合动画设置
        // 表示组合动画中的动画是否和集合共享同一个差值器
        // 如果集合不指定插值器，那么子动画需要单独设置
        AnimationSet animationSet = new AnimationSet(false);
        // 步骤1:创建组合动画对象(设置为true)

        // 步骤2:设置组合动画的属性
        // 特别说明以下情况
        // 因为在下面的旋转动画设置了无限循环(RepeatCount = INFINITE)
        // 所以动画不会结束，而是无限循环
        // 所以组合动画的下面两行设置是无效的
        animationSet.setRepeatMode(Animation.RESTART);
        animationSet.setRepeatCount(1);
        // 设置了循环一次,但无效

        // 步骤3:逐个创建子动画(方式同单个动画创建方式,此处不作过多描述)


        // 子动画2:平移动画
        Animation translate = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        translate.setDuration(10000);

        // 子动画3:透明度动画
        Animation alpha = new AlphaAnimation(1f, 0.5f);
        alpha.setDuration(3000);
        alpha.setStartOffset(7000);

        // 子动画1:旋转动画
        Animation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(Animation.RESTART);
//        rotate.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatCount(5);

        // 子动画4:缩放动画
        Animation scale1 = new ScaleAnimation(1, 0.5f, 1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(1000);
        scale1.setStartOffset(4000);

        // 步骤4:将创建的子动画添加到组合动画里
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(translate);
        animationSet.addAnimation(scale1);

        // 步骤5:播放动画
        mSetButton.startAnimation(animationSet);

        // 主要通过setAnimationListener()设置
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时回调
                Log.d(TAG, "animationSet onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束时回调
                Log.d(TAG, "animationSet onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复执行的时候回调
                Log.d(TAG, "animationSet onAnimationRepeat");
            }
        });

    }


    private void toAlphaAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.view_alpha);
        mAlphaButton.startAnimation(animation);

        // 主要通过setAnimationListener()设置
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时回调
                Log.d(TAG, "mAlphaButton onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束时回调
                Log.d(TAG, "mAlphaButton onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复执行的时候回调
                Log.d(TAG, "mAlphaButton onAnimationRepeat");
            }
        });
    }

    private void toRotateAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.view_rotate);
        mRotateButton.startAnimation(animation);
    }

    private void toScaleAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.view_scale);
        mScaleButton.startAnimation(animation);
    }


    private void toTranslateAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.view_translate);
        mTranslateButton.startAnimation(animation);
        final int[] count = {1};
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTranslateButton.setBackground(getResources().getDrawable(R.drawable.base_shape_msg));
                if (count[0] % 4 == 0) {
                    mTranslateButton.startAnimation(animation);
                    mTranslateButton.setBackground(getResources().getDrawable(R.drawable.zs_pb_large_frame_black_00));
                }
                count[0]++;
            }
        });
    }

    private void toDrawableAnimation() {
        // 1.设置动画
        imageView.setImageResource(R.drawable.play_anim);
        // 获取动画对象
        mDrawable = (AnimationDrawable) imageView.getDrawable();
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开始动画
                mDrawable.start();
                Log.d(TAG, "mDrawable.start");

                //开始value_animator动画
                toValueAnimator(500);
                toObjectAnimator(500);
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 结束动画
                mDrawable.stop();
                Log.d(TAG, "mDrawable.stop");
                //开始value_animator动画
                toValueAnimator(150);
                toObjectAnimator(150);
            }
        });
    }
}
