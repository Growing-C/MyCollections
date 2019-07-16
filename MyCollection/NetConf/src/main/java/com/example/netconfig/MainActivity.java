package com.example.netconfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.e("MainActivity", "onCreate");
        //打开背景服务
        Intent bgService = new Intent(MainActivity.this, BackgroundService.class);
        startService(bgService);
//        Log.e("test", "有 ACCESS_FINE_LOCATION   权限？" + ( checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED));
    }
}
