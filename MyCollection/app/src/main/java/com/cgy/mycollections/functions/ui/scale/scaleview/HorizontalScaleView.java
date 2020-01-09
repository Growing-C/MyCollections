package com.cgy.mycollections.functions.ui.scale.scaleview;

import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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


import appframe.utils.L;


public class HorizontalScaleView extends View {
    private final String TAG = HorizontalScaleView.class.getSimpleName();


    //<editor-fold desc="参数 ">
    private final int SCALE_WIDTH_BIG = 4;//大刻度线宽度
    private final int SCALE_WIDTH_SMALL = 2;//小刻度线宽度
    private final float LINE_WIDTH = 6;//指针线宽度

    private float rectPadding = 40;//圆角矩形间距
    private float rectWidth;//圆角矩形宽
    private float rectHeight;//圆角矩形高

    private float maxScaleHeight;//大刻度长度
    private float midScaleHeight;//中刻度长度
    private float minScaleHeight;//小刻度长度
    private float smallScaleSpace;//刻度间距
    private float bigScaleSpace;//每大格刻度间距
    private int height, width;//view高宽,一旦measure 就不变了
    private int ruleHeight;//刻度尺高

    private float pointerTouchWidth = 40;//指针触摸范围
    private float leftPointerStartX;//左边指针的开始x
    private float rightPointerStartX;//右边指针的开始x
    private int leftPointerValue;//左边指针的值
    private int rightPointerValue;//右指针的值
    private int currentMovingPointer = -1;//当前移动的指针 -1 代表没有 0代表左指针  1代表右指针

    private int maxValue;//最大刻度，由外部赋值，赋值后不会改变
    private int minValue;//最小刻度，由外部赋值，赋值后不会改变
    private float borderLeftX, borderRightX;//左右边界值坐标，一旦赋值不会再改变
    private float originLengthBetweenStartXAndCenterX;//初始中心刻度x坐标和 绘制开始坐标之间的x轴距离，一旦定了就不会再改变
    private float overScrollX = 100;//左右允许滑动出去的x轴长度

    private float midX;//当前中心刻度x坐标
    private float scaleStartX;//最小刻度x坐标,从最小刻度开始画刻度
    private float scaleEndX;//最大刻度x坐标,

    private float lastX;

    //    private float originValue;//初始刻度对应的值
    private float currentValue;//当前刻度对应的值

    private Paint linePaint;//画笔
    private Paint textPaint;//画笔
    private Paint pointerPaint;//指针画笔
    private int unavailableRangeColorGrey;//不可用的范围颜色
    private int lineColorBlack;//黑色的线

    private Context context;

    private String descri = "体重";//描述
    private String unit = "kg";//刻度单位

    private VelocityTracker velocityTracker;//速度监测
    private float velocity;//当前滑动速度
    private float a = 1000000;//加速度
    private boolean continueScroll;//是否继续滑动

    private boolean isMeasured;
    private boolean usingSmallScale = false;//是否使用小刻度

    //</editor-fold>

    private IAvailableFilter mAvailableFilter;

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


    //<editor-fold desc="内部初始化 ">
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
        unavailableRangeColorGrey = Color.GRAY;
//        unavailableRangeColorGrey = getResources().getColor(android.R.color.darker_gray);
        lineColorBlack = Color.BLACK;

        //初始化画笔
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setColor(lineColorBlack);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(lineColorBlack);
        textPaint.setTextSize(40);

        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setDither(true);
        pointerPaint.setColor(Color.parseColor("#057dff"));
        pointerPaint.setStrokeWidth(LINE_WIDTH);

    }
    //</editor-fold>

    //<editor-fold desc="外部初始化设置方法 ">

    //设置刻度范围
    public HorizontalScaleView setRange(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
//        originValue = (max + min) / 2;
//        currentValue = originValue;
        L.e(TAG, "setRange min:" + min + "  max:" + max);
        return this;
    }

    /**
     * 设置左右指针的值
     *
     * @param leftValue
     * @param rightValue
     */
    public HorizontalScaleView setPointerPos(int leftValue, int rightValue) {
        leftPointerValue = leftValue;
        rightPointerValue = rightValue;

        if (leftPointerValue < minValue)
            leftPointerValue = minValue;
        if (rightPointerValue > maxValue)
            rightPointerValue = maxValue;

        if (rightValue < leftValue) {
            throw new IllegalArgumentException("右指针值必须大于左指针值");
        }
        L.e(TAG, "setPointerPos leftValue:" + leftValue + "   rightValue：" + rightValue);
        return this;
    }

    //设置value变化监听
    public HorizontalScaleView setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
        return this;
    }

    //设置是否可用的filter
    public HorizontalScaleView setAvailableFilter(IAvailableFilter availableFilter) {
        this.mAvailableFilter = availableFilter;
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="生命周期 绘制相关 ">
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.e(TAG, "onMeasure isMeasured:" + isMeasured);
        if (!isMeasured) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            ruleHeight = height * 2 / 3;
            maxScaleHeight = height / 2;
            midScaleHeight = height * 2 / 5;
            minScaleHeight = maxScaleHeight / 2;
            if (usingSmallScale)//使用小刻度时 小刻度最小8
                smallScaleSpace = height / 60 > 8 ? height / 60 : 8;
            else {//不使用小刻度时 小刻度还是存在 不过缩小一些 以减小大刻度
                smallScaleSpace = 1f;
            }
            bigScaleSpace = smallScaleSpace * 10 + SCALE_WIDTH_BIG + SCALE_WIDTH_SMALL * 9;
            rectWidth = bigScaleSpace;
            rectHeight = bigScaleSpace / 2;

            //最左边从0开始，右边一直往右超出屏幕部分可以滚动
            borderLeftX = 0;
            borderRightX = borderLeftX + (maxValue - minValue) * bigScaleSpace;

            leftPointerStartX = getXByValue(leftPointerValue);
            rightPointerStartX = getXByValue(rightPointerValue);

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

        //画刻度线的框架，主要是刻度，文字和底部线
        drawScaleFrame(canvas);
        //画指针
        drawPointer(canvas);

        //画圆角矩形
//        paint.setStyle(Paint.Style.FILL);
//        RectF r = new RectF();
//        r.left = width / 2 - rectWidth / 2;
//        r.top = ruleHeight + rectPadding;
//        r.right = width / 2 + rectWidth / 2;
//        r.bottom = ruleHeight + rectPadding + rectHeight;
//        canvas.drawRoundRect(r, 10, 10, paint);
        //画小三角形指针
//        Path path = new Path();
//        path.moveTo(width / 2 - smallScaleSpace * 2, ruleHeight + rectPadding);
//        path.lineTo(width / 2, ruleHeight + rectPadding - 10);
//        path.lineTo(width / 2 + smallScaleSpace * 2, ruleHeight + rectPadding);
//        path.close();
//        canvas.drawPath(path, paint);
        //绘制文字
//        paint.setColor(lineColorBlack);
//        Rect rect1 = new Rect();
//        paint.getTextBounds(descri, 0, descri.length(), rect1);
//        int w1 = rect1.width();
//        int h1 = rect1.height();
//        canvas.drawText(descri, width / 2 - w1 / 2, ruleHeight + rectPadding + rectHeight + h1 + 10, paint);
//        //绘制当前刻度值数字
//        paint.setColor(getResources().getColor(android.R.color.white));
//        float v = (float) (Math.round(currentValue * 10)) / 10;//保留一位小数
//        String value = String.valueOf(v) + unit;
//        Rect rect2 = new Rect();
//        paint.getTextBounds(value, 0, value.length(), rect2);
//        int w2 = rect2.width();
//        int h2 = rect2.height();
//        canvas.drawText(value, width / 2 - w2 / 2, ruleHeight + rectPadding + rectHeight / 2 + h2 / 2, paint);
    }

    /**
     * 画刻度线的框架，主要是刻度，文字和底部线
     *
     * @param canvas
     */
    private void drawScaleFrame(Canvas canvas) {
        //画刻度线
        for (int i = minValue; i <= maxValue; i++) {
            float valueLeftX = scaleStartX + (i - minValue) * bigScaleSpace;
            float valueRightX = scaleStartX + (i + 1 - minValue) * bigScaleSpace;

            String str = String.valueOf(i);
            //画刻度数字
            if (!usingSmallScale) {
                if ((i - minValue) % 5 == 0) {//没有小刻度的时候 大刻度不能画太密，暂定5个一画
                    drawScaleText(canvas, str, valueLeftX);
                }
            } else {
                drawScaleText(canvas, str, valueLeftX);
            }

            //画大刻度线
            linePaint.setStrokeWidth(SCALE_WIDTH_BIG);
            canvas.drawLine(valueLeftX, ruleHeight - maxScaleHeight, valueLeftX, ruleHeight, linePaint);

            if (i == maxValue) {
                continue;//最后一条不画中小刻度线
            }

            if (mAvailableFilter != null && !mAvailableFilter.isValueAvailable(i)) {
                //值不可用
//                linePaint.setColor(unavailableRangeColorGrey);
                canvas.drawRect(valueLeftX, ruleHeight - maxScaleHeight, valueRightX, ruleHeight, linePaint);
//                linePaint.setColor(lineColorBlack);
            }

            if (!usingSmallScale) {//不使用小刻度 就不画了
                continue;
            }

            //画中刻度线
            linePaint.setStrokeWidth(SCALE_WIDTH_SMALL);
            canvas.drawLine(valueLeftX + bigScaleSpace / 2, ruleHeight, valueLeftX + bigScaleSpace / 2, ruleHeight - midScaleHeight, linePaint);
            //画小刻度线
            for (int j = 1; j < 10; j++) {
                if (j == 5) {
                    continue;
                }
                canvas.drawLine(valueLeftX + (SCALE_WIDTH_SMALL + smallScaleSpace) * j, ruleHeight, valueLeftX + (SCALE_WIDTH_SMALL + smallScaleSpace) * j, ruleHeight - minScaleHeight, linePaint);
            }

        }

        //画底部横线
        linePaint.setStrokeWidth(LINE_WIDTH);
        linePaint.setColor(unavailableRangeColorGrey);
        canvas.drawLine(scaleStartX, ruleHeight + LINE_WIDTH / 2, scaleStartX + (maxValue - minValue) * bigScaleSpace, ruleHeight + LINE_WIDTH / 2, linePaint);
    }

    /**
     * 画刻度文字
     *
     * @param canvas
     */
    private void drawScaleText(Canvas canvas, String scaleText, float valueLeftX) {
        Rect rect = new Rect();
        textPaint.getTextBounds(scaleText, 0, scaleText.length(), rect);
        int w = rect.width();
        int h = rect.height();
        float scaleTextBaseLineY = ruleHeight - maxScaleHeight - (float) h / 2;
        L.e(TAG, "ruleHeight:" + ruleHeight + "   maxScaleHeight:" + maxScaleHeight + "  textHeight:" + h + "   Y:" + scaleTextBaseLineY);
        //画刻度文字
        canvas.drawText(scaleText, valueLeftX - w / 2 - SCALE_WIDTH_BIG / 2, scaleTextBaseLineY, textPaint);
    }

    /**
     * 画指针
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        //画指针线
//        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
//        canvas.drawLine(width / 2, 0, width / 2, ruleHeight, paint);
        float pointerCircleRadius = pointerTouchWidth * 2 / 5;//大头针底部圆半径
        float pointerLineBottomY = ruleHeight + LINE_WIDTH / 2 - pointerCircleRadius;
        //画左边指针底部圆
        canvas.drawCircle(leftPointerStartX, ruleHeight + LINE_WIDTH / 2, pointerCircleRadius, pointerPaint);
        //画左边指针
        canvas.drawLine(leftPointerStartX, 0, leftPointerStartX, pointerLineBottomY, pointerPaint);

        //画右边指针底部圆
        canvas.drawCircle(rightPointerStartX, ruleHeight + LINE_WIDTH / 2, pointerCircleRadius, pointerPaint);
        //画右边指针
        canvas.drawLine(rightPointerStartX, 0, rightPointerStartX, pointerLineBottomY, pointerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
//        L.e(TAG, "onTouchEvent x:" + x);
//        屏幕最左边 x = 0 最右边是屏幕宽度 往右滑动 x越来越大
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                currentMovingPointer = getMovingPointerIndex(x);
                L.e(TAG, "当前移动指针：" + currentMovingPointer);

                continueScroll = false;
                //初始化速度追踪
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (lastX - x);
                if (currentMovingPointer == -1) {//view滑动
                    velocityTracker.addMovement(event);
                    moveViewByX(offsetX);
                    calculateCurrentScale();
                } else if (currentMovingPointer == 0) {
                    //左指针移动
                    moveLeftPointerByX(offsetX);
                } else {
                    //右指针移动
                    moveRightPointerByX(offsetX);
                }
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

    //</editor-fold>

    //<editor-fold desc="计算x 和value等 ">

    /**
     * 根据触摸的x坐标确定是不是在移动pointer 以及是第几个pointer
     *
     * @param x 0-左指针 1-右指针
     * @return
     */
    private int getMovingPointerIndex(float x) {
        if (x <= leftPointerStartX) {
            //左指针左边范围
            L.e(TAG, "x:" + x + "   左边界:" + (leftPointerStartX - pointerTouchWidth / 2) + " 左指针x：" + leftPointerStartX);
            if (x > leftPointerStartX - pointerTouchWidth / 2) {
                return 0;
            }
        } else if (x >= rightPointerStartX) {
            //右指针右边范围
            L.e(TAG, "x:" + x + "   右边界" + (rightPointerStartX + pointerTouchWidth / 2) + " 右指针x：" + rightPointerStartX);
            if (x < rightPointerStartX + pointerTouchWidth / 2) {
                return 1;
            }
        } else {
            //在两个指针之间，需要根据距离来判断是在操作哪个
            float touchDistanceWithLeftPointerX = x - leftPointerStartX;
            float touchDistanceWithRightPointerX = rightPointerStartX - x;
            L.e(TAG, "x:" + x + "  距离左指针 x:" + touchDistanceWithLeftPointerX + " 距离右指针：" + touchDistanceWithRightPointerX);

            if (touchDistanceWithLeftPointerX <= touchDistanceWithRightPointerX) {
                //使用更小的那个
                if (touchDistanceWithLeftPointerX <= pointerTouchWidth / 2) {
                    return 0;
                }
            } else {
                if (touchDistanceWithRightPointerX <= pointerTouchWidth / 2) {
                    return 1;
                }
            }
        }

        return -1;
    }

    /**
     * 根据value值获取 其x坐标
     *
     * @param value
     * @return
     */
    private float getXByValue(float value) {
        if (value < minValue || value > maxValue)
            throw new IllegalArgumentException("value can no be lower than min or larger than max!");
        return borderLeftX + (value - minValue) * bigScaleSpace;
    }

    /**
     * 计算x坐标处的 刻度值
     *
     * @param x
     */
    private float calculateScaleValueByX(float x) {
        float scaleValue;
        if (x <= scaleStartX) {
            //x值小于开始值
            scaleValue = minValue;
        } else if (x >= scaleEndX) {
            scaleValue = maxValue;
        } else {
            float xOffsetFromStart = x - scaleStartX;
            int bigScaleValue = (int) (xOffsetFromStart / bigScaleSpace);//x到start 包含了多少大的刻度
            float extraSmallScaleValue = xOffsetFromStart % bigScaleSpace;//多出的小格数

            int offsetSmall = (new BigDecimal(extraSmallScaleValue / (smallScaleSpace + SCALE_WIDTH_SMALL)).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();//移动的小刻度数 四舍五入取整
            float value = minValue + bigScaleValue + offsetSmall * 0.1f;
            if (value > maxValue) {
                scaleValue = maxValue;
            } else if (value < minValue) {
                scaleValue = minValue;
            } else {
                scaleValue = value;
            }
        }
        L.e(TAG, "calculateScaleValueByX x:" + x + "  scaleValue:" + scaleValue + "  scaleStartX:" + scaleStartX + " --scaleEndX" + scaleEndX);
        return scaleValue;
    }

    /**
     * 初始中心点坐标
     *
     * @param x
     */
    private void calculateCenterScaleValueByX(int x) {
        currentValue = calculateScaleValueByX(x);
        mHandler.sendEmptyMessage(0);
    }

    //计算当前刻度
    private void calculateCurrentScale() {
        calculateCenterScaleValueByX(width / 2);
    }

    //</editor-fold>

    //<editor-fold desc="坐标移动">

    private void moveLeftPointerByX(float offsetX) {
        leftPointerStartX -= offsetX;
        leftPointerValue = (int) calculateScaleValueByX(leftPointerStartX);
        L.e(TAG, "leftPointerValue:" + leftPointerValue);
    }

    private void moveRightPointerByX(float offsetX) {
        rightPointerStartX -= offsetX;
        rightPointerValue = (int) calculateScaleValueByX(rightPointerStartX);
        L.e(TAG, "rightPointerValue:" + rightPointerValue);
    }

    /**
     * 主要的 x 有用的值 移动offsetX的距离
     *
     * @param offsetX
     */
    private void moveViewByX(float offsetX) {
        scaleStartX -= offsetX;
        scaleEndX -= offsetX;
        midX -= offsetX;

        leftPointerStartX -= offsetX;
        rightPointerStartX -= offsetX;
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


        leftPointerStartX = scaleStartX + (leftPointerValue - minValue) * bigScaleSpace;
        rightPointerStartX = scaleStartX + (rightPointerValue - minValue) * bigScaleSpace;
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
                    moveViewByX(-velocity * velocity / a);
                    velocityAbs = velocity;
                } else if (velocity < 0 && continueScroll) {
                    velocity += 50;
                    moveViewByX(velocity * velocity / a);
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
    //</editor-fold>

    public interface IAvailableFilter {
        boolean isValueAvailable(int value);
    }
}
