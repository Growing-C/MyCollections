package com.cgy.mycollections.functions.ui.dialogdemo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.widgets.RoundLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/9/1 16:09
 * 修改日期: 2020/9/1 16:09
 * 类说明：悬浮框样式的activity
 */
public class FloatingActivity extends BaseActivity {
    @BindView(R.id.floating_content)
    RoundLinearLayout mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        ButterKnife.bind(this);

        resizeContent();
    }

    private void resizeContent() {
        //目标屏幕 375*667
        //弹框content部分长高 295*403
        //需要按比例计算
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        int width = screenWidth * 295 / 375;
        int height = width * 403 / 295;
        if (height >= screenHeight) {
            height = screenHeight * 403 / 667;
            width = height * 295 / 403;
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContent.getLayoutParams();
        params.width = width;
        params.height = height;
        mContent.setLayoutParams(params);
        mContent.setRadiusInDp(30);//设置圆角
    }

    @OnClick({R.id.floating_close})
    public void onClick(View v) {
        finish();
    }
}
