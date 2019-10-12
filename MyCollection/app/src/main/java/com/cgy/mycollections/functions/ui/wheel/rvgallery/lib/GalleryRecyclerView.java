package com.cgy.mycollections.functions.ui.wheel.rvgallery.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;


/**
 * @author RyanLee
 * @date 2017/12/8
 */

public class GalleryRecyclerView extends RecyclerView implements View.OnTouchListener, GalleryItemDecoration.OnItemSizeMeasuredListener {

    private static final String TAG = "GalleryRecyclerView";

    public static final int LINEAR_SNAP_HELPER = 0;
    public static final int PAGER_SNAP_HELPER = 1;
    /**
     * 滑动速度
     */
    private int mFlingSpeed = 1000;
    /**
     * 是否自动播放
     */
    private boolean mAutoPlay = false;
    /**
     * 自动播放间隔时间
     */
    private int mInterval = 1000;

    /**
     * 初始第一个可见view的位置
     */
    private int mInitFirstVisiblePos = -1;

    private AnimManager mAnimManager;
    private ScrollManager mScrollManager;
    private GalleryItemDecoration mDecoration;

    private OnItemSelectedListener mItemSelectedListener;
    private int mVisibleItemCount = 5;
    /**
     * 自动播放task
     */
    private Runnable mAutoPlayTask = new Runnable() {
        @Override
        public void run() {
            if (getAdapter() == null || getAdapter().getItemCount() <= 0) {
                return;
            }

            int position = getScrolledPosition();
            int itemCount = getAdapter().getItemCount();

            int newPosition = (position + 1) % itemCount;
            smoothScrollToPosition(newPosition);

            ThreadUtils.removeCallbacks(this);
            ThreadUtils.runOnUiThread(this, mInterval);
        }
    };

    public GalleryItemDecoration getDecoration() {
        return mDecoration;
    }

    public AnimManager getAnimManager() {
        return mAnimManager;
    }

    public GalleryRecyclerView(Context context) {
        this(context, null);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GalleryRecyclerView);
        int helper = ta.getInteger(R.styleable.GalleryRecyclerView_helper, LINEAR_SNAP_HELPER);
        ta.recycle();

        L.d(TAG, "GalleryRecyclerView constructor");

        mAnimManager = new AnimManager();
        attachDecoration();
        attachToRecyclerHelper(helper);

        //设置触碰监听
        setOnTouchListener(this);

    }

    private void attachDecoration() {
        L.d(TAG, "GalleryRecyclerView attachDecoration");

        mDecoration = new GalleryItemDecoration();
        mDecoration.setOnItemSizeMeasuredListener(this);
        addItemDecoration(mDecoration);
    }


    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX = balanceVelocity(velocityX);
        velocityY = balanceVelocity(velocityY);
        return super.fling(velocityX, velocityY);
    }

    /**
     * 返回滑动速度值
     *
     * @param velocity int
     * @return int
     */
    private int balanceVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, mFlingSpeed);
        } else {
            return Math.max(velocity, -mFlingSpeed);
        }
    }

    /**
     * 连接RecyclerHelper
     *
     * @param helper int
     */
    private void attachToRecyclerHelper(int helper) {
        L.d(TAG, "GalleryRecyclerView attachToRecyclerHelper");

        mScrollManager = new ScrollManager(this);
        mScrollManager.initScrollListener();
        mScrollManager.initSnapHelper(helper);
    }

    /**
     * 设置页面参数，单位dp
     *
     * @param pageMargin           默认：0dp
     * @param leftPageVisibleWidth 默认：50dp
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initPageParams(int pageMargin, int leftPageVisibleWidth) {
        mDecoration.mPageMargin = pageMargin;
        mDecoration.mLeftPageVisibleWidth = leftPageVisibleWidth;
        return this;
    }

    /**
     * 设置滑动速度（像素/s）
     *
     * @param speed int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initFlingSpeed(@IntRange(from = 0) int speed) {
        this.mFlingSpeed = speed;
        return this;
    }

    public GalleryRecyclerView setItemSelectedListener(OnItemSelectedListener mItemSelectedListener) {
        this.mItemSelectedListener = mItemSelectedListener;
        mScrollManager.setItemSelectedListener(mItemSelectedListener);
        return this;
    }

    public GalleryRecyclerView setVisibleItemCount(int visibleItemCount) {
        if (visibleItemCount % 2 != 1)
            throw new IllegalArgumentException("visibleItemCount must be odd number");

        this.mVisibleItemCount = visibleItemCount;

        mAnimManager.setVisibleItemCount(mVisibleItemCount);
        mDecoration.setVisibleItemCount(mVisibleItemCount);
        return this;
    }

    /**
     * 设置动画因子
     *
     * @param factor float
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setAnimFactor(@FloatRange(from = 0f) float factor) {
        mAnimManager.setAnimFactor(factor);
        return this;
    }

    /**
     * 设置动画类型
     *
     * @param type int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setAnimType(int type) {
        mAnimManager.setAnimType(type);
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param mListener OnItemClickListener
     */
    public GalleryRecyclerView setOnItemClickListener(OnItemClickListener mListener) {
        if (mDecoration != null) {
            mDecoration.setOnItemClickListener(mListener);
        }
        return this;
    }

    /**
     * 是否自动滚动
     *
     * @param auto boolean
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView autoPlay(boolean auto) {
        this.mAutoPlay = auto;
        return this;
    }

    /**
     * 自动播放
     */
    private void autoPlayGallery() {
        if (mAutoPlay) {
            ThreadUtils.removeCallbacks(mAutoPlayTask);
            ThreadUtils.runOnUiThread(mAutoPlayTask, mInterval);
        }
    }

    /**
     * 移除自动播放Runnable
     */
    private void removeAutoPlayTask() {
        if (mAutoPlay) {
            ThreadUtils.removeCallbacks(mAutoPlayTask);
        }
    }

    /**
     * 装载
     *
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setUp() {
        if (getAdapter().getItemCount() <= 0) {
            return this;
        }

        smoothScrollToPosition(0);
        mScrollManager.updateConsume();

        autoPlayGallery();

        return this;
    }

    /**
     * 释放资源
     */
    public void release() {
        removeAutoPlayTask();
    }

    /**
     * 选中某个item,只为点击和初始化到指定位置使用
     */
    public void selectItem(int position) {
        LinearLayoutManager manager = ((LinearLayoutManager) getLayoutManager());
        int firstVisiblePosition = manager.findFirstCompletelyVisibleItemPosition();
        int newFirstVisibleView = position - mVisibleItemCount / 2;

        int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
        int newLastVisibleView = position + mVisibleItemCount / 2;

        L.w(TAG, "GalleryRecyclerView selectItem firstVisiblePosition：" + firstVisiblePosition
                + "  newFirstVisibleView:" + newFirstVisibleView
                + "  lastVisiblePosition:" + lastVisiblePosition
                + "  newLastVisibleView:" + newLastVisibleView
                + "  getItemCount:" + getAdapter().getItemCount()
        );
        boolean canScroll = true;
        //不可以滚动的情况
        //1.老的首尾相同的话就不可以滚动（为了适配初始化的时候 选择第一个的情况，改为可以滚动）
        //2.position在无法滚动到中心的位置的时候不可滚动
        if (newFirstVisibleView < 0 || newLastVisibleView >= getAdapter().getItemCount()) {
            canScroll = false;
        }

        //先放大再说
        mScrollManager.selectItem(position);

        if (canScroll) {

//            manager.smoothScrollToPosition(newFirstVisibleView, 0);//此方法只能保证目标位置在屏幕上，不一定是第一个

            // 无论如何 第一个完全可见的view左边 都肯定是mLeftPageVisibleWidth加上两个margin的偏移量
            int leftMargin = OsUtil.dpToPx(mDecoration.mLeftPageVisibleWidth + 2 * mDecoration.mPageMargin);
            L.w(TAG, "GalleryRecyclerView selectItem scrollToPositionWithOffset  leftMargin：" + leftMargin);
            manager.scrollToPositionWithOffset(newFirstVisibleView, leftMargin); //此方法能将目标view滚动到第一个
        }
        //其他情况 说明选中的view无法滚动到中心,此时直接放大选中的view


        if (mItemSelectedListener != null)
            mItemSelectedListener.onItemSelected(position);
    }

    public int getOrientation() {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            if (getLayoutManager() instanceof GridLayoutManager) {
                throw new RuntimeException("请设置LayoutManager为LinearLayoutManager");
            } else {
                return ((LinearLayoutManager) getLayoutManager()).getOrientation();
            }
        } else {
            throw new RuntimeException("请设置LayoutManager为LinearLayoutManager");
        }
    }

    public int getScrolledPosition() {
        if (mScrollManager == null) {
            return 0;
        } else {
            return mScrollManager.getPosition();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        L.w(TAG, "GalleryRecyclerView onSaveInstanceState()");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

        // 如果是横竖屏切换（Fragment销毁），不应该走smoothScrollToPosition(0)，因为这个方法会导致ScrollManager的onHorizontalScroll不断执行，而ScrollManager.mConsumeX已经重置，会导致这个值紊乱
        // 而如果走scrollToPosition(0)方法，则不会导致ScrollManager的onHorizontalScroll执行，所以ScrollManager.mConsumeX这个值不会错误
        scrollToPosition(0);
        // 但是因为不走ScrollManager的onHorizontalScroll，所以不会执行切换动画，所以就调用smoothScrollBy(int dx, int dy)，让item轻微滑动，触发动画
        smoothScrollBy(10, 0);
        smoothScrollBy(0, 0);

        autoPlayGallery();

        L.d(TAG, "GalleryItemDecoration onRestoreInstanceState -->");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                removeAutoPlayTask();
//                break;
            case MotionEvent.ACTION_MOVE:
                removeAutoPlayTask();
                break;
            case MotionEvent.ACTION_UP:
                autoPlayGallery();
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 播放间隔时间 ms
     *
     * @param interval int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView intervalTime(@IntRange(from = 10) int interval) {
        this.mInterval = interval;
        return this;
    }

    /**
     * 开始处于的位置
     *
     * @param i int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initPosition(@IntRange(from = 0) int i) {
        if (i >= getAdapter().getItemCount()) {
            i = getAdapter().getItemCount() - 1;
        } else if (i < 0) {
            i = 0;
        }
        mInitFirstVisiblePos = i;
        return this;
    }

    /**
     * item大小确定后调用，有且仅有第一次 initPosition 之后才有用，会默认滚动到指定位置，之后就都不调用了
     * <p>
     * 初始化的时候使用
     *
     * @param size int
     */
    @Override
    public void onItemSizeMeasured(int size) {
        if (mInitFirstVisiblePos < 0) {
            return;
        }

        L.d(TAG, "GalleryItemDecoration onItemSizeMeasured mInitFirstVisiblePos-->" + mInitFirstVisiblePos);

        if (mInitFirstVisiblePos == 0) {
//            scrollToPosition(0);
            selectItem(mInitFirstVisiblePos);
        } else {
            if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
//                smoothScrollBy(mInitFirstVisiblePos * size, 0);

                selectItem(mInitFirstVisiblePos);
            } else {
                smoothScrollBy(0, mInitFirstVisiblePos * size);
            }
        }
        mInitFirstVisiblePos = -1;
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param view     View
         * @param position int
         */
        void onItemClick(View view, int position);
    }


//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//    }

//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        super.onWindowFocusChanged(hasWindowFocus);
//    }

}
