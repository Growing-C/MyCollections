package com.cgy.mycollections.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;


import com.cgy.mycollections.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by RB-cgy on 2018/5/9.
 * 自定义支付密码输入框
 */
public class BlinkInputPasswordView extends AppCompatEditText {

    private Context mContext;
    private Blink mBlink;
    boolean shouldBlink = true;//默认光标闪
    boolean cursorIsVisible = false;//默认光标没有显示

    static final int BLINK = 500;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startCenterX;
    private float startY;


    private float cX;


    /**
     * 实心圆的半径
     */
    private int radius = 10;
    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;
    /**
     * 最大输入位数
     */
    private int maxCount = 10;
    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColor = Color.GRAY;

    /**
     * 分割线的颜色
     */
    private int borderColor = Color.GRAY;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;
    private int focusedColor = Color.BLUE;
    private RectF rectF = new RectF();
    private RectF focusedRecF = new RectF();
    private int psdType = 0;
    private final static int psdType_weChat = 0;
    private final static int psdType_bottomLine = 1;

    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;
    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;

    public BlinkInputPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

        setShouldBlink(true);//默认闪动光标
    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BlinkInputPasswordView);
        maxCount = typedArray.getInt(R.styleable.BlinkInputPasswordView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.BlinkInputPasswordView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.BlinkInputPasswordView_bottomLineColor, bottomLineColor);
        radius = typedArray.getDimensionPixelOffset(R.styleable.BlinkInputPasswordView_radius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.BlinkInputPasswordView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.BlinkInputPasswordView_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.BlinkInputPasswordView_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.BlinkInputPasswordView_rectAngle, rectAngle);
        focusedColor = typedArray.getColor(R.styleable.BlinkInputPasswordView_focusedColor, focusedColor);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        borderColor = ContextCompat.getColor(mContext, android.R.color.black);

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLineColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(divideLineWidth, Paint.Style.STROKE, borderColor);//必须用stroke空心

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startCenterX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
//        super.onDraw(canvas);

        switch (psdType) {
            case psdType_weChat:
                drawWeChatBorder(canvas);
                break;
            case psdType_bottomLine:
                drawBottomBorder(canvas);
                break;
        }

//        drawItemFocused(canvas, position);
        drawPsdCircle(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }

    }


    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {
        canvas.drawLine(0,
                height,
                getWidth(),
                height, bottomLinePaint);
    }

    /**
     * 画密码实心圆,从中间开始画
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas) {
        int width = getWidth();//view宽度

        int psdWidth = (int) (textLength * startCenterX * 2);//startCenterX*2得出一个密码所占的长度，此参数为已输入密码总长度
        int psdLeftX = width / 2 - psdWidth / 2;//密码组左边起始的x位置

        for (int i = 0; i < textLength; i++) {
            //画密码圆点
            canvas.drawCircle(psdLeftX + startCenterX + i * 2 * startCenterX,
                    startY,
                    radius,
                    circlePaint);
        }

        //模拟光标
        if (shouldBlink) {
            if (!cursorIsVisible) {
                float cursorLeftX = psdLeftX + textLength * 2 * startCenterX;
                if (cursorLeftX > width)
                    return;
                float offset = height / 4f;
                float cursorBottom = height - offset;
                canvas.drawLine(cursorLeftX,
                        offset,
                        cursorLeftX,
                        cursorBottom, bottomLinePaint);
                cursorIsVisible = true;
            } else {
                cursorIsVisible = false;
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.position = start + lengthAfter;
        textLength = text.toString().length();

        if (textLength == maxCount) {
        }

        invalidate();

    }

    /**
     * 设置是否显示 光标
     *
     * @param shouldBlink
     */
    public void setShouldBlink(boolean shouldBlink) {
        this.shouldBlink = shouldBlink;
        if (shouldBlink)
            makeBlink();//text change的时候makeBlink
        else
            suspendBlink();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        suspendBlink();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        switch (screenState) {
            case View.SCREEN_STATE_ON:
                resumeBlink();
                break;
            case View.SCREEN_STATE_OFF:
                suspendBlink();
                break;
        }
    }

    private void suspendBlink() {
        if (mBlink != null) {
            mBlink.cancel();
        }
    }

    private void resumeBlink() {
        if (mBlink != null) {
            mBlink.unCancel();
            makeBlink();
        }
    }

    public void makeBlink() {
        if (shouldBlink) {
            if (mBlink == null) mBlink = new Blink();
            removeCallbacks(mBlink);
            postDelayed(mBlink, BLINK);
        } else {
            if (mBlink != null) removeCallbacks(mBlink);
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }

    private class Blink implements Runnable {
        private boolean mCancelled;

        public void run() {
            if (mCancelled) {
                return;
            }

            BlinkInputPasswordView.this.removeCallbacks(this);

            if (BlinkInputPasswordView.this.getLayout() != null) {
                BlinkInputPasswordView.this.invalidate();
            }

            BlinkInputPasswordView.this.postDelayed(this, BLINK);
        }

        void cancel() {
            if (!mCancelled) {
                BlinkInputPasswordView.this.removeCallbacks(this);
                mCancelled = true;
            }
        }

        void unCancel() {
            mCancelled = false;
        }
    }

}

//    private void drawItemFocused(Canvas canvas, int position) {
//        if (position > maxCount - 1) {
//            return;
//        }
//        focusedRecF.set(position * divideLineWStartX, 0, (position + 1) * divideLineWStartX,
//                height);
//        canvas.drawRoundRect(focusedRecF, rectAngle, rectAngle, getPaint(3, Paint.Style.STROKE, focusedColor));
//    }


//画一个个item底部的线  中间有空白
//        for (int i = 0; i < maxCount; i++) {
//            cX = startCenterX + i * 2 * startCenterX;
//            canvas.drawLine(cX - bottomLineLength / 2,
//                    height,
//                    cX + bottomLineLength / 2,
//                    height, bottomLinePaint);
//        }


//    @Override
//    protected void onSelectionChanged(int selStart, int selEnd) {
//        super.onSelectionChanged(selStart, selEnd);
//
//        //保证光标始终在最后
//        if (selStart == selEnd) {
//            setSelection(getText().length());
//        }
//    }