package com.growingc.mediaoperator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.os.Bundle;

/**
 * 选择媒体文件
 * 包含图片，视频等
 */
public class PickMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_media);

        LoaderManager.getInstance(this)
    }
}