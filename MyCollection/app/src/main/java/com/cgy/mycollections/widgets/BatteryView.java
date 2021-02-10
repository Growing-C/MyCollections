package com.cgy.mycollections.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.cgy.mycollections.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author: chengy
 * created on: 2020-12-10 11:16
 * description: 充放电 电池自定义view
 */
public class BatteryView extends View {
    private static final String TAG = BatteryView.class.getSimpleName();

    private static final int BATTERY_FRAME_THICKNESS = 5;//电池框厚度
    private static final int BATTERY_LIMIT_LINE_THICKNESS = 6;//限制线宽度
    private static final int BATTERY_GLINT_LIGHT_THICKNESS = 26;//电池使用中闪光的厚度

    private int mFullPowerWidth; //满电量时电池体的X坐标宽度
    private int mFullPowerHeight;//满电量高度

    private int mBatteryLevel = 0;//电量 0-100
    private int mLimitLinePercent = 0;//限制线位置 0-100

    private final Rect mBatteryFrameRect;//电池外边框位置rect
    private final Rect mBatteryBgRect;//电池槽背景绘制rect
    private final Rect mBatteryLevelRect;//绘制电量的矩形,满电量和电池槽背景一样
    private final Rect mLimitLineRect;//限制线绘制的rect
    private final Rect mBatteryGlintRect;//电池使用中闪光
    private final Rect mBatteryChargingAnimRect;//电池充电中动画

    private final Rect mBatteryLevelSrcRect;//要绘制的截取电量bitmap的大小

    private final Rect mBatteryChargingAnimSrcRect;//要绘制的截取充电动画bitmap的大小

    private final Paint mBitmapPaint;//画图片的画笔

    private Bitmap mBatteryBgBitmap;//电池槽
    private Bitmap mBatteryFrameBitmap;//电池框
    private Bitmap mBatteryLevelBitmap;//电量
    private Bitmap mBatteryLimitLineBitmap;//电量限制线
    private Bitmap mBatteryGlintBitmap;//电池放电 中闪光
    private Bitmap mBatteryChargingAnimBitmap;//电池充电中动画效果

    private int mBatteryChargingAnimRectWidth;//电池充电中动画效果的宽度，根据宽高比 缩放到当前电池槽的宽度

    private boolean mIsDischarging = false;//是否在对外供电，对外供电的时候周围高亮

    private boolean mIsCharging = false;//是否正在充电，充电的时候有个动效

    private BatteryLevelState mBatteryLevelState = BatteryLevelState.BATTERY_LEVEL_NORMAL;

    private AnimatorSet mChargingAnimator;//充电中动画
    private AnimatorSet mBatteryLevelAddAnimator;//电量增加动画
    private AnimatorSet mBatteryLevelFirstSetAnimator;//首次设置电量时的增加动画

    //-------------首次设置电量相关---------
    private final Path mFirstSetBatteryLevelClipPath = new Path();//首次设置电量时，动画的clip path
    private boolean mShouldClipBatteryLevel = false;//是否需要裁剪电量视图

    float mBatteryColumnWidth = 0;//电量线宽，设置后不变

    int mColumnGrow = 2;//高度增长，可用于控制右边不是最大高度的电量条数目
    Map<Float, RectF> mBatteryColumnClipMap = new HashMap<>();//裁剪的rect集合
    int mLastGrowth = 0;
    //-------------------------------------------

    public enum BatteryLevelState {
        BATTERY_LEVEL_NORMAL,//电量正常，绿色
        BATTERY_LEVEL_LOW,//电量低 续航低于60km，橙色
        BATTERY_LEVEL_DANGEROUS,//电量危险 续航低于30，红色
        BATTERY_LEVEL_EMPTY,//电量为空，续航低于20 ， 默认值
        BATTERY_LEVEL_CHARGING;//充电中,目前默认为绿色

        /**
         * 从接口传过来的颜色值转换为电池状态
         *
         * @param colorControlProp 颜色值
         * @return 电池状态
         */
        public static BatteryLevelState parseFromElectricityColorControlProp(int colorControlProp) {
            switch (colorControlProp) {
                case 1://电量红色
                    return BATTERY_LEVEL_DANGEROUS;
                case 2://电量橙色
                    return BATTERY_LEVEL_LOW;
                case 3://电量绿色
                    return BATTERY_LEVEL_NORMAL;
                case 0://null
                default:
                    return BATTERY_LEVEL_EMPTY;
            }
        }
    }

    /**
     * 设置电池电量状态，主要是颜色
     *
     * @param batteryView       电池view
     * @param batteryLevelState 电量状态
     */
    @BindingAdapter({"batteryLevelState"})
    public static void setBatteryLevelState(BatteryView batteryView, BatteryLevelState batteryLevelState) {
        Log.d("test", "invoke setBatteryLevelState batteryLevelState:" + batteryLevelState);
        if (batteryLevelState != null) {
            batteryView.setBatteryLevelState(batteryLevelState);
        }
    }

    /**
     * 设置电量
     *
     * @param batteryView  电池view
     * @param batteryLevel 电量 0-100
     */
    @BindingAdapter({"batteryLevel"})
    public static void setBatteryLevel(BatteryView batteryView, int batteryLevel) {
        batteryView.setBatteryLevel(batteryLevel);
    }

    /**
     * 设置是否是放电中状态
     *
     * @param batteryView   电池view
     * @param isDischarging true-放电 false-不在放电
     */
    @BindingAdapter({"isDischarging"})
    public static void setIsDischarging(BatteryView batteryView, boolean isDischarging) {
        batteryView.setIsDischarging(isDischarging);
    }

    /**
     * 设置是否是充电中状态
     *
     * @param batteryView 电池view
     * @param isCharging  true-充电 false-不在充电
     */
    @BindingAdapter({"isCharging"})
    public static void setIsCharging(BatteryView batteryView, boolean isCharging) {
        if (isCharging) {
            batteryView.startCharging();
        } else {
            batteryView.stopCharging();
        }
    }

    /**
     * 设置限制线位置
     *
     * @param batteryView      电池view
     * @param limitLinePercent 位置 0-100
     */
    @BindingAdapter({"limitLine"})
    public static void setLimitLinePercent(BatteryView batteryView, int limitLinePercent) {
        batteryView.setLimitLinePercent(limitLinePercent);
    }

    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setDither(true);

        mBatteryFrameRect = new Rect();
        mBatteryBgRect = new Rect();
        mBatteryLevelRect = new Rect();
        mLimitLineRect = new Rect();
        mBatteryGlintRect = new Rect();
        mBatteryChargingAnimRect = new Rect();

        mBatteryLevelSrcRect = new Rect();
        mBatteryChargingAnimSrcRect = new Rect();

        initBatteryImages();
    }


    /**
     * 初始化电池相关图片
     */
    private void initBatteryImages() {
        mBatteryBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_bg);
        mBatteryFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_frame);
        mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level);
        mBatteryLimitLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_limit_line);
        mBatteryGlintBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_working_glint_light);
        mBatteryChargingAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_charging_anim);

        mBatteryLevelSrcRect.left = 0;
        mBatteryLevelSrcRect.top = 0;
        mBatteryLevelSrcRect.bottom = mBatteryLevelBitmap.getHeight();
        mBatteryLevelSrcRect.right = 0;

        mBatteryChargingAnimSrcRect.left = 0;
        mBatteryChargingAnimSrcRect.top = 0;
        mBatteryChargingAnimSrcRect.bottom = mBatteryChargingAnimBitmap.getHeight();
        mBatteryChargingAnimSrcRect.right = mBatteryChargingAnimBitmap.getWidth();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mChargingAnimator != null) {
            mChargingAnimator.cancel();
            mChargingAnimator = null;
        }
        if (mBatteryLevelAddAnimator != null) {
            mBatteryLevelAddAnimator.cancel();
            mBatteryLevelAddAnimator = null;
        }

        destroyBatteryLevelFirstSetAnim();
    }

    /**
     * 开始充电
     */
    public void startCharging() {
        if (mIsCharging)
            return;
        this.mIsCharging = true;
        setBatteryLevelState(BatteryLevelState.BATTERY_LEVEL_CHARGING);

        if (mChargingAnimator == null) {
            mChargingAnimator = new AnimatorSet();
            ValueAnimator chargingXAnimator = ObjectAnimator.ofFloat(this, "chargingRightX",
                    0, mBatteryBgRect.right + mBatteryChargingAnimRectWidth + 500);//500 当作间隔使用
            chargingXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            chargingXAnimator.setRepeatMode(ValueAnimator.RESTART);

            mChargingAnimator.playTogether(chargingXAnimator);
            mChargingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mChargingAnimator.setDuration(2280);//总时间，包含人为制造的间隔,流光动效从左到右动效的时间是1.9s，第一次和第二次动效的间隔时间是0.38s
        }
        mChargingAnimator.start();
    }

    /**
     * 停止充电
     */
    public void stopCharging() {
        if (!mIsCharging)
            return;

        this.mIsCharging = false;
        if (mChargingAnimator != null) {
            mChargingAnimator.cancel();
        }

        invalidate();
    }

    /**
     * 设置是否在对外放电
     *
     * @param mIsDischarging true-对外放电
     */
    public void setIsDischarging(boolean mIsDischarging) {
        this.mIsDischarging = mIsDischarging;
        invalidate();
    }

    /**
     * 设置电池当前电量的状态图
     */
    public void setBatteryLevelState(@NonNull BatteryLevelState state) {
        if (this.mBatteryLevelState == state) {
            return;
        }
        this.mBatteryLevelState = state;
        switch (state) {
            case BATTERY_LEVEL_CHARGING:
            case BATTERY_LEVEL_NORMAL:
                mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level);
                break;
            case BATTERY_LEVEL_LOW:
                mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level_low);
                break;
            case BATTERY_LEVEL_DANGEROUS:
                mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level_dangerous);
                break;
            default:
                break;
        }
        invalidate();
    }

    /**
     * 设置电量
     *
     * @param battery 电量 0-100
     */
    public void setBatteryLevel(int battery) {
        if (battery > 100 || battery < 0 || battery == mBatteryLevel) {
            return;
        }
        int lastBatteryLevel = mBatteryLevel;
        this.mBatteryLevel = battery;

        //先取消可能存在的动画
        destroyBatteryLevelFirstSetAnim();
        if (mBatteryLevelAddAnimator != null) {
            mBatteryLevelAddAnimator.cancel();
        }

        if (battery > lastBatteryLevel) {
            if (lastBatteryLevel == 0) {//之前的电量为0，本次设置电量使用不同的动画
                startBatteryLevelFirstSetAnim(mBatteryLevel);
            } else {//正常的电量增加动画
                startBatteryLevelAddAnim(lastBatteryLevel, battery);
            }
        } else {
            setBatteryLevelFloat(mBatteryLevel);
        }
    }

    /**
     * 首次设置电量时播放动画
     *
     * @param battery 首次设置的电量
     */
    private void startBatteryLevelFirstSetAnim(int battery) {
        mBatteryLevelRect.right = mBatteryBgRect.left + mFullPowerWidth * battery / 100;//设置电量右边界(最终的值)
        mBatteryLevelSrcRect.right = mBatteryLevelBitmap.getWidth() * battery / 100;//设置电量裁剪图标的右边界(最终的值)

        Log.i(TAG, "startBatteryLevelFirstSetAnim left:" + mBatteryLevelRect.left);
        Log.i(TAG, "startBatteryLevelFirstSetAnim right:" + mBatteryLevelRect.right);
        int batteryRectWidth = mBatteryLevelRect.right - mBatteryLevelRect.left;
        mBatteryLevelFirstSetAnimator = new AnimatorSet();
        mBatteryLevelFirstSetAnimator.setInterpolator(new LinearInterpolator());

        int duration = 36000 * battery / 100;//满电量播放时间是5秒，其他的电量按照比例缩减
        mBatteryLevelFirstSetAnimator.setDuration(duration);

        ValueAnimator batteryLevelFirstAddAnim = ObjectAnimator.ofFloat(this, "batteryLevelClipRightX",
                mBatteryLevelRect.left, mBatteryLevelRect.right + batteryRectWidth);//右边界使用超出当前电量右边的值
        ValueAnimator batteryLevelItemGrowthAnim = ObjectAnimator.ofInt(this, "batteryLevelClipColumnGrowth",
                0, 2 * battery);

        mBatteryLevelFirstSetAnimator.playTogether(batteryLevelFirstAddAnim, batteryLevelItemGrowthAnim);

        mBatteryLevelFirstSetAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel");
                mShouldClipBatteryLevel = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd");
                mShouldClipBatteryLevel = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart");
                mShouldClipBatteryLevel = true;
                mBatteryColumnClipMap.clear();
            }
        });

        mBatteryLevelFirstSetAnimator.start();
    }

    /**
     * 取消首次设置电量的动画
     */
    private void destroyBatteryLevelFirstSetAnim() {
        if (mBatteryLevelFirstSetAnimator != null) {
            mBatteryLevelFirstSetAnimator.cancel();
            mBatteryLevelFirstSetAnimator = null;
        }
        mBatteryColumnClipMap.clear();
    }

    /**
     * 开始电量增加的动画
     */
    private void startBatteryLevelAddAnim(float startBatteryLevelFloat, float endBatteryLevelFloat) {
        if (mBatteryLevelAddAnimator == null) {
            mBatteryLevelAddAnimator = new AnimatorSet();
            mBatteryLevelAddAnimator.setInterpolator(new LinearInterpolator());
            mBatteryLevelAddAnimator.setDuration(600);
        }

        List<Animator> childAnimators = mBatteryLevelAddAnimator.getChildAnimations();
        if (childAnimators.isEmpty()) {
            ValueAnimator batteryLevelAddAnim = ObjectAnimator.ofFloat(this, "batteryLevelFloat",
                    startBatteryLevelFloat, endBatteryLevelFloat);
            mBatteryLevelAddAnimator.play(batteryLevelAddAnim);
        } else {
            for (Animator animator : childAnimators) {
                if (animator instanceof ValueAnimator) {
                    ((ValueAnimator) animator).setFloatValues(startBatteryLevelFloat, endBatteryLevelFloat);
                }
            }
        }

        mBatteryLevelAddAnimator.start();
    }

    /**
     * 设置限制线的百分比
     *
     * @param limitLinePercent 限制线百分比
     */
    public void setLimitLinePercent(int limitLinePercent) {
        this.mLimitLinePercent = limitLinePercent > 100 ? 100 : Math.max(limitLinePercent, 0);

        mLimitLineRect.left = mBatteryBgRect.left + mFullPowerWidth * mLimitLinePercent / 100 - BATTERY_LIMIT_LINE_THICKNESS / 2;
        mLimitLineRect.right = mLimitLineRect.left + BATTERY_LIMIT_LINE_THICKNESS;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int specWidthSize = View.MeasureSpec.getSize(widthMeasureSpec);//宽
        int specHeightSize = View.MeasureSpec.getSize(heightMeasureSpec);//高

        //设置电池使用中闪光，这个最大
        mBatteryGlintRect.left = 0;
        mBatteryGlintRect.top = 0;
        mBatteryGlintRect.right = specWidthSize;
        mBatteryGlintRect.bottom = specHeightSize;

        //设置电池外框，这个第二大
        mBatteryFrameRect.left = BATTERY_GLINT_LIGHT_THICKNESS;
        mBatteryFrameRect.top = BATTERY_GLINT_LIGHT_THICKNESS;
        mBatteryFrameRect.right = specWidthSize - BATTERY_GLINT_LIGHT_THICKNESS;
        mBatteryFrameRect.bottom = specHeightSize - BATTERY_GLINT_LIGHT_THICKNESS;

        //设置电池槽矩形，第三大
        mBatteryBgRect.left = mBatteryFrameRect.left + BATTERY_FRAME_THICKNESS;
        mBatteryBgRect.top = mBatteryFrameRect.top + BATTERY_FRAME_THICKNESS;
        mBatteryBgRect.right = mBatteryFrameRect.right - BATTERY_FRAME_THICKNESS;
        mBatteryBgRect.bottom = mBatteryFrameRect.bottom - BATTERY_FRAME_THICKNESS;

        //电量总的x轴宽度
        mFullPowerWidth = mBatteryBgRect.right - mBatteryBgRect.left;
        //电量的总的y轴高度
        mFullPowerHeight = mBatteryBgRect.bottom - mBatteryBgRect.top;

        //设置电量矩形，根据电量设置右边界
        mBatteryLevelRect.left = mBatteryBgRect.left + 4;//电量和其背景图片宽度差8px
        mBatteryLevelRect.top = mBatteryBgRect.top + 3;
        mBatteryLevelRect.bottom = mBatteryBgRect.bottom - 3;
        mBatteryLevelRect.right = mBatteryBgRect.left - 4;

        //设置电池充电动画的绘制矩形
        mBatteryChargingAnimRect.left = mBatteryBgRect.left;
        mBatteryChargingAnimRect.top = mBatteryBgRect.top;
        mBatteryChargingAnimRect.bottom = mBatteryBgRect.bottom;
        mBatteryChargingAnimRect.right = mBatteryBgRect.left;

        //限制线
        mLimitLineRect.top = mBatteryBgRect.top;
        mLimitLineRect.bottom = mBatteryBgRect.bottom;

        //充电动画rect宽度
        mBatteryChargingAnimRectWidth = mBatteryChargingAnimBitmap.getWidth() * (mBatteryChargingAnimRect.bottom - mBatteryChargingAnimRect.top) / mBatteryChargingAnimBitmap.getHeight();

        //首次设置电量动画相关参数
        float offSet = (float) mFullPowerWidth / 200;
        mBatteryColumnWidth = (mFullPowerWidth + offSet) / 50;//宽度需要加上一个偏移值，不然不准确
        mColumnGrow = mFullPowerHeight / 15;
        Log.i(TAG, "mColumnGrow:" + mColumnGrow);
        Log.i(TAG, "offSet:" + offSet);
        Log.i(TAG, "mBatteryColumnWidth:" + mBatteryColumnWidth);

        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //null代表绘制该图片的全部，会缩放到后面的rect中
        //绘制电池边框
        canvas.drawBitmap(mBatteryFrameBitmap, null, mBatteryFrameRect, mBitmapPaint);
        //绘制电池槽（底部背景）
        canvas.drawBitmap(mBatteryBgBitmap, null, mBatteryBgRect, mBitmapPaint);
        //绘制电池电量
        if (mBatteryLevel > 0) {
            canvas.save();
            if (mShouldClipBatteryLevel) {
                canvas.clipPath(mFirstSetBatteryLevelClipPath);
            }
            canvas.drawBitmap(mBatteryLevelBitmap, mBatteryLevelSrcRect, mBatteryLevelRect, mBitmapPaint);
            canvas.restore();
        }
        //绘制限制线
        if (mLimitLinePercent > 0) {
            canvas.drawBitmap(mBatteryLimitLineBitmap, null, mLimitLineRect, mBitmapPaint);
        }
        //绘制放电闪光
        if (mIsDischarging) {
            canvas.drawBitmap(mBatteryGlintBitmap, null, mBatteryGlintRect, mBitmapPaint);
        }

        //正在充电，需要绘制动效的图片
        if (mIsCharging) {
            //充电图片根据右边界位置进行拉伸
            canvas.drawBitmap(mBatteryChargingAnimBitmap, mBatteryChargingAnimSrcRect, mBatteryChargingAnimRect, mBitmapPaint);
        }
    }

    //<editor-fold desc="属性动画相关">

    /**
     * 设置电量图裁剪的右边界
     *
     * @param clipRightX 右边界
     */
    public void setBatteryLevelClipRightX(float clipRightX) {
        //找到当前的电量条
        float thisColumnLeft = mBatteryLevelRect.left;
        if (clipRightX - mBatteryColumnWidth > mBatteryLevelRect.left) {
            float widthOffset = (clipRightX - mBatteryLevelRect.left) % mBatteryColumnWidth;
            thisColumnLeft = clipRightX - (widthOffset == 0 ? mBatteryColumnWidth : widthOffset);
        }

        RectF thisColumnRect = mBatteryColumnClipMap.get(thisColumnLeft);
        if (thisColumnRect != null) {
            thisColumnRect.right = clipRightX;
        }

        //超过了右边界就不再加了
        if (thisColumnRect == null && clipRightX <= mBatteryLevelRect.right) {
            addBatteryLevelColumnHeight(2);

            float thisColumnTop;
            float thisColumnBottom;
            thisColumnTop = (float) (mFullPowerHeight - mColumnGrow) / 2 + mBatteryLevelRect.top;
            thisColumnBottom = thisColumnTop + mColumnGrow;

            thisColumnRect = new RectF(thisColumnLeft, thisColumnTop, clipRightX, thisColumnBottom);
            mBatteryColumnClipMap.put(thisColumnLeft, thisColumnRect);
            Log.i(TAG, "111111111111111111111111111 newnewnewnewnewnewnewnewnewnew:");
        }

        RectF leftColumnRect = null;//高度不是最大的几个电量条中最左边的那个
        //此处 mBatteryColumnClipMap 中的item必定高度都不是最大的
        for (Map.Entry<Float, RectF> item : mBatteryColumnClipMap.entrySet()) {
            RectF showSegment = item.getValue();
            if (leftColumnRect == null || leftColumnRect.left > showSegment.left) {
                leftColumnRect = showSegment;
            }
        }

        mFirstSetBatteryLevelClipPath.reset();
        if (!mBatteryColumnClipMap.isEmpty()) {
            mFirstSetBatteryLevelClipPath.moveTo(mBatteryLevelRect.left, mBatteryLevelRect.top);
            if (leftColumnRect != null) {
                Log.i(TAG, "0000 mBatteryColumnClipList.size :" + mBatteryColumnClipMap.size());
                mFirstSetBatteryLevelClipPath.lineTo(leftColumnRect.left, mBatteryLevelRect.top);
                mFirstSetBatteryLevelClipPath.lineTo(leftColumnRect.left, mBatteryLevelRect.bottom);
                mFirstSetBatteryLevelClipPath.lineTo(mBatteryLevelRect.left, mBatteryLevelRect.bottom);
            }
            for (Map.Entry<Float, RectF> item : mBatteryColumnClipMap.entrySet()) {
                mFirstSetBatteryLevelClipPath.addRect(item.getValue(), Path.Direction.CCW);
            }
        }
        mFirstSetBatteryLevelClipPath.close();
        //此处取消动画
        if (clipRightX > mBatteryLevelRect.right && mBatteryColumnClipMap.isEmpty()) {
            destroyBatteryLevelFirstSetAnim();
        }

        invalidate();
    }


    /**
     * 设置电量条增长速率
     *
     * @param growth 用于控制增长速率
     */
    public void setBatteryLevelClipColumnGrowth(int growth) {
        if (mLastGrowth == growth) {
            return;
        }
        Log.i(TAG, "setBatteryLevelClipColumnGrowth:" + growth);
        mLastGrowth = growth;

        addBatteryLevelColumnHeight(mColumnGrow);
//        invalidate();
    }

    /**
     * 增长电量条高度
     */
    private void addBatteryLevelColumnHeight(int growValue) {
        List<Float> needRemoveList = new ArrayList<>();
        for (Map.Entry<Float, RectF> item : mBatteryColumnClipMap.entrySet()) {
            RectF showSegment = item.getValue();
            showSegment.top = showSegment.top - growValue;
            showSegment.bottom = showSegment.bottom + growValue;

            if (showSegment.top <= mBatteryLevelRect.top) {
                needRemoveList.add(item.getKey());
            }
        }
        if (!needRemoveList.isEmpty()) {
            for (Float key : needRemoveList) {
                mBatteryColumnClipMap.remove(key);
            }
        }
    }

    /**
     * 设置电量，float值的电量，用于在两个电量值之间增长的时候显示动画
     *
     * @param batteryLevelFloat 电量浮点值
     */
    public void setBatteryLevelFloat(float batteryLevelFloat) {
        mBatteryLevelRect.right = (int) (mBatteryLevelRect.left + mFullPowerWidth * batteryLevelFloat / 100);//设置电量右边界
        mBatteryLevelSrcRect.right = (int) (mBatteryLevelBitmap.getWidth() * batteryLevelFloat / 100);//设置电量裁剪图标的右边界
//        Log.i(TAG,"setBatteryLevelFloat:" + batteryLevelFloat + " ,mBatteryLevelRect.right:" + mBatteryLevelRect.right + "," + mBatteryLevelSrcRect.right);
        invalidate();
    }

    /**
     * 设置充电 显示的图片的右边界，用于展示充电动效
     *
     * @param chargingRightX 充电右边界
     */
    public void setChargingRightX(float chargingRightX) {
//        Log.i(TAG,"setChargingX:" + chargingRightX + ",mBatteryBgRect.right:" + mBatteryBgRect.right);
        int rightX = (int) Math.min(chargingRightX, mBatteryBgRect.right);
        int newLeftX = (int) Math.max(chargingRightX - mBatteryChargingAnimRectWidth, 0);
        if (newLeftX > rightX) {//相同且大于0且大于等于右边值说明到达最右边了，此时不用再重绘
//            Log.i(TAG,"setChargingRightX rightX:" + rightX);
//            Log.i(TAG,"setChargingRightX newLeftX:" + newLeftX);
            return;
        }
        mBatteryChargingAnimRect.left = newLeftX;
        mBatteryChargingAnimRect.right = rightX;

        mBatteryChargingAnimSrcRect.right = mBatteryChargingAnimBitmap.getWidth() * (mBatteryChargingAnimRect.right - mBatteryChargingAnimRect.left) / mBatteryChargingAnimRectWidth;

        invalidate();
    }

    //</editor-fold>
}
