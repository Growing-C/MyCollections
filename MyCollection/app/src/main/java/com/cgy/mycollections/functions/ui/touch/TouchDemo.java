package com.cgy.mycollections.functions.ui.touch;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.SystemUtil;


import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 触摸相关demo
 */
public class TouchDemo extends AppCompatActivity {
    private String TAG = TouchDemo.class.getSimpleName();
    @BindView(R.id.touch_child)
    TouchChild mChildV;
    @BindView(R.id.touch_ll)
    TouchLinearLayout mTouchParent;
    @BindView(R.id.log)
    TextView mLogV;

    boolean shouldInterceptDispatch = false;//是否阻塞dispatch方法
    boolean shouldCostTouch = false;//是否消费touch方法

    TouchListener mTouchListener = new TouchListener() {
        @Override
        public void onInvokeDispatchTouchEvent(MotionEvent event) {
            String msg = "Demo dispatchTouchEvent:" + TouchUtils.getTouchActionName(event.getAction());
            L.e(TAG, msg);
            appendLog(msg, R.color.colorPrimary);
        }

        @Override
        public void onDispatchTouchEventResult(MotionEvent event, boolean result) {
            String msg = "Demo dispatchTouchEvent:"
                    + TouchUtils.getTouchActionName(event.getAction()) + " ---dispatchResult:" + result;
            L.e(TAG, msg);
            appendLog(msg, R.color.colorPrimary);
        }

        @Override
        public void onInvokeTouchEvent(MotionEvent event) {
            String msg = "Demo onInvokeTouchEvent:" + TouchUtils.getTouchActionName(event.getAction());
            L.e(TAG, msg);
            appendLog(msg, R.color.colorPrimary);
        }

        @Override
        public void onTouchEventResult(MotionEvent event, boolean result) {
            String msg = "Demo onTouchEventResult:"
                    + TouchUtils.getTouchActionName(event.getAction()) + " ---dispatchResult:" + result;
            L.e(TAG, msg);
            appendLog(msg, R.color.colorPrimary);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_demo);
        ButterKnife.bind(this);

        mChildV.setTouchListener(new TouchListener() {
            @Override
            public void onInvokeDispatchTouchEvent(MotionEvent event) {
                String msg = "Child dispatchTouchEvent:"
                        + TouchUtils.getTouchActionName(event.getAction());
                L.e("Child", msg);
                appendLog(msg, R.color.colorAccent);
            }

            @Override
            public void onDispatchTouchEventResult(MotionEvent event, boolean result) {
                String msg = "Child onDispatchTouchEventResult:"
                        + TouchUtils.getTouchActionName(event.getAction())
                        + "  result:" + result;
                L.e("Child", msg);
                appendLog(msg, R.color.colorAccent);
            }

            @Override
            public void onInvokeTouchEvent(MotionEvent event) {
                String msg = "Child invokeTouchEvent :"
                        + TouchUtils.getTouchActionName(event.getAction());
                L.e("Child", msg);
                appendLog(msg, R.color.colorAccent);
            }

            @Override
            public void onTouchEventResult(MotionEvent event, boolean result) {
                String msg = "Child onTouchEventResult:"
                        + TouchUtils.getTouchActionName(event.getAction())
                        + "  result:" + result;
                L.e("Child", msg);
                appendLog(msg, R.color.colorAccent);
            }
        });
        mTouchParent.setTouchListener(new TouchListener() {
            @Override
            public void onInvokeDispatchTouchEvent(MotionEvent event) {
                String msg = "Parent dispatchTouchEvent:"
                        + TouchUtils.getTouchActionName(event.getAction());
                L.e("Parent", msg);
                appendLog(msg, R.color.color_041133);

            }

            @Override
            public void onDispatchTouchEventResult(MotionEvent event, boolean result) {
                String msg = "Parent onDispatchTouchEventResult:"
                        + TouchUtils.getTouchActionName(event.getAction())
                        + "  result:" + result;
                L.e("Parent", msg);
                appendLog(msg, R.color.color_041133);
            }

            @Override
            public void onInvokeTouchEvent(MotionEvent event) {
                String msg = "Parent invokeTouchEvent:"
                        + TouchUtils.getTouchActionName(event.getAction());
                L.e("Parent", msg);
                appendLog(msg, R.color.color_041133);
            }

            @Override
            public void onTouchEventResult(MotionEvent event, boolean result) {
                String msg = "Parent onTouchEventResult:"
                        + TouchUtils.getTouchActionName(event.getAction())
                        + "  result:" + result;
                L.e("TouchParent", msg);
                appendLog(msg, R.color.color_041133);
            }

            @Override
            public void onInterceptTouchEventResult(MotionEvent event, boolean result) {
                String msg = "Parent onInterceptTouchEventResult:"
                        + TouchUtils.getTouchActionName(event.getAction())
                        + "  result:" + result;
                L.e("TouchParent", msg);
                appendLog(msg, R.color.color_041133);
            }

            @Override
            public void onInvokeInterceptTouchEvent(MotionEvent event) {
                String msg = "Parent onInvokeInterceptTouchEvent:"
                        + TouchUtils.getTouchActionName(event.getAction());
                L.e("TouchParent", msg);
                appendLog(msg, R.color.color_041133);
            }
        });
    }

    public void appendLog(String msg, int colorIds) {
        SpannableStringBuilder log = new SpannableStringBuilder(mLogV.getText());
        SpannableString spannableString = new SpannableString(msg);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, colorIds)),
                0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        log.append("\n").append(spannableString);
        mLogV.setText(log);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //第一个view底部Y需要加上statusBar高度，才能ev.getY()获得的数值相同。
        float touchParentHeight = SystemUtil.getStatuBarHeight(this)
                + mTouchParent.getY() + mTouchParent.getHeight();//75+0+550
        L.e("teaa", "dispatchTouchEvent touch area bottom y:" + touchParentHeight);
        L.e("teaa", "dispatchTouchEvent y:" + ev.getY());
        if (ev.getY() > touchParentHeight)//如果超过demo触摸测试位置就不管了
            return super.dispatchTouchEvent(ev);

        mTouchListener.onInvokeDispatchTouchEvent(ev);
        if (shouldInterceptDispatch)
            return true;
        boolean dispatchResult = super.dispatchTouchEvent(ev);
        mTouchListener.onDispatchTouchEventResult(ev, dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchParentHeight = SystemUtil.getStatuBarHeight(this)
                + mTouchParent.getY() + mTouchParent.getHeight();
        L.e("teaa", "onTouchEvent touch area bottom y:" + touchParentHeight);
        L.e("teaa", "onTouchEvent y:" + event.getY());
        if (event.getY() > touchParentHeight)//如果超过demo触摸测试位置就不管了
            return super.onTouchEvent(event);

        mTouchListener.onInvokeTouchEvent(event);
        if (shouldCostTouch)
            return true;
        boolean touchResult = super.onTouchEvent(event);
        L.e("TouchDemo", "TouchDemo onTouchEvent touchResult:" + touchResult);
        mTouchListener.onTouchEventResult(event, touchResult);
        return touchResult;
    }

    @OnClick({R.id.touch_child, R.id.touch_ll, R.id.clean})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.touch_child:
                L.e("TouchDemo", "Child onClick:");
                break;
            case R.id.touch_ll:
                L.e("TouchDemo", "Parent onClick:");
                break;
            case R.id.clean:
                mLogV.setText("");
                break;
            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.activity_intercept_dispatch, R.id.activity_intercept_touch
            , R.id.group_intercept, R.id.group_intercept_dispatch, R.id.group_intercept_touch
            , R.id.child_intercept_dispatch, R.id.child_intercept_touch})
    public void onCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.activity_intercept_dispatch://activity是否拦截dispatch事件
                shouldInterceptDispatch = isChecked;
                break;
            case R.id.activity_intercept_touch://activity是否消费touch
                shouldCostTouch = isChecked;
                break;
            case R.id.group_intercept://group是否intercept touch事件
                mTouchParent.setShouldIntercept(isChecked);
                break;
            case R.id.group_intercept_dispatch://group是否intercept dispatch事件
                mTouchParent.setShouldInterceptDispatch(isChecked);
                break;
            case R.id.group_intercept_touch://group是否消费touch
                mTouchParent.setShouldCostTouch(isChecked);
                break;
            case R.id.child_intercept_dispatch://child是否intercept dispatch事件
                mChildV.setShouldInterceptDispatch(isChecked);
                break;
            case R.id.child_intercept_touch://child是否消费touch
                mChildV.setShouldCostTouch(isChecked);
                break;
            default:
                break;
        }
    }
}
