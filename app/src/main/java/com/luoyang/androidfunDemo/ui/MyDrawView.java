package com.luoyang.androidfunDemo.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.luoyang.androidfunDemo.R;

/**
 * @author luoyang
 * @date 2022/9/1
 */
public class MyDrawView extends View {

    private Paint paint;

    public MyDrawView(Context context) {
        super(context);
        init();
    }

    public MyDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint=new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.white));
        //画笔风格
        paint.setStyle(Paint.Style.FILL);
        //绘制文字大小，单位px
        paint.setTextSize(36);
        //画笔粗细
        paint.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画幕布
//        canvas.drawColor(getResources().getColor(R.color.base_tab_selected_color));

        //画圆
        canvas.drawCircle(getWidth()/4,getHeight()/4,100,paint);

        //画矩形
        canvas.drawRect(100,100,300,200,paint);

        //设置图片
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_right_back),
                750, 0, paint);

        //设置字体
        Paint paint1=new Paint();
        paint.setColor(getResources().getColor(R.color.purple_200));
        paint1.setTextSize(36);
        paint1.setTextAlign(Paint.Align.RIGHT);
        float baseLineY = getHeight() / 4 + Math.abs(paint.ascent() + paint.descent()) / 4;
        //绘制文字
        canvas.drawText("Canvas真好玩",getWidth()/4,baseLineY,paint1);

        //绘制路径
        Path path = new Path() ;
        path.moveTo(0,100);
        path.lineTo(100, 0) ;
        path.lineTo(200, 100) ;
        path.lineTo(300,200) ;
        path.lineTo(400, 100) ;
        path.close() ;
        //canvas . drawPath(path, paint);//绘制多边形//绘制文字
        canvas.drawTextOnPath("abcdefghijklmnopqrestuvwxyz", path, 350, 350, paint);
    }
}