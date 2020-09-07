package com.cgy.mycollections.functions.ui.dialogdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.OnPasswordInputListener;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.PayPasswordDialog;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.TestWindowDialog;
import com.cgy.mycollections.widgets.HintView;
import com.cgy.mycollections.widgets.MProgressView;
import com.cgy.mycollections.widgets.MSpinner;
import com.cgy.mycollections.widgets.PopWindowGenerator;
import com.cgy.mycollections.widgets.pickerview.TimePickerView;
import com.cgy.mycollections.widgets.wheelview.WheelView;
import com.cgy.mycollections.widgets.wheelview.adapter.NumericWheelAdapter;
import com.cgy.mycollections.widgets.wheelview.adapter.WheelViewAdapter;

import java.util.Date;

import appframe.utils.L;
import appframe.utils.TimeUtils;
import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 该页面展示了对话框
 * 1.密码输入对话框
 */
public class DialogAndWidgetsDemo extends AppCompatActivity {

    @BindView(R.id.wheel_view)
    WheelView mWheelView;
    @BindView(R.id.m_spinner)
    MSpinner mSpinner;
    @BindView(R.id.m_progress)
    MProgressView mProgress;
    @BindView(R.id.hint_view)
    HintView mHintV;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        ButterKnife.bind(this);

        WheelViewAdapter adapter = new NumericWheelAdapter(this, 1, 20);
        mWheelView.setViewAdapter(adapter);

        mSpinner.setDownListItems(new String[]{"111", "222", "333", "444", "555", "666", "333", "666"});
        mSpinner.setOnItemClickListener(new MSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String s) {
                new ToastCustom(getApplicationContext(), s).show();
            }
        });

        mProgress.setProgress(80);
        mProgress.setProgressColor(getResources().getColor(R.color.colorPrimary));
        mProgress.setUnitText("Unit");
        mProgress.setProgressText("80");
        mProgress.setProgressTextColor(Color.GREEN);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_window_dialog:
                TestWindowDialog dialog = new TestWindowDialog(this);
                dialog.show();
                break;
            case R.id.floating_dialog:
                startActivity(new Intent(this, FloatingActivity.class));
                break;
            case R.id.password_dialog:
                PayPasswordDialog mPasswordDialog = new PayPasswordDialog(this, new OnPasswordInputListener() {
                    @Override
                    public void onPasswordInput(String password) {
                        L.e("test", "password:" + password);
                    }
                });
                mPasswordDialog.show();
                break;
            case R.id.date_dialog:
                TimePickerView mSelectBirthV = PopWindowGenerator.createTimePickerView(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        Toast.makeText(DialogAndWidgetsDemo.this, "选择了" + TimeUtils.getTime(date.getTime()), Toast.LENGTH_SHORT).show();
                    }
                });
                mSelectBirthV.show();
                break;
            case R.id.hint_view:
                mHintV.setCircleColor(Color.BLUE);
                mHintV.setIsInProgress(true);
                break;
            default:
                break;
        }
    }

}
