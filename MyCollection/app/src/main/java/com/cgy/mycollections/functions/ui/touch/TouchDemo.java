package com.cgy.mycollections.functions.ui.touch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.textdemo.EditTextActivity;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkClickListener;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkMovementMethodEx;
import com.cgy.mycollections.functions.ui.textdemo.linkify.NoUnderlineSpan;
import com.cgy.mycollections.widgets.WaveView;

import java.util.Locale;
import java.util.regex.Matcher;

import appframe.utils.L;
import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 触摸相关demo
 */
public class TouchDemo extends AppCompatActivity {
    @BindView(R.id.touch_child)
    TouchChild mChildV;
    @BindView(R.id.touch_ll)
    TouchLinearLayout mTouchParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_demo);
        ButterKnife.bind(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.e("TouchDemo", "TouchDemo dispatchTouchEvent:" + ev.getAction());
        boolean dispatchResult = super.dispatchTouchEvent(ev);
        L.e("TouchDemo", "TouchDemo dispatchTouchEvent dispatchResult:" + dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.e("TouchDemo", "TouchDemo onTouchEvent:" + event.getAction());
        boolean touchResult = super.onTouchEvent(event);
        L.e("TouchDemo", "TouchDemo onTouchEvent touchResult:" + touchResult);
        return touchResult;
    }
    //    @OnClick({R.id.touch_child, R.id.touch_ll})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.touch_child:
//                break;
//            case R.id.touch_ll:
//                break;
//            default:
//                break;
//        }
//    }
}
