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
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;


import com.cgy.mycollections.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by RB-cgy on 2018/5/9.
 * 自定义支付密码输入框
 */
public class InputPasswordView extends AppCompatEditText {

    private Context mContext;
    private Blink mBlink;
    boolean shouldBlink = true;//默认光标闪
    boolean cursorIsVisible = false;//默认光标没有显示

    static final int BLINK = 500;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startCenterX;
    /**
     * 第一个格子中心的y坐标
     */
    private float startY;


//    private float cX;


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
//    private int borderColor = Color.GRAY;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 第一个分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;

    /**
     * 默认的两个文字之间的距离，默认为0
     */
    private int textMargin = 0;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;
    private int focusedColor = Color.BLUE;
    /**
     * view的外边框
     */
    private RectF rectF = new RectF();
    //    private RectF focusedRecF = new RectF();
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
//    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
//    private Paint circlePaint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;

    public InputPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

        setShouldBlink(shouldBlink);//默认闪动光标
    }

    /**
     * 手动设置最大输入数目
     *
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.InputPasswordView);
        maxCount = typedArray.getInt(R.styleable.InputPasswordView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.InputPasswordView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.InputPasswordView_bottomLineColor, Color.GRAY);
        radius = typedArray.getDimensionPixelOffset(R.styleable.InputPasswordView_radius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.InputPasswordView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.InputPasswordView_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.InputPasswordView_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.InputPasswordView_rectAngle, rectAngle);
        focusedColor = typedArray.getColor(R.styleable.InputPasswordView_focusedColor, focusedColor);
        shouldBlink = typedArray.getBoolean(R.styleable.InputPasswordView_blink, true);
        textMargin = typedArray.getDimensionPixelOffset(R.styleable.InputPasswordView_textMargin, 0);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

//        borderColor = ContextCompat.getColor(mContext, android.R.color.black);

//        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        textPaint = getPaint(5, Paint.Style.FILL, circleColor);
        textPaint.setTextSize(getTextSize());

//        bottomLineColor = ContextCompat.getColor(getContext(), R.color.grey_5A6978);
        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(divideLineWidth, Paint.Style.STROKE, divideLineColor);//必须用stroke空心

//        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, divideLineColor);

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

        divideLineWStartX = (w - (maxCount - 1) * textMargin) / maxCount;

        startCenterX = (float) divideLineWStartX / 2;
        startY = (float) h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    private float getCenterX(int index) {
        return startCenterX + textMargin * index + index * 2 * startCenterX;
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
        drawPsd(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {
        //一个个画框
        for (int i = 0; i < maxCount; i++) {
            int leftX = i * divideLineWStartX + i * textMargin;
            int rightX = leftX + divideLineWStartX;

            RectF cellRect = new RectF();
            cellRect.set(leftX, 0, rightX, height);

            canvas.drawRoundRect(cellRect, rectAngle, rectAngle, borderPaint);
//                canvas.drawLine(x,
//                        0,
//                        x,
//                        height,
//                        divideLinePaint);
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
     * 或者直接画密码文字
     *
     * @param canvas
     */
    private void drawPsd(Canvas canvas) {

        if (isPasswordInputType(getInputType()))//密码模式，画圆
            for (int i = 0; i < textLength; i++) {
                //画密码圆点
                canvas.drawCircle(getCenterX(i),
                        startY,
                        radius,
                        textPaint);
            }
        else {//其他模式 画文字
            String text = getText().toString();
            float baselineToCenterY = getBaselineToCenterY(textPaint);

            for (int i = 0; i < textLength; i++) {
                String textInPlaceI = String.valueOf(text.charAt(i));
                float textWidthInPlaceI = textPaint.measureText(textInPlaceI);
                //画密码圆点
                canvas.drawText(textInPlaceI, getCenterX(i) - textWidthInPlaceI / 2,
                        startY + baselineToCenterY,
                        textPaint);
            }
        }

        //模拟光标
        if (shouldBlink) {
            if (!cursorIsVisible) {
                float cursorOffset;
                float cursorLeftX;
                if (textLength > 0) {
                    cursorOffset = textPaint.measureText(String.valueOf(getText().charAt(textLength - 1))) / 2 + 5;
                    cursorLeftX = getCenterX(textLength - 1) + cursorOffset;//文字偏右一点画光标
                } else {
                    cursorLeftX = getCenterX(0);
                }

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

            InputPasswordView.this.removeCallbacks(this);

            if (InputPasswordView.this.getLayout() != null) {
                InputPasswordView.this.invalidate();
            }

            InputPasswordView.this.postDelayed(this, BLINK);
        }

        void cancel() {
            if (!mCancelled) {
                InputPasswordView.this.removeCallbacks(this);
                mCancelled = true;
            }
        }

        void unCancel() {
            mCancelled = false;
        }
    }

    static boolean isPasswordInputType(int inputType) {
        final int variation =
                inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION);
        return variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     *
     * @param p
     * @return 基线和centerY的距离
     */
    public static float getBaselineToCenterY(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }
}
