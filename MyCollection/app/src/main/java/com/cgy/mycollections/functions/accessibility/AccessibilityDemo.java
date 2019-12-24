package com.cgy.mycollections.functions.accessibility;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.LogUtils;

/**
 * Created by RB-cgy on 2015/12/25.
 */
public class AccessibilityDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelope);
    }

    public void click(View v) {

        //打开系统设置中辅助功能
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        switch (v.getId()) {
            case R.id.red_envelope_btn://微信抢红包（微信似乎已经优化过 似乎没啥用了）
                LogUtils.d("red_envelope_btn");
                try {
                    Toast.makeText(AccessibilityDemo.this, "找到抢红包，然后开启服务即可", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.open_auto_input://自动输入账号密码
                LogUtils.d("open_auto_input");
                try {
                    Toast.makeText(AccessibilityDemo.this, "找到自动帐号输入，然后开启服务即可", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
