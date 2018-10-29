package com.cgy.mycollections.functions.sqlite;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.functions.sqlite.db.UserAccount;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.widgets.WaveView;

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
public class DataBaseDemo extends AppCompatActivity {
    @BindView(R.id.info_tx)
    TextView mInfoTx;
    String mCurrentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_demo);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.add_user, R.id.add_user_key, R.id.query_user, R.id.delete_all_user})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_user:
                mCurrentAccount = UUID.randomUUID().toString();
                UserAccount account = new UserAccount();
                account.setUid(mCurrentAccount);
                account.setPhone("15051111111");
                account.setAct("act1");
                account.setName("name1");
                account.setSex("男");
                account.setEmail("123@123.com");
                DBOperator.getInstance().addUserAccount(account).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Toast.makeText(DataBaseDemo.this, "添加账户成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.add_user_key:
                if (TextUtils.isEmpty(mCurrentAccount)) {
                    Toast.makeText(this, "请先创建账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBOperator.getInstance().getUserAccountInfo(mCurrentAccount).flatMap(new Function<UserAccount, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(UserAccount userAccount) throws Exception {
                        if (userAccount == null) {
                            L.e("add_user_key error --->user null");
                        }
                        userAccount.setPublicKey("public key 1");
                        userAccount.setPrivateKey("private key 1");
                        return DBOperator.getInstance().addUserKey(userAccount);
                    }
                }).subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(DataBaseDemo.this, "添加key成功！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DataBaseDemo.this, "add_user_key error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

                break;
            case R.id.delete_all_user:
                DBOperator.getInstance().deleteAllUserAccount().subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Toast.makeText(DataBaseDemo.this, "删除所有用户成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.query_user:
                if (TextUtils.isEmpty(mCurrentAccount)) {
                    Toast.makeText(this, "请先创建账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBOperator.getInstance().getUserAccountInfo(mCurrentAccount).subscribe(new DisposableObserver<UserAccount>() {
                    @Override
                    public void onNext(UserAccount userAccount) {
                        if (userAccount != null) {
                            mInfoTx.setText(userAccount.toString());
                        } else {
                            mInfoTx.setText("未找到当前用户数据");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mInfoTx.setText("query_user error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            default:
                break;
        }
    }

}
