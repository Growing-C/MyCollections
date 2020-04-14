package com.cgy.mycollections.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import appframe.utils.L;

/**
 * Description :圆圈中有个勾或者 一个时钟样式
 * Author :cgy
 * Date :2018/12/20
 */
public class HintView extends View {

    private boolean isDrawProgress = false;//默认是画勾不是 时钟

    /**
     * View 是个正方形，宽高中小的一个值,根据小的值来定位绘制
     */
    private int mRealSize;

    /**
     * 越小，线条锯齿度越小
     */
    private static final float DEFAULT_SEGMENT_LENGTH = 10F;
    private static final float DEFAULT_WIDTH = 3F;//默认线宽
    private static final float MAX_WIDTH = 45F;

    private List<PathSegment> pathSegments = new ArrayList<>();
    private float growMax = DEFAULT_WIDTH;//增长到的最大点

    Paint mLinePaint;
    Paint mCirclePaint;

    /**
     * 圆心横坐标
     */
    private int mCenterX;
    /**
     * 圆心纵坐标
     */
    private int mCenterY;
    /**
     * 实际开始画的图的左边界
     */
    private int mRealLeftX;

    public HintView(Context context) {
        super(context);
        init();
    }

    public HintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.WHITE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);
    }

    /**
     * 测量，强制将 View 设置为正方形
     * 当宽和高有一个为 wrap_content 时，就将宽高都定为 150 px
     * 当宽或者高有一个小于 150 px 时，都设置为 150 px
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRealSize = Math.min(width, height);// 取较小的长宽作为view的内容的大小

        mCenterY = height / 2;
        mCenterX = width / 2;
        mRealLeftX = mCenterX - mRealSize / 2;//实际左边开始画的位置
    }

    /**
     * 设置是画勾号还是 时针分针
     *
     * @param isInProgress
     */
    public void setIsInProgress(boolean isInProgress) {
        this.isDrawProgress = isInProgress;
        invalidate();
    }

    /**
     * 设置背景圆的颜色
     *
     * @param color
     */
    public void setCircleColor(@ColorInt int color) {
        mCirclePaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        L.e("mRealSize:" + mRealSize);
        L.e("mCenterY:" + mCenterY);
        L.e("mCenterX:" + mCenterX);

        canvas.drawCircle(mCenterX, mCenterY, mRealSize / 2, mCirclePaint);

        if (isDrawProgress) {//画时针分针
            initInProgressPatternPath();
        } else {//画勾号
            initOkMarkPatternPath();
        }
        L.e("PathSegment size: " + pathSegments.size());
        //画线
        for (PathSegment p : pathSegments) {
            mLinePaint.setAlpha(p.getAlpha());
            mLinePaint.setStrokeWidth(p.getWidth());
            L.e("PathSegment width: " + p.getWidth());
            canvas.drawPath(p.getPath(), mLinePaint);
        }
    }

    private void initInProgressPatternPath() {
        pathSegments.clear();

        Path path = new Path();
        // 时针起点
        float startX = (float) (mRealLeftX + 0.5 * mRealSize);
        float startY = (float) (0.15 * mRealSize);
        path.moveTo(startX, startY);
        generatePathSegment(path, true);

        // 中心点
        float cornerX = (float) (mRealLeftX + 0.5 * mRealSize);
        float cornerY = (float) (0.5 * mRealSize);
        path.lineTo(cornerX, cornerY);
        generatePathSegment(path, true);

        // 分针终点
        float endX = (float) (mRealLeftX + 0.72 * mRealSize);
        float endY = (float) (0.68 * mRealSize);
        path.lineTo(endX, endY);
        generatePathSegment(path, false);
    }

    private void initOkMarkPatternPath() {
        pathSegments.clear();

        Path path = new Path();
        // 对号起点
        float startX = (float) (mRealLeftX + 0.3 * mRealSize);
        float startY = (float) (0.5 * mRealSize);
        path.moveTo(startX, startY);
        generatePathSegment(path, true);

        // 对号拐角点
        float cornerX = (float) (mRealLeftX + 0.43 * mRealSize);
        float cornerY = (float) (0.66 * mRealSize);
        path.lineTo(cornerX, cornerY);
        generatePathSegment(path, true);

        // 对号终点
        float endX = (float) (mRealLeftX + 0.75 * mRealSize);
        float endY = (float) (0.4 * mRealSize);
        path.lineTo(endX, endY);
        generatePathSegment(path, false);
    }

    /**
     * 截取path
     *
     * @param path
     */
    private void generatePathSegment(Path path, boolean isGrow) {

        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        int segmentSize = (int) Math.ceil(length / DEFAULT_SEGMENT_LENGTH);
//        L.e("generatePathSegment segmentSize:" + segmentSize);
//        L.e("generatePathSegment growMax:" + growMax);
        Path ps = null;
        PathSegment pe = null;
        int nowSize = pathSegments.size();//集合中已经有的
        if (nowSize == 0) {
            ps = new Path();
            pm.getSegment(0, length, ps, true);
            pe = new PathSegment(ps);
            pe.setAlpha(255);
            pe.setWidth(DEFAULT_WIDTH);
            pathSegments.add(pe);
        } else {
            float minGap = 0;
            if (!isGrow) {
                minGap = (growMax - DEFAULT_WIDTH) / ((float) (segmentSize - nowSize));
            }
            for (int i = nowSize; i < segmentSize; i++) {
                ps = new Path();
                pm.getSegment((i - 1) * DEFAULT_SEGMENT_LENGTH - 0.4f, Math.min(i * DEFAULT_SEGMENT_LENGTH, length), ps, true);
                pe = new PathSegment(ps);
                pe.setAlpha(255);
                float width;
                if (isGrow) {
                    width = Math.min(MAX_WIDTH, i * 2 + DEFAULT_WIDTH);
                    if (width > growMax) {
                        growMax = width;
                    }
                } else {//缩小
                    width = Math.max(DEFAULT_WIDTH, growMax - (i - nowSize) * minGap);
                }
                pe.setWidth(width);
                pathSegments.add(pe);
            }
        }
    }


    /**
     * path片段
     */
    private class PathSegment {
        Path path;
        float width;
        int alpha;

        public PathSegment(Path path) {
            this.path = path;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

    }
}
