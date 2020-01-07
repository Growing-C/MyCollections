package com.cgy.mycollections.functions.ui.scale.scaleview;

import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.cgy.mycollections.R;

import appframe.utils.L;


/**
 * Copyright (c) 2017, Bongmi
 * All rights reserved
 * Author: xuyuanyi@bongmi.com
 */

public class HorizontalScaleView extends View {
    private final String TAG = HorizontalScaleView.class.getSimpleName();

    private final int SCALE_WIDTH_BIG = 4;//大刻度线宽度
    private final int SCALE_WIDTH_SMALL = 2;//小刻度线宽度
    private final int LINE_WIDTH = 6;//指针线宽度

    private int rectPadding = 40;//圆角矩形间距
    private int rectWidth;//圆角矩形宽
    private int rectHeight;//圆角矩形高

    private int maxScaleLength;//大刻度长度
    private int midScaleLength;//中刻度长度
    private int minScaleLength;//小刻度长度
    private int scaleSpace;//刻度间距
    private int scaleSpaceUnit;//每大格刻度间距
    private int height, width;//view高宽,一旦measure 就不变了
    private int ruleHeight;//刻度尺高

    private int pointerTouchWidth = 40;//指针触摸范围
    private int leftPointerCenterX;//左边指针的中心x
    private int rightPointerCenterX;//左边指针的中心x
    private int leftPointerValue;//左边指针的值
    private int rightPointerValue;//右指针的值

    private int maxValue;//最大刻度，由外部赋值，赋值后不会改变
    private int minValue;//最小刻度，由外部赋值，赋值后不会改变
    private int borderLeftX, borderRightX;//左右边界值坐标，一旦赋值不会再改变
    private float midX;//当前中心刻度x坐标
    private float originLengthBetweenStartXAndCenterX;//初始中心刻度x坐标和 绘制开始坐标之间的x轴距离，一旦定了就不会再改变
    private float scaleStartX;//最小刻度x坐标,从最小刻度开始画刻度
    private float scaleEndX;//最大刻度x坐标,
    private float overScrollX = 100;//左右允许滑动出去的x轴长度

    private float lastX;

    //    private float originValue;//初始刻度对应的值
    private float currentValue;//当前刻度对应的值

    private Paint paint;//画笔

    private Context context;

    private String descri = "体重";//描述
    private String unit = "kg";//刻度单位

    private VelocityTracker velocityTracker;//速度监测
    private float velocity;//当前滑动速度
    private float a = 1000000;//加速度
    private boolean continueScroll;//是否继续滑动

    private boolean isMeasured;

    private OnValueChangeListener onValueChangeListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != onValueChangeListener) {
                float v = (float) (Math.round(currentValue * 10)) / 10;//保留一位小数
                onValueChangeListener.onValueChanged(v);
            }
        }
    };

    public HorizontalScaleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HorizontalScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HorizontalScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

    }

    //设置刻度范围
    public void setRange(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
//        originValue = (max + min) / 2;
//        currentValue = originValue;
        L.e(TAG, "setRange min:" + min + "  max:" + max);
    }

    /**
     * 设置左右指针的值
     *
     * @param leftValue
     * @param rightValue
     */
    public void setPointerPos(int leftValue, int rightValue) {
        leftPointerValue = leftValue;
        rightPointerValue = rightValue;
        if (rightValue < leftValue) {
            throw new IllegalArgumentException("右指针值必须大于左指针值");
        }
        L.e(TAG, "setPointerPos leftValue:" + leftValue + "   rightValue：" + rightValue);
    }

    //设置刻度单位
    public void setUnit(String unit) {
        this.unit = unit;
    }

    //设置刻度描述
    public void setDescri(String descri) {
        this.descri = descri;
    }

    //设置value变化监听
    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.e(TAG, "onMeasure isMeasured:" + isMeasured);
        if (!isMeasured) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            ruleHeight = height * 2 / 3;
            maxScaleLength = height / 10;
            midScaleLength = height / 12;
            minScaleLength = maxScaleLength / 2;
            scaleSpace = height / 60 > 8 ? height / 60 : 8;
            scaleSpaceUnit = scaleSpace * 10 + SCALE_WIDTH_BIG + SCALE_WIDTH_SMALL * 9;
            rectWidth = scaleSpaceUnit;
            rectHeight = scaleSpaceUnit / 2;

            //最左边从0开始，右边一直往右超出屏幕部分可以滚动
            borderLeftX = 0;
            borderRightX = borderLeftX + (maxValue - minValue) * scaleSpaceUnit;
//            borderLeftX = width / 2 - ((min + max) / 2 - min) * scaleSpaceUnit;
//            borderRightX = width / 2 + ((min + max) / 2 - min) * scaleSpaceUnit;
            leftPointerCenterX = width / 2 - ((minValue + maxValue) / 2 - minValue) * scaleSpaceUnit;
            rightPointerCenterX = width / 2 + ((minValue + maxValue) / 2 - minValue) * scaleSpaceUnit;

//            midX = (borderLeftX + borderRightX) / 2;
            midX = width / 2;
            originLengthBetweenStartXAndCenterX = midX - borderLeftX;
            scaleStartX = borderLeftX;
            scaleEndX = borderRightX;

            calculateCurrentScale();
            isMeasured = true;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画刻度线
        for (int i = minValue; i <= maxValue; i++) {
            //画刻度数字
            Rect rect = new Rect();
            String str = String.valueOf(i);
            paint.setColor(getResources().getColor(android.R.color.black));
            paint.setTextSize(40);
            paint.getTextBounds(str, 0, str.length(), rect);
            int w = rect.width();
            int h = rect.height();
            //画刻度文字
            canvas.drawText(str, scaleStartX + (i - minValue) * scaleSpaceUnit - w / 2 - SCALE_WIDTH_BIG / 2, ruleHeight - maxScaleLength - h - minScaleLength / 2, paint);
            //画大刻度线
            paint.setStrokeWidth(SCALE_WIDTH_BIG);
            canvas.drawLine(scaleStartX + (i - minValue) * scaleSpaceUnit, ruleHeight - maxScaleLength, scaleStartX + (i - minValue) * scaleSpaceUnit, ruleHeight, paint);

            if (i == maxValue) {
                continue;//最后一条不画中小刻度线
            }
            //画中刻度线
            paint.setStrokeWidth(SCALE_WIDTH_SMALL);
            canvas.drawLine(scaleStartX + (i - minValue) * scaleSpaceUnit + scaleSpaceUnit / 2, ruleHeight, scaleStartX + (i - minValue) * scaleSpaceUnit + scaleSpaceUnit / 2, ruleHeight - midScaleLength, paint);
            //画小刻度线
            for (int j = 1; j < 10; j++) {
                if (j == 5) {
                    continue;
                }
                canvas.drawLine(scaleStartX + (i - minValue) * scaleSpaceUnit + (SCALE_WIDTH_SMALL + scaleSpace) * j, ruleHeight, scaleStartX + (i - minValue) * scaleSpaceUnit + (SCALE_WIDTH_SMALL + scaleSpace) * j, ruleHeight - minScaleLength, paint);
            }

        }

        //画底部横线
        paint.setStrokeWidth(LINE_WIDTH);
        paint.setColor(getResources().getColor(android.R.color.darker_gray));
        canvas.drawLine(scaleStartX, ruleHeight + LINE_WIDTH / 2, scaleStartX + (maxValue - minValue) * scaleSpaceUnit, ruleHeight + LINE_WIDTH / 2, paint);
        //画指针线
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawLine(width / 2, 0, width / 2, ruleHeight, paint);
        //画圆角矩形
        paint.setStyle(Paint.Style.FILL);
        RectF r = new RectF();
        r.left = width / 2 - rectWidth / 2;
        r.top = ruleHeight + rectPadding;
        r.right = width / 2 + rectWidth / 2;
        r.bottom = ruleHeight + rectPadding + rectHeight;
        canvas.drawRoundRect(r, 10, 10, paint);
        //画小三角形指针
        Path path = new Path();
        path.moveTo(width / 2 - scaleSpace * 2, ruleHeight + rectPadding);
        path.lineTo(width / 2, ruleHeight + rectPadding - 10);
        path.lineTo(width / 2 + scaleSpace * 2, ruleHeight + rectPadding);
        path.close();
        canvas.drawPath(path, paint);
        //绘制文字
        paint.setColor(getResources().getColor(android.R.color.black));
        Rect rect1 = new Rect();
        paint.getTextBounds(descri, 0, descri.length(), rect1);
        int w1 = rect1.width();
        int h1 = rect1.height();
        canvas.drawText(descri, width / 2 - w1 / 2, ruleHeight + rectPadding + rectHeight + h1 + 10, paint);
        //绘制当前刻度值数字
        paint.setColor(getResources().getColor(android.R.color.white));
        float v = (float) (Math.round(currentValue * 10)) / 10;//保留一位小数
        String value = String.valueOf(v) + unit;
        Rect rect2 = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect2);
        int w2 = rect2.width();
        int h2 = rect2.height();
        canvas.drawText(value, width / 2 - w2 / 2, ruleHeight + rectPadding + rectHeight / 2 + h2 / 2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
//        L.e(TAG, "onTouchEvent x:" + x);
//        屏幕最左边 x = 0 最右边是屏幕宽度 往右滑动 x越来越大
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                continueScroll = false;
                //初始化速度追踪
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                int offsetX = (int) (lastX - x);
                moveByX(offsetX);
//                scaleStartX -= offsetX;
//                midX -= offsetX;
                calculateCurrentScale();
                invalidate();
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                confirmBorder();
                //当前滑动速度
                velocityTracker.computeCurrentVelocity(1000);
                velocity = velocityTracker.getXVelocity();
                float minVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
                if (Math.abs(velocity) > minVelocity) {
                    continueScroll = true;
                    continueScroll();
                } else {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }
        return true;
    }

    /**
     * 主要的 x 有用的值 移动offsetX的距离
     *
     * @param offsetX
     */
    private void moveByX(float offsetX) {
        scaleStartX -= offsetX;
        scaleEndX -= offsetX;
        midX -= offsetX;
    }

    /**
     * 设置刻度开始的x位置 同时设置其他相关值
     *
     * @param newScaleStartX
     */
    private void setScaleStartXAndFixOtherData(float newScaleStartX) {
        scaleStartX = newScaleStartX;
        scaleEndX = scaleStartX + borderRightX - borderLeftX;
        midX = originLengthBetweenStartXAndCenterX + scaleStartX;
    }

    /**
     * 计算x坐标处的 刻度值
     *
     * @param x
     */
    private void calculateScaleValueByX(int x) {
        if (x <= scaleStartX) {
            //x值小于开始值
            currentValue = minValue;
        } else if (x >= scaleEndX) {
            currentValue = maxValue;
        } else {
            float xOffsetFromStart = x - scaleStartX;
            int bigScaleValue = (int) (xOffsetFromStart / scaleSpaceUnit);//x到start 包含了多少大的刻度
            float extraSmallScaleValue = xOffsetFromStart % scaleSpaceUnit;//多出的小格数

            int offsetSmall = (new BigDecimal(extraSmallScaleValue / (scaleSpace + SCALE_WIDTH_SMALL)).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();//移动的小刻度数 四舍五入取整
            float value = minValue + bigScaleValue + offsetSmall * 0.1f;
            if (value > maxValue) {
                currentValue = maxValue;
            } else if (value < minValue) {
                currentValue = minValue;
            } else {
                currentValue = value;
            }
        }
        L.e(TAG, "calculate x:" + x + "  currentValue:" + currentValue + "  scaleStartX:" + scaleStartX + " --scaleEndX" + scaleEndX);
        mHandler.sendEmptyMessage(0);
    }

    //计算当前刻度
    private void calculateCurrentScale() {
        calculateScaleValueByX(width / 2);
    }

    //指针线超出范围时 重置回边界处
    private void confirmBorder() {
        if (overScrollX + borderLeftX < scaleStartX) {
            //view开始绘制的左边位置大于限制位置了
            setScaleStartXAndFixOtherData(overScrollX + borderLeftX);
            postInvalidate();
        } else if (width - overScrollX > scaleStartX + borderRightX - borderLeftX) {
            //view最右边绘制的位置 x坐标小于限制位置
            setScaleStartXAndFixOtherData(width - overScrollX - (borderRightX - borderLeftX));
            postInvalidate();
        }
    }

    //手指抬起后继续惯性滑动
    private void continueScroll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float velocityAbs = 0;//速度绝对值
                if (velocity > 0 && continueScroll) {
                    velocity -= 50;
                    moveByX(-velocity * velocity / a);
                    velocityAbs = velocity;
                } else if (velocity < 0 && continueScroll) {
                    velocity += 50;
                    moveByX(velocity * velocity / a);
                    velocityAbs = -velocity;
                }
                calculateCurrentScale();
                confirmBorder();
                postInvalidate();
                if (continueScroll && velocityAbs > 0) {
                    post(this);
                } else {
                    continueScroll = false;
                }
            }
        }).start();
    }
}
