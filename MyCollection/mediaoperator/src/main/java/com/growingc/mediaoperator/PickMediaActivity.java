package com.growingc.mediaoperator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.growingc.mediaoperator.picturerender.ByteBufferUtils;
import com.growingc.mediaoperator.picturerender.ImageShaderHelper;

/**
 * 选择媒体文件
 * 包含图片，视频等
 */
public class PickMediaActivity extends AppCompatActivity {
    ImageView mPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_media);

        LoaderManager.getInstance(this);

        mPic = findViewById(R.id.pic);

        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        ImageShaderHelper helper = new ImageShaderHelper();
        helper.init(this);
        mPic.setImageBitmap(helper.createNaviShaderBitmap(map));

        //使用上面的图片的宽高来进行测试绘图对比 两张图绘制效果
        ImageView testPic = findViewById(R.id.test_pic);
        testPic.setImageBitmap(helper.testDraw(map));

//        map = ByteBufferUtils.testBitmap2Buffer2Bitmap(map);
//        ByteBufferUtils.bitmap2JpgData(map);
//        ByteBufferUtils.convertBitmap2Buffer(map);
    }
}