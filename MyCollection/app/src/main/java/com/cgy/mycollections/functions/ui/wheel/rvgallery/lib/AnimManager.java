package com.cgy.mycollections.functions.ui.wheel.rvgallery.lib;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.utils.L;


/**
 * @author RyanLee
 * @date 2017/12/12
 */

public class AnimManager {

    /**
     * 页面可见item数量,必须是奇数，偶数的话中间的item无法确定
     */
    private int mVisibleItemCount = 5;

    public static final int ANIM_BOTTOM_TO_TOP = 0;
    public static final int ANIM_TOP_TO_BOTTOM = 1;

    /**
     * 动画类型
     */
    private int mAnimType = ANIM_BOTTOM_TO_TOP;
    /**
     * 变化因子
     */
    private float mAnimFactor = 0.15f;

    public void setVisibleItemCount(int mVisibleItemCount) {
        this.mVisibleItemCount = mVisibleItemCount;
    }

    /**
     * 设置动画
     *
     * @param recyclerView
     * @param snapViewPosition 中心的view的位置 和 mVisibleItemCount 有关
     * @param percent
     */
    public void setAnimation(RecyclerView recyclerView, int snapViewPosition, float percent) {
        switch (mAnimType) {
            case ANIM_BOTTOM_TO_TOP:
                setBottomToTopAnim(recyclerView, snapViewPosition, percent);
                break;
            case ANIM_TOP_TO_BOTTOM:
                setTopToBottomAnim(recyclerView, snapViewPosition, percent);
                break;
//            default:
//                setBottomToTopAnim(recyclerView, snapViewPosition, percent);
//                break;
        }
    }


    /**
     * 从下到上的动画效果,需要把界面上显示的view全都设置一下
     *
     * @param recyclerView     RecyclerView
     * @param snapViewPosition int
     * @param percent          float
     */
    private void setBottomToTopAnim(RecyclerView recyclerView, int snapViewPosition, float percent) {
        // 中间页
        View mCurView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition);
        // 右边页
//        View mRightView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition + 1);
        // 左边页
//        View mLeftView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition - 1);
        // 右右边页
//        View mRRView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition + 2);

        L.d("AnimManager", "AnimManager setBottomToTopAnim snapViewPosition-->" + snapViewPosition);

        if (mCurView != null) {
            mCurView.setScaleX((1 + mAnimFactor));
            mCurView.setScaleY((1 + mAnimFactor));
        }

        int sideItemCount = mVisibleItemCount / 2+2;//中心view两边view的数量(+2表示两个未显示完全的左右的view)
        if (sideItemCount > 0) {
            for (int i = 0; i < sideItemCount; i++) {
                View leftView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition - 1 - i);
                View rightView = recyclerView.getLayoutManager().findViewByPosition(snapViewPosition + 1 + i);
                if (leftView != null) {
                    leftView.setScaleX(1);
                    leftView.setScaleY(1);
                }
                if (rightView != null) {
                    rightView.setScaleX(1);
                    rightView.setScaleY(1);
                }
            }
        }

//        if (mLeftView != null) {
////            mLeftView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
////            mLeftView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
//        }
//        if (mRightView != null) {
////            mRightView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
////            mRightView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
//        }
//        if (mRRView != null) {
//            mRRView.setScaleX(1 - percent * mAnimFactor);
//            mRRView.setScaleY(1 - percent * mAnimFactor);
//        }
    }


    /***
     * 从上到下的效果
     * @param recyclerView RecyclerView
     * @param position int
     * @param percent int
     */
    private void setTopToBottomAnim(RecyclerView recyclerView, int position, float percent) {
        // 中间页
        View mCurView = recyclerView.getLayoutManager().findViewByPosition(position);
        // 右边页
        View mRightView = recyclerView.getLayoutManager().findViewByPosition(position + 1);
        // 左边页
        View mLeftView = recyclerView.getLayoutManager().findViewByPosition(position - 1);
        // 左左边页
        View mLLView = recyclerView.getLayoutManager().findViewByPosition(position - 2);

        if (mLeftView != null) {
            mLeftView.setScaleX(1 - percent * mAnimFactor);
            mLeftView.setScaleY(1 - percent * mAnimFactor);
        }
        if (mCurView != null) {
            mCurView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
            mCurView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
        }
        if (mRightView != null) {
            mRightView.setScaleX(1 - percent * mAnimFactor);
            mRightView.setScaleY(1 - percent * mAnimFactor);
        }
        if (mLLView != null) {
            mLLView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
            mLLView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
        }
    }

    public void setAnimFactor(float mAnimFactor) {
        this.mAnimFactor = mAnimFactor;
    }

    public void setAnimType(int mAnimType) {
        this.mAnimType = mAnimType;
    }
}
