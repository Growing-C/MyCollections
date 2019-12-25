package com.cgy.mycollections.functions.ui.wheel.rvgallery.lib;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import appframe.utils.L;


/**
 * @author RyanLee
 * @date 2017/12/8
 */

public class ScrollManager {
    private static final String TAG = "ScrollManager";

    private GalleryRecyclerView mGalleryRecyclerView;

//    private int mFirstVisiblePosition = 0;

    private int mSelectedPosition = 0;

    private OnItemSelectedListener mItemSelectedListener;

    /**
     * x方向消耗距离，使偏移量为左边距 + 左边Item的可视部分宽度 此数据会一直叠加 除非调用 updateConsume
     */
    private int mConsumeX = 0;
    private int mConsumeY = 0;
    LinearSnapHelper mLinearSnapHelper;
//    int mSelectedItem = -1;//选中的 view

    ScrollManager(GalleryRecyclerView mGalleryRecyclerView) {
        this.mGalleryRecyclerView = mGalleryRecyclerView;
    }


    public void setItemSelectedListener(OnItemSelectedListener mItemSelectedListener) {
        this.mItemSelectedListener = mItemSelectedListener;
    }

    /**
     * 初始化SnapHelper
     *
     * @param helper int
     */
    public void initSnapHelper(int helper) {
        switch (helper) {
            case GalleryRecyclerView.LINEAR_SNAP_HELPER:
                mLinearSnapHelper = new LinearSnapHelper();
                mLinearSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            case GalleryRecyclerView.PAGER_SNAP_HELPER:
                PagerSnapHelper mPagerSnapHelper = new PagerSnapHelper();
                mPagerSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            default:
                break;
        }
    }

    /**
     * 监听RecyclerView的滑动
     */
    public void initScrollListener() {
        GalleryScrollerListener mScrollerListener = new GalleryScrollerListener();
        mGalleryRecyclerView.addOnScrollListener(mScrollerListener);
    }

    /**
     * 更新已滚动距离
     */
    public void updateConsume() {
        mConsumeX += OsUtil.dpToPx(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
        mConsumeY += OsUtil.dpToPx(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
        L.d(TAG, "ScrollManager updateConsume mConsumeX=" + mConsumeX);
    }

    class GalleryScrollerListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            L.d(TAG, "ScrollManager newState=" + newState);
            super.onScrollStateChanged(recyclerView, newState);
            //点击item 调用scrollToPositionWithOffset 不会走 这里
            //这里目前应该只有手动滑动的时候会走
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            DLog.d(TAG, "ScrollManager onScrolled dx=" + dx);
            if (mGalleryRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                L.d(TAG, "ScrollManager onScrolled dx=" + dx + "  state:" + mGalleryRecyclerView.getScrollState());
                //只有不是滚动停止状态才可以修改中心view，即只有手动滑动才走下面的方法， 调用scrollToPositionWithOffset 不会走 这里
                if (mGalleryRecyclerView.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    onHorizontalScroll(recyclerView, dx);
                } else {
                    onVerticalScroll(recyclerView, dy);
                }
            }
        }
    }

    /**
     * 选中某个item,只做放大动作，不滚动
     */
    public void selectItem(int position) {
        //说明选中的view无法滚动到中心
        L.i(TAG, "ScrollManager selectItem   position=" + position);
        mSelectedPosition = position;//手动选中的位置
        mGalleryRecyclerView.getAnimManager().setAnimation(mGalleryRecyclerView, position, 0);
    }

    /**
     * 垂直滑动
     *
     * @param recyclerView RecyclerView
     * @param dy           int
     */
    private void onVerticalScroll(final RecyclerView recyclerView, int dy) {
        mConsumeY += dy;

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemHeight的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeY = mGalleryRecyclerView.getDecoration().mItemConsumeY;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeY / (float) shouldConsumeY;
                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);

                mSelectedPosition = (int) offset;

                L.i(TAG, "ScrollManager offset=" + offset + "; mConsumeY=" + mConsumeY + "; shouldConsumeY=" + mSelectedPosition);

                // 设置动画变化
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, mSelectedPosition, percent);
            }
        });
    }

    /**
     * 水平滑动
     *
     * @param recyclerView RecyclerView
     * @param dx           int
     */
    private void onHorizontalScroll(final RecyclerView recyclerView, final int dx) {
        mConsumeX += dx;//莫得用了

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemWidth的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeX = mGalleryRecyclerView.getDecoration().mItemConsumeX;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
//                float offset = (float) mConsumeX / (float) shouldConsumeX;

                // 获取当前页移动的百分值
//                float percent = offset - ((int) offset);

//                mFirstVisiblePosition = (int) offset;//页面上显示的第一个item的位置

//                DLog.i(TAG, "ScrollManager offset=" + offset + "; percent=" + percent + "; mConsumeX=" + mConsumeX + "; shouldConsumeX=" + shouldConsumeX + "; position=" + mFirstVisiblePosition);
                L.i(TAG, "ScrollManager ; mConsumeX=" + mConsumeX + "; shouldConsumeX=" + shouldConsumeX + "; mSelectedPosition=" + mSelectedPosition);

                View snapView = mLinearSnapHelper.findSnapView(mGalleryRecyclerView.getLayoutManager());
                int snapViewPosition = -1;
                if (snapView != null) {
                    snapViewPosition = mGalleryRecyclerView.getChildAdapterPosition(snapView);

                    L.i(TAG, "ScrollManager  snapView position=" + snapViewPosition);

                }

                mSelectedPosition = snapViewPosition;//滚动的时候 默认中心view为选中位置

                // 设置动画变化,中心的view放大 其他的缩小
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, snapViewPosition, 0);

                if (mItemSelectedListener != null)
                    mItemSelectedListener.onItemSelected(snapViewPosition);
            }
        });

    }

    public int getPosition() {
        return mSelectedPosition;
    }
}
