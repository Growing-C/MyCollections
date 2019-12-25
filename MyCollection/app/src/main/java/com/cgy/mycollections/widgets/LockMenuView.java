package com.cgy.mycollections.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.cgy.mycollections.R;
import appframe.utils.L;


/**
 * 锁的布局
 */
public class LockMenuView extends RelativeLayout implements View.OnClickListener {
    private final String TAG = "LockMenuView";
    public static final int LOCK_MODE_WIFI = 4;//选中wifi
    public static final int LOCK_MODE_BLE_FAST = 5;//选中蓝牙极速
    public static final int LOCK_MODE_BLE_NORMAL = 6;//选中蓝牙普通

    public static final int LOCK_TYPE_BLE_WIFI = 1;//蓝牙wifi都支持

    private Context mContext;

    private View mMenuContentV;
    private IconFontTextView mCloseMenuBtn;
    private IconFontTextView mBleFastV;
    private IconFontTextView mBleNormalV;
    private IconFontTextView mWifiV;
    private IconFontTextView mBleV;

    private int mMenuType = 1;//默认蓝牙wifi都支持
    private int mSelectedMenu = 4;//默认选中wifi
    private OnMenuSelectedListener mListener;
    private boolean mMenuIsShown = false;//菜单是否展开

    public LockMenuView(Context context) {
        this(context, null);
    }

    public LockMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }


    private void init() {
        View contentV = LayoutInflater.from(mContext).inflate(R.layout.view_lock_menu, this, true);
        mMenuContentV = contentV.findViewById(R.id.ll_menu_content);
        mBleFastV = contentV.findViewById(R.id.fast);
        mBleNormalV = contentV.findViewById(R.id.normal);
        mBleV = contentV.findViewById(R.id.ble);
        mWifiV = contentV.findViewById(R.id.wifi);
        mCloseMenuBtn = contentV.findViewById(R.id.close_menu);
        mCloseMenuBtn.setOnClickListener(this);
        mWifiV.setOnClickListener(this);
        mBleNormalV.setOnClickListener(this);
        mBleFastV.setOnClickListener(this);
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener listener) {
        this.mListener = listener;
    }

    /**
     * 获取菜单是否展开
     *
     * @return
     */
    public boolean isMenuShown() {
        return mMenuIsShown;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_menu) {
            if (mMenuContentV.getVisibility() == View.VISIBLE) {
                hide();
            } else {
                show();
            }
        } else if (v.getId() == R.id.fast) {//蓝牙 极速
            selectMenu(LOCK_MODE_BLE_FAST, true);
        } else if (v.getId() == R.id.normal) {//蓝牙 普通
            selectMenu(LOCK_MODE_BLE_NORMAL, true);
        } else if (v.getId() == R.id.wifi) {//wifi
            selectMenu(LOCK_MODE_WIFI, true);
        }
    }

    /**
     * 设置菜单包含哪些内容
     *
     * @param contentType
     */
    public void setMenuContent(int contentType) {
        this.mMenuType = contentType;
        mWifiV.setBackgroundResource(R.drawable.selector_rec_btn);
      if (contentType == LOCK_TYPE_BLE_WIFI) {//wifi ble都有
            mBleV.setVisibility(View.VISIBLE);
            mBleFastV.setVisibility(View.VISIBLE);
            mBleNormalV.setVisibility(View.VISIBLE);
            mWifiV.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 选中某个菜单
     *
     * @param menu
     * @param shouldNotify 是否要通知
     */
    public void selectMenu(int menu, boolean shouldNotify) {
        if (mBleFastV == null)
            return;
        mBleNormalV.setText("1");
        mBleFastV.setText("2");

        if (menu == LOCK_MODE_WIFI) {//选中wifi模式

            mSelectedMenu = menu;
            mWifiV.setSelected(true);
            mBleV.setSelected(false);
            mBleFastV.setSelected(false);
            mBleNormalV.setSelected(false);
        } else {


            mSelectedMenu = menu;
            mWifiV.setSelected(false);
            mBleV.setSelected(true);
            if (menu == LOCK_MODE_BLE_FAST) {//选中蓝牙极速模式
                String fastString = "1√";
                mBleFastV.setText(fastString);
                mBleFastV.setSelected(true);
                mBleNormalV.setSelected(false);
            } else if (menu == LOCK_MODE_BLE_NORMAL) {//选中蓝牙普通模式
                String normalString = "1√";
                mBleNormalV.setText(normalString);
                mBleFastV.setSelected(false);
                mBleNormalV.setSelected(true);
            }
        }
        if (shouldNotify && mListener != null)
            mListener.onMenuSelected(menu);

        hide();
    }


    public void show() {

        mCloseMenuBtn.setText("X");
        mCloseMenuBtn.setBackgroundResource(R.drawable.shape_rec_left_circle);
        mMenuContentV.setVisibility(View.VISIBLE);
        PropertyValuesHolder h5 = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder h7 = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMenuContentV, h5, h7);
        mMenuContentV.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mMenuContentV.setPivotX(0);//中心点为左边

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator);
        set.setDuration(300);
        set.setInterpolator(new LinearInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                L.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mMenuContentV.setLayerType(View.LAYER_TYPE_NONE, null);
                L.e(TAG, "onAnimationEnd  ");
                mMenuIsShown = true;

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void hide() {
        PropertyValuesHolder h5 = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder h7 = PropertyValuesHolder.ofFloat("scaleX", 1, 0.2f);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(mMenuContentV, h5, h7);
        mMenuContentV.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator2);
        set.setDuration(300);
        set.setInterpolator(new LinearInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mMenuContentV.setLayerType(View.LAYER_TYPE_NONE, null);
                mMenuContentV.setVisibility(View.GONE);
                mCloseMenuBtn.setBackgroundResource(R.drawable.shape_circle_add_n);
                mCloseMenuBtn.setText("X");
                mMenuIsShown = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 菜单选中
     */
    public interface OnMenuSelectedListener {
        void onMenuSelected(int lockType);
    }

}