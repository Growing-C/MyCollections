package com.cgy.mycollections.functions.ui.dialogdemo.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;


import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.InputPasswordView;

import appframe.utils.DisplayHelperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RB-cgy on 2018/5/9.
 */
public class PayPasswordDialog extends Dialog {
    @BindView(R.id.password_input_view)
    InputPasswordView mPasswordView;
    OnPasswordInputListener mListener;

    public PayPasswordDialog(@NonNull Context context, OnPasswordInputListener listener) {
        this(context, R.style.CommonDialogTheme);
        this.mListener = listener;
    }

    public PayPasswordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_password);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setCanceledOnTouchOutside(false);

//        int height = getContext().getResources().getDimensionPixelOffset(R.dimen.px107);
        int width = (DisplayHelperUtils.getScreenWidth() - getContext().getResources().getDimensionPixelOffset(R.dimen.px2_size)) / 3;

        mPasswordView.setInputType(InputType.TYPE_NULL);//禁止弹出软键盘
//        System.out.println("height:" + height);
        System.out.println("width:" + width);
        setUpInputView(R.id.btn_0, width);
        setUpInputView(R.id.btn_1, width);
        setUpInputView(R.id.btn_2, width);
        setUpInputView(R.id.btn_3, width);
        setUpInputView(R.id.btn_4, width);
        setUpInputView(R.id.btn_5, width);
        setUpInputView(R.id.btn_6, width);
        setUpInputView(R.id.btn_7, width);
        setUpInputView(R.id.btn_8, width);
        setUpInputView(R.id.btn_9, width);
        setUpInputView(R.id.empty, width);
        setUpInputView(R.id.btn_delete, width);
    }

    /**
     * 由于layout_columnWeight在api21以上才有用，所以必须自己设置宽高
     *
     * @param vId
     * @param w
     */
    private void setUpInputView(int vId, int w) {
        View view = findViewById(vId);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.width = w;
//        params.height = h;//由于已经手动设置了高度，就不多此一举了
        view.setLayoutParams(params);
    }

    @OnClick({R.id.confirm_btn, R.id.close_btn, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_delete})
    public void onClick(View v) {
        Editable password = mPasswordView.getText();
        String append = "";
        switch (v.getId()) {
            case R.id.close_btn:
                dismiss();
                return;
            case R.id.confirm_btn:
                if (password.length() < 6) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (mListener != null) {
                    mListener.onPasswordInput(password.toString());
                }
                return;
            case R.id.btn_delete://删除
                append = "-1";
                break;
            default:
                if (v.getTag() != null) {
                    append = (String) v.getTag();

                }
                break;
        }

        if (!TextUtils.isEmpty(append)) {
            int selection = mPasswordView.getSelectionStart();//光标的位置
            if ("-1".equals(append)) {
                if (selection != 0) {
                    password.delete(selection - 1, selection);
                }
            } else {
                password.insert(selection, append);
            }
        }
//        System.out.println("text:" + password.toString());
    }

    public void clearPassword() {
        mPasswordView.setText("");
    }

    @Override
    public void show() {
        super.show();
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);//dialog 默认的样式@android:style/Theme.AppCompat.Light.Dialog 对应的style 有pading属性，所以就能够水平占满了
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
    }
}
