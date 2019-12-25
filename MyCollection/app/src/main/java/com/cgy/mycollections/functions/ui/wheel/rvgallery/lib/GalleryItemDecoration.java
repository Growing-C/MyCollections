package com.cgy.mycollections.functions.ui.wheel.rvgallery.lib;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import appframe.utils.L;


/**
 * @author RyanLee
 * @date 2017/12/14
 */

public class GalleryItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = "MainActivity_TAG";

    private int mVisibleItemCount = 5;

    /**
     * 每一个页面默认页边距
     */
    public int mPageMargin = 0;
    /**
     * 中间页面左右两边的页面可见部分宽度
     */
    public int mLeftPageVisibleWidth = 50;

    public int mItemConsumeY = 0;
    /**
     * 每个item 所占用的宽度（包括margin） 一般来说一旦算好就是固定的
     */
    public int mItemConsumeX = 0;

    private GalleryRecyclerView.OnItemClickListener onItemClickListener;

    private OnItemSizeMeasuredListener mOnItemSizeMeasuredListener;

    GalleryItemDecoration() {
    }

    public void setVisibleItemCount(int mVisibleItemCount) {
        this.mVisibleItemCount = mVisibleItemCount;
    }


    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        L.d(TAG, "GalleryItemDecoration getItemOffset() --> position = " + parent.getChildAdapterPosition(view));

        final int position = parent.getChildAdapterPosition(view);
        final int itemCount = parent.getAdapter().getItemCount();


        if (((GalleryRecyclerView) parent).getOrientation() == LinearLayoutManager.HORIZONTAL) {
            //view之间的margin通过outRect来设置，不再使用itemView的margin属性，因为itemView的margin在点击滚动view复用的时候会导致复用的view位置有问题
            // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
            int leftMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
            int rightMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);

            outRect.left = leftMargin;//为divider预留高度
            outRect.right = rightMargin;//为divider预留高度
        }
        parent.post(new Runnable() {
            @Override
            public void run() {
                if (((GalleryRecyclerView) parent).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    onSetHorizontalParams(parent, view, position, itemCount);
                } else {
                    onSetVerticalParams(parent, view, position, itemCount);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    private void onSetVerticalParams(ViewGroup parent, View itemView, int position, int itemCount) {
        int itemNewWidth = parent.getWidth();
        int itemNewHeight = parent.getHeight() - OsUtil.dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth);

        mItemConsumeY = itemNewHeight + OsUtil.dpToPx(2 * mPageMargin);

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener.onItemSizeMeasured(mItemConsumeY);
        }

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        int topMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
        int bottomMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);

        setLayoutParams(itemView, 0, topMargin, 0, bottomMargin, itemNewWidth, itemNewHeight);
    }

    /**
     * 设置水平滚动的参数
     *
     * @param parent    ViewGroup
     * @param itemView  View
     * @param position  int
     * @param itemCount int
     */
    private void onSetHorizontalParams(ViewGroup parent, View itemView, int position, int itemCount) {
        int itemNewWidth;
        //宽度等于 parent宽度 减去两边的 view可见宽度和两个margin 然后除以完全可见view个数 再减去两个margin 就是一个可见view的完全的宽度
        itemNewWidth = (parent.getWidth() - OsUtil.dpToPx(2 * mPageMargin + 2 * mLeftPageVisibleWidth)) / mVisibleItemCount - OsUtil.dpToPx(2 * mPageMargin);

        int itemNewHeight = parent.getHeight();

        int lastItemConsumeX = mItemConsumeX;
        mItemConsumeX = itemNewWidth + OsUtil.dpToPx(2 * mPageMargin);
        if (lastItemConsumeX != mItemConsumeX) {
            L.e(TAG, "GalleryItemDecoration onSetHorizontalParams consumeX 改变-->" +
                    "lastItemConsumeX=" + lastItemConsumeX + ";mItemConsumeX=" + mItemConsumeX);
        }

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener.onItemSizeMeasured(mItemConsumeX);
        }

        L.d(TAG, "GalleryItemDecoration onSetHorizontalParams -->" + "parent.width=" + parent.getWidth() + ";mPageMargin=" + OsUtil.dpToPx(mPageMargin)
                + ";mLeftVis=" + OsUtil.dpToPx(mLeftPageVisibleWidth) + ";itemNewWidth=" + itemNewWidth);

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
//        int leftMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
//        int rightMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);

//        setLayoutParams(itemView, leftMargin, 0, rightMargin, 0, itemNewWidth, itemNewHeight);
        setLayoutParams(itemView, 0, 0, 0, 0, itemNewWidth, itemNewHeight);
    }

    /**
     * 设置参数
     *
     * @param itemView   View
     * @param left       int
     * @param top        int
     * @param right      int
     * @param bottom     int
     * @param itemWidth  int
     * @param itemHeight int
     */
    private void setLayoutParams(View itemView, int left, int top, int right, int bottom, int itemWidth, int itemHeight) {

        L.d(TAG, "GalleryItemDecoration setLayoutParams -->" + "left=" + left + ";top=" + top
                + ";right=" + right + ";bottom=" + bottom + ";itemWidth=" + itemWidth + ";itemHeight=" + itemHeight);

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        boolean mMarginChange = false;
        boolean mWidthChange = false;
        boolean mHeightChange = false;

        //margin都写死成了0  所以这边应该不会走了
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
//            L.e(TAG, "setLayoutParams inital leftMargin:" + lp.leftMargin + "  lp.topMargin ：" + lp.topMargin
//                    + " lp.rightMargin：" + lp.rightMargin + " lp.bottomMargin：" + lp.bottomMargin);
//            L.e(TAG, "setLayoutParams new  left:" + left + "  top：" + top
//                    + " right：" + right + " bottom：" + bottom);
            lp.setMargins(left, top, right, bottom);
            mMarginChange = true;
        }
        if (lp.width != itemWidth) {
            L.e(TAG, "setLayoutParams    lp.width:" + lp.width + "  newWidth" + itemWidth);
            lp.width = itemWidth;
            mWidthChange = true;
        }
        if (lp.height != itemHeight) {
            L.e(TAG, "setLayoutParams    lp.height:" + lp.height + "  newHeight：" + itemHeight);
            lp.height = itemHeight;
            mHeightChange = true;

        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            L.e(TAG, "setLayoutParams real invoke!! mMarginChange:" + mMarginChange + " mWidthChange：" + mWidthChange
                    + " mHeightChange：" + mHeightChange);
            itemView.setLayoutParams(lp);
        }
    }

    public void setOnItemClickListener(GalleryRecyclerView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSizeMeasuredListener(OnItemSizeMeasuredListener itemSizeMeasuredListener) {
        this.mOnItemSizeMeasuredListener = itemSizeMeasuredListener;
    }

    interface OnItemSizeMeasuredListener {
        /**
         * Item的大小测量完成
         *
         * @param size int
         */
        void onItemSizeMeasured(int size);
    }
}
