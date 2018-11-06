package com.cgy.mycollections.functions.cache;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.anim.LottieAnimActivity;

import okhttp3.internal.cache.DiskLruCache;

/**
 * 该页面展示了安卓的缓存策略
 * TODO:尚未填坑
 * 1.LruCache 系统默认缓存 LRU是Least Recently Used的缩写，意思是最近最少使用，它是一种Cache替换算法
 * 2.DiskLruCache 是谷歌推荐的用来实现硬盘缓存的类,（貌似没有默认实现）
 * 3.ACache 轻量级的缓存框架
 */
public class CacheDemo extends AppCompatActivity {

    long mCurrentMillis;//计算缓存和读取时间
    LruCache mLruCache;
    DiskLruCache mDiskLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_demo);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lottie:
                startActivity(new Intent(CacheDemo.this, LottieAnimActivity.class));
                break;
            default:
                break;
        }
    }

}
