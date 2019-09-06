package com.cgy.mycollections.functions.ui.dialogdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.OnPasswordInputListener;
import com.cgy.mycollections.functions.ui.dialogdemo.dialogs.PayPasswordDialog;

/**
 * 该页面展示了对话框
 * 1.密码输入对话框
 */
public class DialogDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
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
            default:
                break;
        }
    }

}
