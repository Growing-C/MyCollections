package com.cgy.mycollections.functions.ui.dialogdemo;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.OnPasswordInputListener;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.PayPasswordDialog;
import com.cgy.mycollections.widgets.PopWindowGenerator;
import com.cgy.mycollections.widgets.pickerview.TimePickerView;
import com.cgy.mycollections.widgets.wheelview.WheelView;
import com.cgy.mycollections.widgets.wheelview.adapter.NumericWheelAdapter;
import com.cgy.mycollections.widgets.wheelview.adapter.WheelViewAdapter;

import java.util.Date;

import appframe.utils.TimeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 该页面展示了对话框
 * 1.密码输入对话框
 */
public class DialogDemo extends AppCompatActivity {

    @BindView(R.id.wheel_view)
    WheelView mWheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        ButterKnife.bind(this);

        WheelViewAdapter adapter = new NumericWheelAdapter(this, 1, 20);
        mWheelView.setViewAdapter(adapter);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_dialog:
                PayPasswordDialog mPasswordDialog = new PayPasswordDialog(this, new OnPasswordInputListener() {
                    @Override
                    public void onPasswordInput(String password) {
                    }
                });
                mPasswordDialog.show();
                break;
            case R.id.date_dialog:
                TimePickerView mSelectBirthV = PopWindowGenerator.createTimePickerView(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        Toast.makeText(DialogDemo.this, "选择了" + TimeUtils.getTime(date.getTime()), Toast.LENGTH_SHORT).show();
                    }
                });
                mSelectBirthV.show();
                break;
            default:
                break;
        }
    }

}
