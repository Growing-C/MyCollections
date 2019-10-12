package com.cgy.mycollections.widgets.itemdecorations;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.R;


/**
 * 医保账户消费记录 月份头部
 * Created by RB-cgy on 2017/11/6.
 */
public class SectionItemDecoration extends RecyclerView.ItemDecoration {
    DecorationCallback mCallback;


    int mSectionHeight;
    int mTextBaseLine;
    int mLeftOffset;
    int mDividerHeight;

    TextPaint mTextPaint;
    Paint mDividerPaint;

    int mTextColorGrey, mTextColorRed;


    public SectionItemDecoration(Context context, DecorationCallback callback) {
        Resources res = context.getResources();
        this.mCallback = callback;

        mDividerPaint = new Paint();
        mDividerPaint.setColor(ContextCompat.getColor(context, R.color.grey_26818181));
        mTextColorGrey = ContextCompat.getColor(context, R.color.tx_color_666666);
        mTextColorRed = ContextCompat.getColor(context, R.color.color_red);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColorGrey);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(res.getDimensionPixelSize(R.dimen.px25_tx_size));
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mSectionHeight = res.getDimensionPixelSize(R.dimen.px36_common);//题头高度
        mLeftOffset = res.getDimensionPixelSize(R.dimen.px28_margin);//左边的距离
        mDividerHeight = res.getDimensionPixelSize(R.dimen.px2_size);//左边的距离

        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        //fontMetrics 的bottom和top等都是相对于baseline的（baseline为0）y轴正半轴向下，top在baseline上面所以是负的
        mTextBaseLine = (mSectionHeight - Math.abs(fontMetrics.bottom - fontMetrics.top)) / 2 + fontMetrics.bottom;//文字的baseline
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);

        if (isFirstInGroup(pos)) {//是分组第一个就预留 高度
            outRect.top = mSectionHeight;
        } else
            outRect.top = mDividerHeight;//为divider预留高度

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(childView);

            if (isFirstInGroup(pos)) {//是title
                String groupTitleStr = mCallback.getGroupTitle(pos);

                float top = childView.getTop() - mSectionHeight;
                float bottom = childView.getTop();

                c.drawRect(left, top, right, bottom, mDividerPaint);//绘制红色矩形
                if (pos != 0)//0位置不画上边线
                    c.drawLine(left, top, childView.getRight(), top + mDividerHeight, mDividerPaint);//上边线

                mTextPaint.setColor(mTextColorGrey);
                c.drawText(groupTitleStr, mLeftOffset + left, bottom - mTextBaseLine, mTextPaint);//绘制文本

            } else {//不是title
                float top = childView.getTop() - mDividerHeight;
                float bottom = childView.getTop();
//                c.drawLine(mLeftOffset + childView.getLeft(), top, childView.getRight(), bottom, mDividerPaint);
            }
        }
    }

    /**
     * 当前位置是不是第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0)
            return true;

        int preGroupId = mCallback.getGroupId(pos - 1);//前一个位置的groupId
        int currentGroupId = mCallback.getGroupId(pos);//当前位置的groupId

        return preGroupId != currentGroupId;
    }

    public interface DecorationCallback {
        int getGroupId(int pos);

        String getGroupTitle(int pos);
    }
}
