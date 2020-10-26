package com.cgy.mycollections.functions.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicInteger;

import appframe.utils.L;

/**
 * Description :
 * Author :cgy
 * Date :2020/10/26
 */
public class GameSurface extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    public static final int ACTION_RUDDER = 1, ACTION_ATTACK = 2; // 1：摇杆事件 2：按钮事件（未实现）
    public static final int NO_ANGLE = -10086;//没有角度，代表同一个点

    private SurfaceHolder mHolder;
    private boolean isStop = false;
    private Thread mThread;
    private Paint mRockerPaint;
    private Paint mTextPaint;
    private Point mMovingViewCenterPoint; //可移动的view中心点
    private Point mRockerPosition; //摇杆位置
    private Point mRockerCenterPoint;//摇杆起始位置
    private Point mFunctionCenterPoint;//功能按键中心点
    private int mFunctionRadius = 100;//功能按键半径
    private float mMovingAngle = NO_ANGLE;//移动的角度，NO_ANGLE代表不移动
    private int mMovingLen = 10;//移动速度
    private int mRockerMargin = 100;//摇杆和左下的边距
    private int mRockerCenterPointRadius = 20;//摇杆中间点半径
    private volatile AtomicInteger mMovingRadius = new AtomicInteger(20);//移动点的半径
    private int mRockerPlateRadius = 130;//摇杆背景，活动范围半径
    private RudderListener listener = null; //事件回调接口

    private long mSecondDownTime = 0;

    public GameSurface(Context context) {
        super(context);
    }

    public GameSurface(Context context, AttributeSet as) {
        super(context, as);
        this.setKeepScreenOn(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new Thread(this);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);//抗锯齿
        mTextPaint.setTextSize(60);

        mRockerPaint = new Paint();
        mRockerPaint.setColor(Color.GREEN);
        mRockerPaint.setAntiAlias(true);//抗锯齿

        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSPARENT);//设置背景透明
    }

    //设置回调接口
    public void setRudderListener(RudderListener rockerListener) {
        listener = rockerListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        if (measuredHeight > 0 && mRockerCenterPoint == null) {
            L.e("measuredHeight:" + measuredHeight);
            L.e("measuredWidth:" + measuredWidth);
            //可移动的view放在中间
            mMovingViewCenterPoint = new Point(measuredWidth / 2, measuredHeight / 2);

            int rockerCenterX = mRockerMargin + mRockerPlateRadius;
            int rockerCenterY = measuredHeight - mRockerMargin - mRockerPlateRadius;

            L.e("rockerCenterX:" + rockerCenterX);
            L.e("rockerCenterY:" + rockerCenterY);
            mFunctionCenterPoint = new Point(measuredWidth - rockerCenterX - mRockerMargin, rockerCenterY);
            mRockerCenterPoint = new Point(rockerCenterX, rockerCenterY);
            mRockerPosition = new Point(mRockerCenterPoint);
        }
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (!isStop) {
            try {
                canvas = mHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//清除屏幕
                mRockerPaint.setColor(Color.CYAN);
                //绘制摇杆背景范围
                canvas.drawCircle(mRockerCenterPoint.x, mRockerCenterPoint.y, mRockerPlateRadius, mRockerPaint);
                canvas.drawCircle(mFunctionCenterPoint.x, mFunctionCenterPoint.y, mFunctionRadius, mRockerPaint);
                canvas.drawText("技能", mFunctionCenterPoint.x - mFunctionRadius + 35,
                        mFunctionCenterPoint.y + mFunctionRadius - 70, mTextPaint);
                mRockerPaint.setColor(Color.RED);
                //绘制摇杆中心点，会在背景内部移动
                canvas.drawCircle(mRockerPosition.x, mRockerPosition.y, mRockerCenterPointRadius, mRockerPaint);
                //绘制摇杆操作的可以移动的view
                canvas.drawCircle(mMovingViewCenterPoint.x, mMovingViewCenterPoint.y, mMovingRadius.get(), mRockerPaint);
                if (mMovingAngle != NO_ANGLE) {
                    //有移动view的移动角度则增加移动逻辑
                    mMovingViewCenterPoint = MathUtils.getBorderPoint(mMovingViewCenterPoint, mMovingAngle
                            , mMovingLen);
                    L.e("mMovingViewCenterPoint.x:" + mMovingViewCenterPoint.x);
                    L.e("mMovingViewCenterPoint.y:" + mMovingViewCenterPoint.y);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        L.e("surfaceCreated");
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isStop = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_POINTER_DOWN) {
            L.e("onTouchEvent", "ACTION_POINTER_DOWN x:" + event.getX() + " y" + event.getY());
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            L.e("onTouchEvent", "ACTION_POINTER_UP x:" + event.getX() + " y" + event.getY());
        }
        //点击了技能，有两个触摸的话需要
        if (isTouchFunction(event.getX(), event.getY()) || (event.getPointerCount() > 1
                && isTouchFunction(event.getX(1), event.getY(1)))) {
            if (action == MotionEvent.ACTION_POINTER_DOWN
                    || action == MotionEvent.ACTION_DOWN) {
                L.e("onTouchEvent", "ACTION_POINTER function down x:" + event.getX() + " y" + event.getY());

                mSecondDownTime = System.currentTimeMillis();
            } else if (action == MotionEvent.ACTION_POINTER_UP ||
                    action == MotionEvent.ACTION_UP) {
                L.e("onTouchEvent", "ACTION_POINTER function up x:" + event.getX() + " y" + event.getY());

                mMovingRadius.set(100);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMovingRadius.set(20);
                    }
                }, 1500);
            }

            return true;
        }
        int len = MathUtils.getLength(mRockerCenterPoint.x, mRockerCenterPoint.y, event.getX(), event.getY());
        if (action == MotionEvent.ACTION_DOWN) {

            L.e("onTouchEvent", "ACTION_DOWN x:" + event.getX() + " y" + event.getY());
            //如果屏幕接触点不在摇杆挥动范围内,则不处理
            if (len > mRockerPlateRadius) {
                return true;
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            L.e("onTouchEvent", "ACTION_MOVE x:" + event.getX() + " y" + event.getY());

            Point touchPoint = new Point((int) event.getX(), (int) event.getY());
            mMovingAngle = MathUtils.getRadian(mRockerCenterPoint, touchPoint);
            if (len <= mRockerPlateRadius) {
                //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
                mRockerPosition.set((int) event.getX(), (int) event.getY());
            } else {
                //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
                mRockerPosition = MathUtils.getBorderPoint(mRockerCenterPoint, mMovingAngle
                        , mRockerPlateRadius);
            }
            if (listener != null) {
                listener.onSteeringWheelChanged(ACTION_RUDDER,
                        GameSurface.this.getAngleCouvert(mMovingAngle));
            }
        }
        //如果手指离开屏幕，则摇杆返回初始位置
        else if (action == MotionEvent.ACTION_UP) {
            L.e("onTouchEvent", "ACTION_UP x:" + event.getX() + " y" + event.getY());

            mMovingAngle = NO_ANGLE;//重置移动角度
            mRockerPosition = new Point(mRockerCenterPoint);
        }
        return true;
    }

    //获取摇杆偏移角度 0-360°
    private int getAngleCouvert(float radian) {
        int tmp = (int) Math.round(radian / Math.PI * 180);
        if (tmp < 0) {
            return -tmp;
        } else {
            return 180 + (180 - tmp);
        }
    }

    //回调接口
    public interface RudderListener {
        void onSteeringWheelChanged(int action, int angle);
    }

    /**
     * 是否是触摸在功能按钮上
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchFunction(float x, float y) {
        int touchPoint2FunctionCenterLen = MathUtils.getLength(mFunctionCenterPoint.x, mFunctionCenterPoint.y, x, y);
        L.e("ACTION_POINTER isTouchFunction:" + touchPoint2FunctionCenterLen);
        return touchPoint2FunctionCenterLen <= mFunctionRadius;
    }
}
