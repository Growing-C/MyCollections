package com.cgy.mycollections.widgets.itemdecorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
//import androidx.appcompat.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

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
        mPaint.setColor(Color.parseColor("#c0c0c0"));
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = mSpace;
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
    }
}
