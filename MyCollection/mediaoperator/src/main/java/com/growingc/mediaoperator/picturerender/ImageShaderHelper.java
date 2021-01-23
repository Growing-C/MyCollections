package com.growingc.mediaoperator.picturerender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.Log;

import androidx.annotation.NonNull;

import com.growingc.mediaoperator.R;


/**
 * author: chengy
 * created on: 2021-1-22 11:54
 * description:   主要用来将图片周围模糊
 */
public class ImageShaderHelper {

    Bitmap mNaviShaderSourceBitmap;//用于shader的图
    Paint mPaint;

    /**
     * 初始化相关资源
     */
    public void init(@NonNull Context context) {
        mNaviShaderSourceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_mask);
        mPaint = new Paint();
    }

    /**
     * 销毁相关资源
     */
    public void destroy() {
        mNaviShaderSourceBitmap.recycle();
        mNaviShaderSourceBitmap = null;
        mPaint = null;
    }

    /**
     * 使用shader 将地图图片周围模糊
     *
     * @param naviMapBitmap 原始的地图图片
     * @return 周边模糊处理后的地图展示图片
     */
    public Bitmap createNaviShaderBitmap(Bitmap naviMapBitmap) {
        if (naviMapBitmap == null || mNaviShaderSourceBitmap == null) {
            return null;
        }
        int bitmapWidth = mNaviShaderSourceBitmap.getWidth();
        int bitmapHeight = mNaviShaderSourceBitmap.getHeight();
        Log.i("testImage", "bitmapWidth:" + bitmapWidth);
        Log.i("testImage", "bitmapHeight:" + bitmapHeight);
        Log.i("testImage", "naviMapBitmap width:" + naviMapBitmap.getWidth());
        Log.i("testImage", "naviMapBitmap height:" + naviMapBitmap.getHeight());
//        bitmapWidth:1980
//        bitmapHeight:1980
//        naviMapBitmap width:1722
//        naviMapBitmap height:1815
        //创建bitmap 用于将地图图片shader后绘制 ARGB_8888是为了防止背景变成黑色
        Bitmap target = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        //创建BitmapShader，用以绘制椭圆形渐变图
        BitmapShader bitmapShader = new BitmapShader(mNaviShaderSourceBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //创建BitmapShader，用于将目标图片像素和源像素进行进行处理
        BitmapShader mapShader = new BitmapShader(naviMapBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //bitmapShader对应目标像素，mapShader对应源像素，像素颜色混合采用MULTIPLY模式
        ComposeShader composeShader = new ComposeShader(bitmapShader, mapShader, PorterDuff.Mode.MULTIPLY);
        //将组合的composeShader作为画笔paint绘图所使用的shader
        mPaint.setShader(composeShader);
        //用composeShader绘制矩形区域
        canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, mPaint);
        return target;
    }

    public Bitmap testDraw(Bitmap naviMapBitmap) {
        int bitmapWidth = mNaviShaderSourceBitmap.getWidth();
        int bitmapHeight = mNaviShaderSourceBitmap.getHeight();
//        bitmapWidth:1980
//        bitmapHeight:1980
//        naviMapBitmap width:1722
//        naviMapBitmap height:1815
        //创建bitmap 用于将地图图片shader后绘制 ARGB_8888是为了防止背景变成黑色
        Bitmap target = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        //创建LinearGradient，用以产生从左上角到右下角的颜色渐变效果
        //CLAMP 是在画布大小大于图片的时候将图片边缘像素重复，表现为边缘拉长线
        //REPEAT 是将图片重复，如x轴大于图片大小的地方将图片重复绘制，
        //MIRROR 是将图片像镜子一样对称绘制
        BitmapShader mapShader = new BitmapShader(naviMapBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
        //将组合的composeShader作为画笔paint绘图所使用的shader
        mPaint.setShader(mapShader);
        //用composeShader绘制矩形区域
        canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, mPaint);
        return target;
    }
}
