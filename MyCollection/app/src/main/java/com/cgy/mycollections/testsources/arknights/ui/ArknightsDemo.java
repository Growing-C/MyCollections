package com.cgy.mycollections.testsources.arknights.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.functions.sqlite.db.UserAccount;
import appframe.utils.L;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * 该页面展示了数据库操作的demo
 */
public class ArknightsDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_demo);
        ButterKnife.bind(this);

        //恢复阴影
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//设置背景透明度
        getWindow().setAttributes(lp);
    }

    @OnClick({R.id.add_user, R.id.add_user_key, R.id.query_user, R.id.delete_all_user})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_user:

                break;
            case R.id.add_user_key:


                break;
            case R.id.delete_all_user:

                break;
            case R.id.query_user:

                break;
            default:
                break;
        }
    }

}
