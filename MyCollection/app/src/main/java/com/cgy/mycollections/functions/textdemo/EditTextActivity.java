package com.cgy.mycollections.functions.textdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.HookViewClickUtil;
import com.cgy.mycollections.utils.ViewUtils;

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
public class EditTextActivity extends AppCompatActivity {

    @BindView(R.id.limit_in_code)
    EditText mLimitInCodeV;
    @BindView(R.id.limit_in_btn)
    EditText mLimitInBtnV;

    @BindView(R.id.filter_unique_characters)
    Button mHookBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_demo);
        ButterKnife.bind(this);

        mLimitInCodeV.setKeyListener(DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM@."));
        mLimitInCodeV.setInputType(InputType.TYPE_CLASS_TEXT);//弹出英文

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                HookViewClickUtil.hookView(mHookBtn);
            }
        });
    }

    @OnClick({R.id.filter_unique_characters, R.id.open_input, R.id.close_input})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_unique_characters:
                ViewUtils.setEditTextInhibitInputSpeChat(mLimitInBtnV, "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？1234567890\\n]");
                Toast.makeText(this, "试试呗", Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_input://打开软键盘
                ViewUtils.showKeyboard(this, mLimitInBtnV);
                break;
            case R.id.close_input://关闭软键盘
                ViewUtils.closeKeyboard(this);
                break;
            default:
                break;
        }
    }

}
