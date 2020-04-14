package com.cgy.mycollections.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by RB-cgy on 2015/9/28.
 */
public class MProgressView extends View {
    /**
     * 扇形圆环开始角度
     */
    private final static int START_ANGLE = 130;

    /**
     * 扇形圆环总划过角度
     */
    private final static int SWEEP_ANGLE = 280;

    /**
     * 最大progress
     */
    private int mMax = 100;

    /**
     * 设置的progress
     */
    private int mProgress = 60;

    /**
     * 圆心横坐标
     */
    private int mCenterW;
    /**
     * 圆心纵坐标
     */
    private int mCenterH;

    /**
     * 内部圆环宽度
     */
    private int mInStroke;

    /**
     * 外部进度圆环宽度
     */
    private int mOutStroke;


    /**
     * 单位的字体大小
     */
    private int mUnitTextSize;

    /**
     * 进度字体大小
     */
    private int mProgressTextSize;


    /**
     * 内部扇形的参考矩形
     */
    private RectF mInRectF;
    /**
     * 外部扇形的参考矩形
     */
    private RectF mOutRectF;

    /**
     * 扇形的画笔
     */
    private Paint mFanPaint;

    /**
     * 画字体的画笔
     */
    private Paint mTextPaint;

    /**
     * 测量单位
     */
    private String mUnitText = "";
    /**
     * 中心的字
     */
    private String mCenterText = "Center";

    /**
     * 中心字体的颜色
     */
    private int mCenterTextColor = Color.RED;

    /**
     * 进度条的颜色
     */
    private int mProgressColor = Color.RED;

    public MProgressView(Context context) {
        super(context);
    }

    public MProgressView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * 初始化view
     */
    private void init() {

        mFanPaint = new Paint();
        mFanPaint.setAntiAlias(true);
        mFanPaint.setStyle(Paint.Style.STROKE);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.min(width, height);// 取较小的长宽作为view的内容的大小

        mCenterH = height / 2;
        mCenterW = width / 2;

        //view自带的外部边距
        int defaultPadding = size / 100;

        mOutStroke = (size - defaultPadding) / 11;
        mInStroke = mOutStroke / 2;// 内环宽度为外环的一半


        //内部圆环半径
        int pInRadius;

        //外部圆环半径
        int pOutRadius;
        // 计算扇形的半径
        pInRadius = size / 2 - defaultPadding - mInStroke;
        pOutRadius = size / 2 - defaultPadding - mInStroke;

        mInRectF = new RectF(mCenterW - pInRadius, mCenterH - pInRadius,
                mCenterW + pInRadius, mCenterH + pInRadius);
        mOutRectF = new RectF(mCenterW - pOutRadius, mCenterH - pOutRadius,
                mCenterW + pOutRadius, mCenterH + pOutRadius);

        mProgressTextSize = size / 6;
        mUnitTextSize = mProgressTextSize * 2 / 5;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);// 设置文字对齐方式在其中心

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画扇形圆环内部的黑色环
        mFanPaint.setStrokeWidth(mInStroke);
        mFanPaint.setColor(Color.BLACK);
        canvas.drawArc(mInRectF, START_ANGLE, SWEEP_ANGLE, false, mFanPaint);

        // 画扇形圆环外部的红色环
        mFanPaint.setStrokeWidth(mOutStroke);
        mFanPaint.setColor(mProgressColor);
        canvas.drawArc(mOutRectF, START_ANGLE, SWEEP_ANGLE * mProgress / mMax,
                false, mFanPaint);

        // 画中心的字
        mTextPaint.setColor(mCenterTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
        canvas.drawText(mCenterText, mCenterW,
                mCenterH + mProgressTextSize / 5, mTextPaint);

        // 画单位的字
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextSize(mUnitTextSize);
        canvas.drawText(mUnitText, mCenterW, mCenterH + +mProgressTextSize,
                mTextPaint);
    }

    /**
     * 设置进度条进度
     *
     * @param progress -值为1-100
     */
    public void setProgress(int progress) {
    	if (progress < 0) {
            progress = 0;
        }
        if (progress > mMax ) {
            progress = mMax;
        }
        mProgress = progress;
        this.invalidate();
    }

    /**
     * 设置progress条的颜色
     *
     * @param color
     */
    public void setProgressColor(int color) {
        mProgressColor = color;
        this.invalidate();
    }

    /**
     * 设置中心字体的颜色
     */
    public void setProgressTextColor(int color) {
        mCenterTextColor = color;
        this.invalidate();
    }

    /**
     * 设置中心的文本内容
     */
    public void setProgressText(String s) {
        mCenterText = s;
        this.invalidate();
    }

    /**
     * 设置单位的文本内容
     */
    public void setUnitText(String s) {
        mUnitText = s;
        this.invalidate();
    }
}
