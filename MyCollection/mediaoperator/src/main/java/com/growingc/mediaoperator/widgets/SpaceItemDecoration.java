package com.growingc.mediaoperator.widgets;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import appframe.utils.L;

/**
 * RecylerView的divider高度设置器
 * Created by RB-cgy on 2017/1/21.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    private Paint mPaint;

    public SpaceItemDecoration(int spacePixel) {
        mSpace = spacePixel;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#36818181"));
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //此方法 每增加一个view都会调用，导致 getChildCount 一直在变

        int childPos = parent.getChildAdapterPosition(view);
        if (childPos != 0)
            outRect.top = mSpace;

        if (parent.getAdapter() != null && childPos == parent.getAdapter().getItemCount() - 1) {
            L.e(childPos + "--");
            //最后一个要画个底部线
            outRect.bottom = mSpace;//这一行加不加似乎没影响
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                c.drawRect(0, child.getTop() - params.topMargin - mSpace, child.getRight(), child.getTop() - params.topMargin, mPaint);
            }
        }

        if (childCount < 1)
            return;

        final View child = parent.getChildAt(childCount - 1);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int position = params.getViewLayoutPosition();
        if (position > -1) {
            c.drawRect(0, child.getBottom() + params.bottomMargin, child.getRight(), child.getBottom() + params.bottomMargin + mSpace, mPaint);
        }
    }

}
