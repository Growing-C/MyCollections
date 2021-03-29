package com.growingc.mediaoperator.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import appframe.utils.L;

public class ReduceSizeTransform extends CenterCrop {
    private static final String TAG = "ReduceSizeTransform";
    private float mHeight;
    private float mWidth;

    public ReduceSizeTransform(Context context, float width, float height) {
        this.mWidth = width;
        this.mHeight = height;
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) {
            return null;
        }
        source = super.transform(pool, source, outWidth, outHeight);
        int finalWidth, finalHeight;
        float mNeedRatio = mHeight / mWidth;
        float outRatio = (float) source.getHeight() / (float) source.getWidth();
        L.e(TAG," source.getWidth()   "+ source.getWidth());
        L.e(TAG," source.getHeight()   "+ source.getHeight());
        if (mNeedRatio > outRatio) {
            finalHeight = (int) mHeight;
            finalWidth = (int) (mHeight / outRatio);
        } else {
            finalWidth = (int) mWidth;
            finalHeight = (int) (mWidth / outRatio);
        }
        L.e(TAG," finalWidth   "+ finalWidth);
        L.e(TAG," finalHeight   "+ finalHeight);

        //修正圆角
        Bitmap outBitmap = pool.get(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        if (outBitmap == null) {
            outBitmap = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        //关联画笔绘制的原图bitmap
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //计算中心位置,进行偏移
        int width = (source.getWidth() - finalWidth) / 2;
        int height = (source.getHeight() - finalHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float) (-width), (float) (-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0F, 0.0F, (float) canvas.getWidth(), (float) canvas.getHeight());
        canvas.drawRoundRect(rectF, 0, 0, paint); //先绘制圆角矩形
        return outBitmap;
    }

}