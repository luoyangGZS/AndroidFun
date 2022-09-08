package com.luoyang.note;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * 分割线
 *
 * @author luoyang
 * @date 2022/7/13
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation;
    private int padding = 10;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public DividerItemDecoration(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            if (childAdapterPosition < itemCount - 1) {
                outRect.set(0, 0, padding, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else {
            if (childAdapterPosition < itemCount - 1) {
                outRect.set(0, 0, 0, padding);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Paint paint = new Paint();
        //列表TextView的颜色
        paint.setColor(Color.WHITE);
        //获取列表数量
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            //最后一个底下不需要分割线
            if (i == count - 1) {
                c.drawRect(child.getLeft(), child.getTop(), child.getRight(),
                        child.getBottom() + 10, paint);
            } else {
                c.drawRect(child.getLeft(), child.getTop(), child.getRight(),
                        child.getBottom(), paint);
            }
        }
    }
}
