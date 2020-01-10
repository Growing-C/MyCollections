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
    private final int SCALE_LINE_WIDTH_BIG = 4;//大刻度线宽度
    private final int SCALE_LINE_WIDTH_SMALL = 2;//小刻度线宽度
    private final float LINE_WIDTH = 6;//指针线宽度
    private final float SMALL_SCALE_SPACE = 20;//最小刻度间距
    private final float overScrollX = 100;//左右允许滑动出去的x轴长度

//    private float rectPadding = 40;//圆角矩形间距
//    private float rectWidth;//圆角矩形宽
//    private float rectHeight;//圆角矩形高

    private float maxScaleHeight;//大刻度长度
    private float midScaleHeight;//中刻度长度
    private float minScaleHeight;//小刻度长度
    private int smallScaleSpaceCountInBig = 10;//一个大刻度之间有几个小刻度
    private float bigScaleSpace;//每大格刻度间距
    private int height, width;//view高宽,一旦measure 就不变了
    private int ruleHeight;//刻度尺高

    private float pointerTouchWidth = 40;//指针触摸范围
    private float leftPointerX;//左边指针的开始x
    private float rightPointerX;//右边指针的开始x
    private float leftPointerValue;//左边指针的值
    private float rightPointerValue;//右指针的值
    private int currentMovingPointer = -1;//当前移动的指针 -1 代表没有 0代表左指针  1代表右指针

    private float baseScaleValue;//一个刻度值是多少(如果使用小刻度 则是小刻度大小，不使用则是大刻度大小)
    private float maxValue;//最大刻度，由外部赋值，赋值后不会改变
    private float minValue;//最小刻度，由外部赋值，赋值后不会改变
    private float borderLeftX, borderRightX;//左右边界值坐标，一旦赋值不会再改变
    //    private float originLengthBetweenStartXAndCenterX;//初始中心刻度x坐标和 绘制开始坐标之间的x轴距离，一旦定了就不会再改变


    //    private float midX;//当前中心刻度x坐标
    private float scaleStartX;//最小刻度线中心x坐标,从最小刻度开始画刻度
    private float scaleEndX;//最大刻度线中心x坐标

    private float lastX;

    //    private float originValue;//初始刻度对应的值
//    private float currentValue;//当前刻度对应的值

    private Paint linePaint;//刻度线画笔
    private Paint textPaint;//文字画笔
    private Paint pointerPaint;//指针画笔
    private Paint rangePaint;//范围画笔

    private int unavailableRangeColorGrey;//不可用的范围颜色
    private int rangeColorHighlightBlue;//选中颜色
    private int lineColorBlack;//黑色的线

    private Context context;

//    private String descri = "体重";//描述
//    private String unit = "kg";//刻度单位

    private VelocityTracker velocityTracker;//速度监测
    private float velocity;//当前滑动速度
    private float a = 1000000;//加速度
    private boolean continueScroll;//是否继续滑动

    private boolean isMeasured;
    private boolean usingSmallScale = false;//是否使用小刻度

    //</editor-fold>

    private IAvailableFilter mAvailableFilter;

    private OnValueChangeListener onValueChangeListener;


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
        rangeColorHighlightBlue = Color.parseColor("#7732B0DB");

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
        pointerPaint.setStyle(Paint.Style.STROKE);

        rangePaint = new Paint();
        rangePaint.setAntiAlias(true);
        rangePaint.setDither(true);
        rangePaint.setColor(rangeColorHighlightBlue);
        rangePaint.setStyle(Paint.Style.FILL);
    }
    //</editor-fold>

    //<editor-fold desc="外部初始化设置方法 ">


    /**
     * 设置刻度范围  最大值减最小值必须可以被 单元刻度值整除
     *
     * @param min        刻度范围起始值
     * @param max        刻度范围结束值
     * @param scaleValue 最小刻度值
     * @return
     */
    public HorizontalScaleView setRangeAndScale(float min, float max, float scaleValue) {
        this.minValue = min;
        this.maxValue = max;
        this.baseScaleValue = scaleValue;
        if (scaleValue <= 0)
            throw new IllegalArgumentException("Scale value can not be smaller than 0!");

        float totalCount = getScaleSpaceCountBetweenValues(minValue, maxValue) / scaleValue;
        if (totalCount != (int) totalCount)
            throw new IllegalArgumentException((maxValue - minValue) + " can not be divided by " + scaleValue);
//        originValue = (max + min) / 2;
//        currentValue = originValue;
        L.e(TAG, "setRange min:" + min + "  max:" + max);
        return this;
    }


    /**
     * 设置一个大的刻度包含有几个小的刻度
     *
     * @param count
     * @return
     */
    public HorizontalScaleView setSmallScaleSpaceCountInBig(int count) {
        this.smallScaleSpaceCountInBig = count;
        this.usingSmallScale = true;

        if (count <= 1)//1相当于没有 必须至少2
            throw new IllegalArgumentException("small scale count can not be smaller than 1");
        return this;
    }

    /**
     * 设置是否使用小刻度
     *
     * @param usingSmallScale
     * @return
     */
    public HorizontalScaleView setUsingSmallScale(boolean usingSmallScale) {
        this.usingSmallScale = usingSmallScale;

        return this;
    }


    /**
     * 设置左右指针的值
     *
     * @param leftValue
     * @param rightValue
     */
    public HorizontalScaleView setPointerPos(float leftValue, float rightValue) {
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

    /**
     * 重新绘制
     */
    public void reset() {
        isMeasured = false;
        initScaleData();
        invalidate();
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
            minScaleHeight = maxScaleHeight * 3 / 5;

//            rectWidth = bigScaleSpace;
//            rectHeight = bigScaleSpace / 2;

            initScaleData();
            isMeasured = true;
        }
    }

    /**
     * 初始化时计算 刻度相关数据
     */
    private void initScaleData() {
        if (baseScaleValue <= 0)
            return;

        if (usingSmallScale) {//使用小刻度时 根据小刻度数目计算出大刻度大小
            bigScaleSpace = SMALL_SCALE_SPACE * smallScaleSpaceCountInBig;
        } else {//不使用小刻度时 小刻度大小和大刻度一样
            bigScaleSpace = SMALL_SCALE_SPACE;
        }
        L.e(TAG, "initScaleData  bigScaleSpace:" + bigScaleSpace);

        //最左边从0开始，右边一直往右超出屏幕部分可以滚动
        borderLeftX = 0;
        borderRightX = borderLeftX + getScaleSpaceCountBetweenValues(minValue, maxValue) * SMALL_SCALE_SPACE;

        scaleStartX = borderLeftX;
        scaleEndX = borderRightX;

//        midX = width / 2;
//        originLengthBetweenStartXAndCenterX = midX - borderLeftX;

        leftPointerX = getXByValue(leftPointerValue);
        rightPointerX = getXByValue(rightPointerValue);

//        calculateCurrentScale();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (baseScaleValue <= 0)//刻度值未定，就不画了
            return;

        //drawLine 竖线的x 是线的中心坐标

        //画刻度线的框架，主要是刻度，文字和底部线
        drawScaleFrame(canvas);
        //画指针
        drawPointer(canvas);

    }

    /**
     * 画刻度线的框架，主要是刻度，文字和底部线
     *
     * @param canvas
     */
    private void drawScaleFrame(Canvas canvas) {
        //最少多个刻度画一个刻度文字，防止文字重叠
        int draw2TextSpaceCount = smallScaleSpaceCountInBig < 5 ? smallScaleSpaceCountInBig * 2 : smallScaleSpaceCountInBig;

        //画刻度线
        for (float i = minValue; i <= maxValue; i += baseScaleValue) {
            float valueX = getXByValue(i);//i值对应的x坐标

            //从最小值到当前值 之间右多少个空间
            int spaceCountFromMin = getScaleSpaceCountBetweenValues(minValue, i);
            //画刻度数字
            if (!usingSmallScale) {//不使用小刻度，全都是大刻度
                if (spaceCountFromMin % draw2TextSpaceCount == 0) {
                    //没有小刻度的时候 大刻度不能画太密，暂定5个一画
                    drawScaleText(canvas, subZeroAndDot(String.valueOf(i)), valueX);
                }
                drawBigScaleLine(canvas, valueX);  //画大刻度线
            } else {//使用小刻度 需要区分大小刻度线

                if (spaceCountFromMin % smallScaleSpaceCountInBig == 0) {
                    //总空间数是 一个 大刻度的空间数的倍数 则这个value是大刻度线
                    drawBigScaleLine(canvas, valueX);  //画大刻度线
                    if (spaceCountFromMin % draw2TextSpaceCount == 0) {
                        drawScaleText(canvas, subZeroAndDot(String.valueOf(i)), valueX);
                    }
                } else {
                    drawSmallScaleLine(canvas, valueX);
                }
            }

            if (i == maxValue) {//最大值后面就不会有不可用范围了
                continue;
            }

            if (mAvailableFilter != null && !mAvailableFilter.valueAvailable(i)) {

                float valueRightX = getXByValue(i + baseScaleValue);//i后面一个值对应的x坐标
                //值不可用
                rangePaint.setColor(unavailableRangeColorGrey);
                canvas.drawRect(valueX, ruleHeight - maxScaleHeight, valueRightX, ruleHeight, rangePaint);
            }
        }

        //画底部横线
        linePaint.setStrokeWidth(LINE_WIDTH);
        linePaint.setColor(unavailableRangeColorGrey);
        canvas.drawLine(scaleStartX, ruleHeight, scaleEndX, ruleHeight, linePaint);
    }

    /**
     * 画大刻度线
     *
     * @param canvas
     * @param valueX
     */
    private void drawBigScaleLine(Canvas canvas, float valueX) {
        //画大刻度线
        linePaint.setStrokeWidth(SCALE_LINE_WIDTH_BIG);
        linePaint.setColor(lineColorBlack);

        canvas.drawLine(valueX, ruleHeight - maxScaleHeight, valueX, ruleHeight, linePaint);
    }

    /**
     * 画小刻度线
     *
     * @param canvas
     * @param valueX
     */
    private void drawSmallScaleLine(Canvas canvas, float valueX) {
        //画大刻度线
        linePaint.setStrokeWidth(SCALE_LINE_WIDTH_SMALL);
        linePaint.setColor(lineColorBlack);

        canvas.drawLine(valueX, ruleHeight - minScaleHeight,
                valueX, ruleHeight, linePaint);
    }

    /**
     * 画刻度文字
     *
     * @param canvas
     */
    private void drawScaleText(Canvas canvas, String scaleText, float valueX) {
        Rect rect = new Rect();
        textPaint.getTextBounds(scaleText, 0, scaleText.length(), rect);
        int w = rect.width();
        int h = rect.height();
        float scaleTextBaseLineY = ruleHeight - maxScaleHeight - (float) h / 2;
//        L.e(TAG, "ruleHeight:" + ruleHeight + "   maxScaleHeight:" + maxScaleHeight + "  textHeight:" + h + "   Y:" + scaleTextBaseLineY);
        //画刻度文字
        canvas.drawText(scaleText, valueX - (float) w / 2, scaleTextBaseLineY, textPaint);
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
        float pointerCircleRadius = pointerTouchWidth * 3 / 10;//大头针底部圆半径
        float pointerLineBottomY = ruleHeight - pointerCircleRadius;
        //画左边指针底部圆
        canvas.drawCircle(leftPointerX, ruleHeight, pointerCircleRadius, pointerPaint);
        //画左边指针
        canvas.drawLine(leftPointerX, 0, leftPointerX, pointerLineBottomY, pointerPaint);

        //画右边指针底部圆
        canvas.drawCircle(rightPointerX, ruleHeight, pointerCircleRadius, pointerPaint);
        //画右边指针
        canvas.drawLine(rightPointerX, 0, rightPointerX, pointerLineBottomY, pointerPaint);

        boolean shouldDrawSelectedRange = true;

        for (float i = leftPointerValue; i <= rightPointerValue; i++) {
            if (mAvailableFilter != null && !mAvailableFilter.valueAvailable(i)) {
                //选中范围内 该值不可用
                shouldDrawSelectedRange = false;
                break;
            }
        }
        if (shouldDrawSelectedRange) {
            rangePaint.setColor(rangeColorHighlightBlue);
            canvas.drawRect(leftPointerX, ruleHeight - maxScaleHeight, rightPointerX, ruleHeight, rangePaint);
        }
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
//                    calculateCurrentScale();
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
                adjustScale();
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
                if (currentMovingPointer != -1) {
                    if (null != onValueChangeListener) {//通知外部 指针值确定了
                        onValueChangeListener.onValueChanged(leftPointerValue, rightPointerValue);
                    }
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
     * 获取两个值之间 刻度空间的数量
     *
     * @param startValue
     * @param endValue
     * @return
     */
    public int getScaleSpaceCountBetweenValues(float startValue, float endValue) {
        return (int) ((endValue - startValue) / baseScaleValue);
    }

    /**
     * 根据触摸的x坐标确定是不是在移动pointer 以及是第几个pointer
     *
     * @param x 0-左指针 1-右指针
     * @return
     */
    private int getMovingPointerIndex(float x) {
        if (x <= leftPointerX) {
            //左指针左边范围
            L.e(TAG, "getMovingPointerIndex x:" + x + "   左边界:" + (leftPointerX - pointerTouchWidth / 2) + " 左指针x：" + leftPointerX);
            if (x > leftPointerX - pointerTouchWidth / 2) {
                return 0;
            }
        } else if (x >= rightPointerX) {
            //右指针右边范围
            L.e(TAG, "getMovingPointerIndex x:" + x + "   右边界：" + (rightPointerX + pointerTouchWidth / 2) + " 右指针x：" + rightPointerX);
            if (x < rightPointerX + pointerTouchWidth / 2) {
                return 1;
            }
        } else {
            //在两个指针之间，需要根据距离来判断是在操作哪个
            float touchDistanceWithLeftPointerX = x - leftPointerX;
            float touchDistanceWithRightPointerX = rightPointerX - x;
            L.e(TAG, "getMovingPointerIndex x:" + x + "  距离左指针 x:" + touchDistanceWithLeftPointerX + " 距离右指针：" + touchDistanceWithRightPointerX);

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
        return scaleStartX + getScaleSpaceCountBetweenValues(minValue, value) * SMALL_SCALE_SPACE;
    }

    /**
     * 计算x坐标处的 刻度值,此处已选择了 距离x最近的刻度值
     *
     * @param x
     */
    private float getValueByX(float x) {
        float scaleValue;
        if (x <= scaleStartX) {
            //x值小于开始值
            scaleValue = minValue;
        } else if (x >= scaleEndX) {
            scaleValue = maxValue;
        } else {
            int scaleSpaceCount = (int) ((x - scaleStartX) / SMALL_SCALE_SPACE);//不存在四舍五入 必定比真实的小
            float valueX = scaleStartX + scaleSpaceCount * SMALL_SCALE_SPACE;
            float value = minValue + scaleSpaceCount * baseScaleValue;

            if (valueX + SMALL_SCALE_SPACE / 2 > x) {
                scaleValue = value;
            } else if (valueX + SMALL_SCALE_SPACE / 2 <= x) {
                scaleValue = value + baseScaleValue;
            } else {
                scaleValue = value;
                L.e(TAG, "wtffffffffffffffffffffffff getValueByX 不可能走这里");
            }
        }
        L.e(TAG, "getValueByX x:" + x + "  scaleValue:" + scaleValue + "  scaleStartX:" + scaleStartX + " --scaleEndX" + scaleEndX);
        return scaleValue;
    }

//    /**
//     * 初始中心点坐标
//     *
//     * @param x
//     */
//    private void calculateCenterScaleValueByX(int x) {
//        currentValue = calculateScaleValueByX(x);
////        mHandler.sendEmptyMessage(0);
//    }

//    //计算当前刻度
//    private void calculateCurrentScale() {
//        calculateCenterScaleValueByX(width / 2);
//    }

    /**
     * 获取最近的 刻度值的x坐标
     *
     * @param x
     * @return
     */
    private float getNearestScaleX(float x) {
        float scale = getValueByX(x);
        float xCurrent = getXByValue(scale);//实际的刻度 对应的x值
//        float xLeft = getXByValue(scale - 1);//左边一个刻度
//        float xRight = getXByValue(scale + 1);//右边一个刻度
//        if (xCurrent >= x) {
//            xRight = xCurrent;
//        } else {
//            xLeft = xCurrent;
//        }

        L.e(TAG, "getNearestScaleX x:" + x + " scale:" + scale + "  xCurrent:" + xCurrent);

//        return Math.abs(x - xLeft) < Math.abs(xRight - x) ? xLeft : xRight;
        return xCurrent;
    }

    //</editor-fold>

    //<editor-fold desc="坐标移动">

    private void moveLeftPointerByX(float offsetX) {
        if (leftPointerX - offsetX + SMALL_SCALE_SPACE > rightPointerX) {
            //左指针右移不可以超过右指针
            return;
        }
        leftPointerX -= offsetX;
        leftPointerValue = (int) getValueByX(leftPointerX);
        L.e(TAG, "moveLeftPointerByX leftPointerX:" + leftPointerX + "   leftPointerValue:" + leftPointerValue);
    }

    private void moveRightPointerByX(float offsetX) {
        if (rightPointerX - offsetX - SMALL_SCALE_SPACE < leftPointerX) {
            //右指针左移不可以超过左指针
            return;
        }
        rightPointerX -= offsetX;
        rightPointerValue = (int) getValueByX(rightPointerX);
        L.e(TAG, "moveRightPointerByX rightPointerValue:" + rightPointerValue);
    }

    /**
     * 主要的 x 有用的值 移动offsetX的距离
     *
     * @param offsetX
     */
    private void moveViewByX(float offsetX) {
        scaleStartX -= offsetX;
        scaleEndX -= offsetX;
//        midX -= offsetX;

        leftPointerX -= offsetX;
        rightPointerX -= offsetX;
    }

    /**
     * 设置刻度开始的x位置 同时设置其他相关值
     *
     * @param newScaleStartX
     */
    private void setScaleStartXAndFixOtherData(float newScaleStartX) {
        scaleStartX = newScaleStartX;
        scaleEndX = scaleStartX + borderRightX - borderLeftX;
//        midX = originLengthBetweenStartXAndCenterX + scaleStartX;

        leftPointerX = getXByValue(leftPointerValue);
        rightPointerX = getXByValue(rightPointerValue);

        L.e(TAG, "setScaleStartXAndFixOtherData scaleStartX:" + scaleStartX + " leftPointerX:" + leftPointerX);
    }

    //停止触摸时 修正坐标问题 如指针线超出范围时 重置回边界处
    private void adjustScale() {
        if (overScrollX + borderLeftX < scaleStartX) {
            //view开始绘制的左边位置大于限制位置了
            setScaleStartXAndFixOtherData(overScrollX + borderLeftX);
        } else if (width - overScrollX > scaleStartX + borderRightX - borderLeftX) {
            //view最右边绘制的位置 x坐标小于限制位置
            setScaleStartXAndFixOtherData(width - overScrollX - (borderRightX - borderLeftX));
        }
        //修正指针位置
        if (currentMovingPointer == 0) {// 左指针移动
            leftPointerX = getNearestScaleX(leftPointerX);
            leftPointerValue = (int) getValueByX(leftPointerX);
        } else if (currentMovingPointer == 1) {// 右指针移动
            rightPointerX = getNearestScaleX(rightPointerX);
            rightPointerValue = (int) getValueByX(rightPointerX);
        }
        L.e(TAG, "adjustScale leftPointerX:" + leftPointerX);

        postInvalidate();
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
//                calculateCurrentScale();
                adjustScale();
                if (continueScroll && velocityAbs > 0) {
                    post(this);
                } else {
                    continueScroll = false;
                }
            }
        }).start();
    }
    //</editor-fold>

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public interface IAvailableFilter {
        boolean valueAvailable(float value);
    }
}

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