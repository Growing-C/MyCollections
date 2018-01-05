package com.cgy.mycollections.functions.androiddesign.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecylerView的divider高度设置器
 * Created by RB-cgy on 2017/1/21.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    public SpaceItemDecoration(int spacePixel) {
        mSpace = spacePixel;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = mSpace;
    }

}
