package com.growingc.mediaoperator.picturerender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.growingc.mediaoperator.utils.BitmapUtils;

import java.nio.ByteBuffer;

import appframe.utils.L;

/**
 * author: chengy
 * created on: 2021-1-26 10:14
 * description: 将bitmap图片和 byte[]以及ByteBuffer互相转换
 */
public class ByteBufferUtils {


    public static Bitmap testBitmap2Buffer2Bitmap(@NonNull Bitmap source) {
        Paint mPaint = new Paint();

        long time = System.currentTimeMillis();
        ByteBuffer sourceBuffer = BitmapUtils.convertBitmap2Buffer(source);

        byte[] transData = new byte[sourceBuffer.remaining()];
        sourceBuffer.get(transData);
        sourceBuffer = ByteBuffer.wrap(transData);
        L.i("testa", "  sourceBuffer limit:" + sourceBuffer.limit());
        L.i("testa", "  sourceBuffer position:" + sourceBuffer.position());
        L.i("testa", "  sourceBuffer remaining:" + sourceBuffer.remaining());
        L.i("testa", "  sourceBuffer   getConfig():" + source.getConfig());
        //第一步将buffer转成目标大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
//        Bitmap bitmap = Bitmap.createBitmap(640, 660, Bitmap.Config.RGB_565);
        L.i("testa", "init bitmap bytecount:" + bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(sourceBuffer);
        byte[] data;
        if (1 > 0) {
            //第三步压缩bitmap
            data = BitmapUtils.bitmap2JpgData(bitmap);
//            data = bitmap2JpgData(source);
//            data = new byte[sourceBuffer.remaining()];
//            sourceBuffer.get(data);
//            ByteBuffer.wrap(data)
//            data = sourceBuffer.array();
            L.i("testa", "  data.length:" + data.length);
        } else {
            //第二步转换bitmap大小
            PoolBitmap poolBitmap = PoolBitmap.obtainBitmap(626, 660);
            Canvas c = new Canvas(poolBitmap.mBitmap);
            c.drawBitmap(bitmap, 0, 0, mPaint);
            c.setBitmap(null);

            source.recycle();

            //第三步压缩bitmap
            data = BitmapUtils.bitmap2JpgData(poolBitmap.mBitmap);
        }

        //第四步 压缩后的byte[]转bitmap
        Bitmap imageGuidance = BitmapFactory.decodeByteArray(data, 0, data.length);
        L.d("testa", "all cost time:" + (System.currentTimeMillis() - time));
        return imageGuidance;
    }

}
