package com.cgy.mycollections.functions.ui.textdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkClickListener;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkMovementMethodEx;
import com.cgy.mycollections.functions.ui.textdemo.linkify.NoUnderlineSpan;
import com.cgy.mycollections.widgets.WaveView;

import java.util.regex.Matcher;

import appframe.utils.L;
import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1.该页面展示了iconFont  点击圆形效果  和波纹动画
 * 2.svg作为background的使用。svg在5.x可以直接使用，5.0以下 如4.x直接使用可能会报错，需要适配一下，这里懒得适配
 * Google在Android 5.X中提供了两个新API来帮助支持SVG:
 * VectorDrawable
 * AnimatedVectorDrawable
 */
public class TextDemo extends AppCompatActivity {
    @BindView(R.id.wave_view)
    WaveView mWaveView;
    @BindView(R.id.tv_unlock)
    View mTv;
    @BindView(R.id.clickable_text)
    TextView mClickTextV;
    @BindView(R.id.linkify_text)
    TextView mLinkifyTextV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_demo);
        ButterKnife.bind(this);

        setUpWave();

        setTextClick("可点击的文字");

        testLinkify();
    }

    @OnClick({R.id.tv_unlock, R.id.edit_demo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_demo:
                startActivity(new Intent(this, EditTextActivity.class));
                break;
            case R.id.tv_unlock:
                mWaveView.start();
                mWaveView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWaveView.stop();
                    }
                }, 5000);
                break;
            default:
                break;
        }

    }

    public void setUpWave() {
        mWaveView.setSpeed(700);
        mWaveView.setOneWaveDuration(5000);
        mWaveView.setMaxRadiusRate(2);
        mWaveView.setStyle(Paint.Style.STROKE);//画圆环
        mWaveView.setInitialRadius(120);//字体dp大小
        mWaveView.setColor(Color.WHITE);
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
    }

    private void setTextClick(String name) {
        if (TextUtils.isEmpty(name))
            return;

        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                new ToastCustom(TextDemo.this, "点击了文字", Toast.LENGTH_LONG).show();
            }

        }, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.px25_tx_size)), name.length() - 4, name.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                1, name.length() - 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mClickTextV.setText(spannableString);
        mClickTextV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void testLinkify() {
        try {
            //此方法只适用于部分手机，红米note8之类的不管用，而且 Linkify.WEB_URLS这个不能正确识别
//            Linkify.addLinks(mLinkifyTextV, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS);
            Linkify.addLinks(mLinkifyTextV, Patterns.PHONE, "tel:", Linkify.sPhoneNumberMatchFilter, Linkify.sPhoneNumberTransformFilter);
            Linkify.addLinks(mLinkifyTextV, Patterns.WEB_URL, "http://", Linkify.sUrlMatchFilter, new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return url + "suffix";//可以给点击时候
                }
            });
            NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
            //可点击的link会带上下划线，此处用于去除下划线
            if (mLinkifyTextV.getText() instanceof Spannable) {
                Spannable s = (Spannable) mLinkifyTextV.getText();
                ClickableSpan[] links = s.getSpans(0, s.length(), ClickableSpan.class);
                L.e("test", "ClickableSpan count:" + links.length);
                for (ClickableSpan cs :
                        links) {
                    if (cs instanceof URLSpan) {
                        //电话和手机号都是urlSpan，getUrl都会带上上面的 schema以及TransformFilter之后的内容
                        L.e("test", "ClickableSpan  is url span:" + ((URLSpan) cs).getURL());
                    }
                }
                s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
            }

            mLinkifyTextV.setLinkTextColor(Color.parseColor("#3A77B8"));
            //使用自带的会自动跳转到打电话
//            mLinkifyTextV.setMovementMethod(LinkMovementMethod.getInstance());
            mLinkifyTextV.setMovementMethod(new LinkMovementMethodEx(new LinkClickListener() {
                @Override
                public boolean onLinkClick(String mURL) {

                    L.e("test", "onLinkClick:" + mURL);
                    final String phoneNum = mURL;
                    if (mURL.startsWith("http://") || mURL.startsWith("https://") || mURL.startsWith("rtsp://")) {
                        return true;
                    }
                    if (mURL.startsWith("tel:")) {
                        mURL = mURL.replace("tel:", "");
                    }
                    return true;
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
