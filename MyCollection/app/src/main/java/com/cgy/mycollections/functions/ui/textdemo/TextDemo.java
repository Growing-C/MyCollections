package com.cgy.mycollections.functions.ui.textdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkClickListener;
import com.cgy.mycollections.functions.ui.textdemo.linkify.LinkMovementMethodEx;
import com.cgy.mycollections.functions.ui.textdemo.linkify.NoUnderlineSpan;
import com.cgy.mycollections.widgets.WaveView;

import java.text.NumberFormat;
import java.util.Locale;
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
    @BindView(R.id.format)
    TextView mFormatTextV;
    @BindView(R.id.count_down)
    Button mCountDownV;

    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_demo);
        ButterKnife.bind(this);

        setUpWave();

        String tt = String.format(Locale.getDefault(), "%s%d", "可点击的文字", 1234);
        setTextClick(tt);

        testLinkify();

        showFormatText();

        testMeasureText();
    }

    private void testMeasureText() {
        //1.measureText 测量出来的宽度有一定规律一个英文字符和中文字符的宽度不同
        //2.测量的时候\n并不会换行，实际还是算成一行来测量了，\n也会当作字符来测量
        Paint textPaint = new Paint();
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀fddfs"));//  124
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀小苹果"));//11个汉字  132
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀\n小苹果，哈"));//12+1
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀小苹果哈利"));//13
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀小苹果哈利路"));//14
        L.i("text width:" + textPaint.measureText("你是我的小苹果呀小苹果哈利路呀"));//15
        L.i("text width:" + "dfasf\nfdsaf".contains("\n"));//15
        L.i("text width:" + "dfasf\nfdsaf".split("\n")[1]);//15

        cutTextExactWidth("你是我的小苹果呀小苹果哈利路", 124);
    }

    /**
     * 截取指定宽度的文字
     *
     * @param input
     * @param width
     */
    private void cutTextExactWidth(String input, int width) {
        try {

            Paint paint = new Paint();
            int targetEnd = input.length();
            while (paint.measureText(input, 0, targetEnd) > width) {
                L.i("cutTextExactWidth:" + input.substring(0, targetEnd));
                targetEnd--;
            }
            L.i("targetWidth  :" + width);
            L.i("final  :" + paint.measureText(input.substring(0, targetEnd)));
            L.i("final 1:" + paint.measureText(input.substring(0, targetEnd + 1)));
            if (targetEnd < input.length()) {
                L.i("targetString  :" + input.substring(0, targetEnd) + "\n" + input.substring(targetEnd));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小数点格式化,会四舍五入
     */
    private void showFormatText() {
        float targetF = 3.4516f;
        StringBuilder formatStr = new StringBuilder();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        formatStr.append(targetF).append("格式化0位小数：").append(numberFormat.format(targetF));
        formatStr.append("\n");

        numberFormat.setMaximumFractionDigits(1);
        formatStr.append(targetF).append("格式化1位小数：").append(numberFormat.format(targetF));
        formatStr.append("\n");

        numberFormat.setMaximumFractionDigits(3);
        formatStr.append(targetF).append("格式化3位小数：").append(numberFormat.format(targetF));

        mFormatTextV.setText(formatStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @OnClick({R.id.tv_unlock, R.id.edit_demo, R.id.count_down})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_down:
                testCountDown();
                break;
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

    /**
     * 测试倒计时
     */
    public void testCountDown() {
        //倒计时
        if (mCountDownTimer == null) {
            L.e("testCountDown~~");
            mCountDownTimer = new CountDownTimer(5 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.e("test", "millisUntilFinished:" + millisUntilFinished);
//                    mGetVerifyBtn.setText(String.format("%s%d）", getString(R.string.has_send), millisUntilFinished / 1000));
                    //四舍五入向上取整 开启后会直接onTick  但是 是 4999,此时需要四舍五入
                    mCountDownV.setText(String.format(Locale.getDefault(), "(%s%d)", "倒计时", Math.round(millisUntilFinished / 1000f)));
                }

                @Override
                public void onFinish() {
                    mCountDownV.setEnabled(true);
                    mCountDownV.setText("验证码倒计时");
                    mCountDownTimer = null;
                }
            };
            mCountDownTimer.start();
            mCountDownV.setEnabled(false);
        }
    }

    /**
     * 点击动画
     */
    public void setUpWave() {
        mWaveView.setSpeed(700);
        mWaveView.setOneWaveDuration(5000);
        mWaveView.setMaxRadiusRate(2);
        mWaveView.setStyle(Paint.Style.STROKE);//画圆环
        mWaveView.setInitialRadius(120);//字体dp大小
        mWaveView.setColor(Color.WHITE);
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
    }

    /**
     * 给文字添加点击事件
     *
     * @param name
     */
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

    /**
     * 测试识别文字中的电话号码和网址，并添加点击事件
     */
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
