package com.growingc.mediaoperator.picturerender;

import androidx.annotation.NonNull;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        ByteBuffer sourceBuffer = convertBitmap2Buffer(source);

        byte[] transData = new byte[sourceBuffer.remaining()];
        sourceBuffer.get(transData);
        sourceBuffer= ByteBuffer.wrap(transData);
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
            data = bitmap2JpgData(bitmap);
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
            data = bitmap2JpgData(poolBitmap.mBitmap);
        }

        //第四步 压缩后的byte[]转bitmap
        Bitmap imageGuidance = BitmapFactory.decodeByteArray(data, 0, data.length);
        L.d("testa", "all cost time:" + (System.currentTimeMillis() - time));
        return imageGuidance;
    }

    public static ByteBuffer convertBitmap2Buffer(@NonNull Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        L.d("testa", "convertBitmap2Buffer bytecount:" + bitmap.getByteCount());
        L.d("testa", "convertBitmap2Buffer bufferArraySize:" + byteBuffer.array().length);
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static byte[] bitmap2JpgData(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
                out.flush();
                byte[] data = out.toByteArray();
                out.close();

                L.d("testa", "bitmap2JpgData byteArray size:" + data.length);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}
