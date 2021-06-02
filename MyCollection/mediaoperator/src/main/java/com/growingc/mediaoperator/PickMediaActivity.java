package com.growingc.mediaoperator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;

import com.growingc.mediaoperator.picturerender.ImageShaderHelper;

import appframe.utils.L;

/**
 * 选择媒体文件
 * 包含图片，视频等
 */
public class PickMediaActivity extends AppCompatActivity {
    ImageView mPic;

    TextView mLayerTestTx;
    LayerDrawable mCameraDrawableLayer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_media);

        LoaderManager.getInstance(this);

        mPic = findViewById(R.id.pic);
        mLayerTestTx = findViewById(R.id.test_layer);

        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        ImageShaderHelper helper = new ImageShaderHelper();
        helper.init(this);
        mPic.setImageBitmap(helper.createShaderBitmap(map));

        //使用上面的图片的宽高来进行测试绘图对比 两张图绘制效果
        ImageView testPic = findViewById(R.id.test_pic);
        testPic.setImageBitmap(helper.testDraw(map));

//        map = ByteBufferUtils.testBitmap2Buffer2Bitmap(map);
//        ByteBufferUtils.bitmap2JpgData(map);
//        ByteBufferUtils.convertBitmap2Buffer(map);

        testLayer();
    }

    /**
     * 多图层测试
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void testLayer() {
        Rect padding = new Rect();
        mCameraDrawableLayer = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.level_bg_vector_ic_mid_camera);
        L.i("aacccdd layer height:" + mCameraDrawableLayer.getIntrinsicHeight() + "  width:" + mCameraDrawableLayer.getIntrinsicWidth());
//        layer height:198  width:378
        Drawable cameraDrawable = ContextCompat.getDrawable(this, R.drawable.vector_ic_mid_monitor);
//        mCameraDrawableLayer.setDrawableByLayerId(R.id.camera_icon, cameraDrawable);
//        mCameraDrawableLayer.setDrawableByLayerId(R.id.camera_icon, restrictDrawable);

        mCameraDrawableLayer.setPaddingMode(LayerDrawable.PADDING_MODE_STACK);
        //没有setImageDrawable 时 getBounds 都是0
        //1.set之后 setDrawableByLayerId = cameraDrawable 的 getBounds Rect(112, 22 - 266, 176)
        //2.set之后 setDrawableByLayerId = restrictDrawable 的 getBounds Rect(90, 0 - 288, 198)
        //3.set之后 不调用 setDrawableByLayerId 由于本身里面用的就是 cameraDrawable 所以和第一种情况一样 会偏移
        //不同，这是先设置图片 再设置文字 导致文字偏移的罪魁祸首

        mLayerTestTx.setCompoundDrawablesWithIntrinsicBounds(null, mCameraDrawableLayer, null, null);

        mLayerTestTx.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                L.i("aacccdd 1 getBounds:" + mCameraDrawableLayer.getDrawable(1).getBounds());

                mCameraDrawableLayer.setDrawableByLayerId(R.id.camera_icon, createRestrictSpeedDrawable());

                mLayerTestTx.setCompoundDrawablesWithIntrinsicBounds(null, mCameraDrawableLayer, null, null);
            }
        }, 3000);
    }

    private Drawable createRestrictSpeedDrawable() {
        int restrictSpeed = 180;
        RestrictSpeedTextDrawable restrictSpeedTextDrawable = new RestrictSpeedTextDrawable(50, Color.RED, Typeface.DEFAULT);
        restrictSpeedTextDrawable.setText(String.valueOf(restrictSpeed));
        return restrictSpeedTextDrawable;
    }
}