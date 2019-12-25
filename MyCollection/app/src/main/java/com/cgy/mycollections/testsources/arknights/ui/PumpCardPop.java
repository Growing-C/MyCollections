package com.cgy.mycollections.testsources.arknights.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.testsources.arknights.ArknightsConstant;
import appframe.utils.L;
import com.cgy.mycollections.utils.SharePreUtil;
import com.cgy.mycollections.widgets.pickerview.adapter.NumericWheelAdapter;
import com.cgy.mycollections.widgets.pickerview.lib.WheelView;
import com.cgy.mycollections.widgets.pickerview.listener.OnItemSelectedListener;

import java.lang.reflect.Method;

import appframe.utils.ToastCustom;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/19
 */
public class PumpCardPop implements View.OnClickListener {


    private WheelView mCountWheel;
    private TextView mTotalHint;
    private Spinner mAccountSpinner;

    private PopupWindow mPop;
    private Context mContext;

    private View rootView;

    public PumpCardPop(Context context) {
        mContext = context;
    }

    private View getSettingTableView() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_pump_card_count, null);
        mCountWheel = rootView.findViewById(R.id.current_loop_count);
        mTotalHint = rootView.findViewById(R.id.total_hint);
        mAccountSpinner = rootView.findViewById(R.id.account_spinner);

        rootView.findViewById(R.id.decrease_10).setOnClickListener(this);
        rootView.findViewById(R.id.decrease).setOnClickListener(this);
        rootView.findViewById(R.id.add_10).setOnClickListener(this);
        rootView.findViewById(R.id.add).setOnClickListener(this);

        mCountWheel.setAdapter(new NumericWheelAdapter(0, 100));//1-100
        mCountWheel.setLabel("次");// 添加文字
        mCountWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int totalCount = (int) mCountWheel.getAdapter().getItem(index);
                String totalCountHint = mAccountSpinner.getSelectedItem() + "  总抽卡次数：" + totalCount;
                mTotalHint.setText(totalCountHint);
                L.e("onItemSelected:" + index + "--" + totalCountHint);
                SharePreUtil.putInt(ArknightsConstant.PREF, mContext
                        , mAccountSpinner.getSelectedItem().toString(), totalCount);
            }
        });

        String[] guestAmount = new String[]{"萝莉爱吃糖", "11号"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                mContext, android.R.layout.simple_spinner_item, guestAmount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccountSpinner.setAdapter(adapter);
        mAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAccountName = parent.getAdapter().getItem(position).toString();
                L.e("选中了：" + selectedAccountName);
                initByAccount(selectedAccountName, -1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initByAccount(guestAmount[0], -1);
        return rootView;
    }

    public void initByAccount(String accountName, int currentCount) {
        if (currentCount < 0) {
            currentCount = SharePreUtil.getInt(ArknightsConstant.PREF, mContext
                    , accountName);
        }
        String totalCountHint;
        if (currentCount >= 0) {
            int targetPos = mCountWheel.getAdapter().indexOf(currentCount);
            mCountWheel.setCurrentItem(targetPos);
            totalCountHint = accountName + " 总抽卡次数：" + currentCount;
        } else {
            mCountWheel.setCurrentItem(0);// 初始化时显示的数据
            totalCountHint = accountName + " 总抽卡次数：" + 0;
        }
        mTotalHint.setText(totalCountHint);
    }

    private void changeCountByOffset(int offset) {
        int currentNumber = (int) mCountWheel.getAdapter().getItem(mCountWheel.getCurrentItem());
        L.e("changeCountByOffset currentNumber:" + currentNumber);
        int targetNumber = currentNumber + offset;
        if (targetNumber < 0) {
            new ToastCustom(mContext, "突破下限啦！", Toast.LENGTH_LONG).show();
        } else if (targetNumber > 100) {
            new ToastCustom(mContext, "突破上限啦！", Toast.LENGTH_LONG).show();
        } else {
            int targetPos = mCountWheel.getAdapter().indexOf(targetNumber);
            mCountWheel.setCurrentItem(targetPos);//手动设置不会走mCountWheel 的onItemSelected，所以需要手动保存

            SharePreUtil.putInt(ArknightsConstant.PREF, mContext
                    , mAccountSpinner.getSelectedItem().toString(), targetNumber);
        }

    }

    public void showPop(final View touchView) {
        if (mPop == null) {
            int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

            L.e("screenHeight:" + screenHeight + "    screenWidth:" + screenWidth);
            int popWidth = screenWidth;
            int popHeight = screenHeight;
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                L.e("竖屏");
                popHeight = screenHeight / 2;
            } else {
                //横屏
                L.e("横屏");
                popWidth = screenWidth / 2;
            }
            mPop = new PopupWindow(getSettingTableView(), popWidth, popHeight);

            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    touchView.setVisibility(View.VISIBLE);
                }
            });

            mPop.setBackgroundDrawable(null);//null 原来是new BitmapDrawable
            mPop.setTouchable(true);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
//            mPop.setAnimationStyle(R.style.AnimationPreview);
            mPop.setFocusable(true);

            Class c = mPop.getClass();

            //popWindow 和悬浮窗口类似，但是其有固定的layoutType，固定的Type不能显示在其他view之上，所以我们反射来重新设置
            try {
                Method method = c.getMethod("setWindowLayoutType", int.class);
                method.setAccessible(true);

                int windowType;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//6.0
                    windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    windowType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
                method.invoke(mPop, windowType);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mPop.setTouchInterceptor(new View.OnTouchListener() {
            float xDown, yDown;
            float yOffsetPermitted = 10;
            float transparentDistance = 200;//完全透明需要的度

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                float x = event.getRawX();
                float y = event.getRawY();
//                L.e("onTouch" + event.getAction() + "  x：" + x + "   y:" + y);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = x;
                        yDown = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(y - yDown) <= yOffsetPermitted) {
                            float xDistance = Math.abs(x - xDown);
                            L.e("move x轴距离:" + xDistance);
                            setRootViewAlpha((transparentDistance - xDistance) / transparentDistance);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        setRootViewAlpha(1);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        mPop.showAtLocation(touchView, Gravity.NO_GRAVITY, 0, 0);
        touchView.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置透明度（0-1）
     *
     * @param alpha
     */
    private void setRootViewAlpha(float alpha) {
        if (rootView != null && alpha >= 0 && alpha <= 1) {
            L.e("alpha:" + alpha);
            rootView.setAlpha(alpha);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.decrease_10:
                changeCountByOffset(-10);
                break;
            case R.id.decrease:
                changeCountByOffset(-1);
                break;
            case R.id.add_10:
                changeCountByOffset(10);
                break;
            case R.id.add:
                changeCountByOffset(1);
                break;
            default:
                break;
        }
    }
}
