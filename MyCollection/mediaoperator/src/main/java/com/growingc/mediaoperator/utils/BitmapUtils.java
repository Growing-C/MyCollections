package com.growingc.mediaoperator.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import appframe.utils.L;
import appframe.utils.LogUtils;

/**
 * author: chengy
 * created on: 2021-6-2 11:10
 * description: 专门处理bitmap
 */
public class BitmapUtils {
    /**
     * 水平翻转图片
     *
     * @param source 源图片
     * @return 翻转后的图片
     */
    public static Bitmap horizontalReverseBitmap(@NonNull Bitmap source) {
        Matrix m = new Matrix();
        m.setScale(-1, 1);//水平翻转

        int w = source.getWidth();
        int h = source.getHeight();
        //生成的翻转后的bitmap
        return Bitmap.createBitmap(source, 0, 0, w, h, m, true);
    }

    /**
     * 根据原图片宽高和目标宽高计算需要 压缩的比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    /**
     * 加载本地图片 并且压缩图片，设置为目标的宽高
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 读取SD卡图片并缩小到相应尺寸
     */
    public static final Bitmap getSizeBitmap(String filepath, int maxWidth, int maxHeight) {
        Bitmap bitmap = null;
        if (!new File(filepath).exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 当为true时，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeFile(filepath, options);
            LogUtils.i("未缩放，srcWidth = " + srcWidth + ", srcHeight = " + srcHeight);
        } else {
            LogUtils.i("缩放前，srcWidth = " + srcWidth + ", srcHeight = " + srcHeight);
            float be = 1;
            if (srcWidth / (float) srcHeight >= maxWidth / (float) maxHeight)
                be = srcWidth / (float) maxWidth;
            else
                be = srcHeight / (float) maxHeight;
            options.inSampleSize = (int) be;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeFile(filepath, options);

            if (bitmap == null) {// 此读图方式只是试试
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(
                            new FileInputStream(new File(filepath)).getFD(), null,
                            options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /** 若只是缩小图片，到此 return bitmap即可，以下内容是返回的固定大小图片 */

            int tempWidth = bitmap.getWidth();
            int tempHeight = bitmap.getHeight();
            float scale_w = ((float) maxWidth) / tempWidth;
            float scale_h = ((float) maxHeight) / tempHeight;
            // 按原图比例缩小
            Matrix matrix = new Matrix();
            float scale = (scale_w < scale_h ? scale_w : scale_h);
            matrix.postScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, tempWidth, tempHeight, matrix, true);
            LogUtils.i("tempWidth = " + tempWidth + "tempHeight = " + tempHeight);
        }
        return bitmap;
    }

    //<editor-fold desc="图片转换相关">

    /**
     * bitmap转换成byteBuffer
     *
     * @param bitmap 源
     * @return byteBuffer
     */
    public static ByteBuffer convertBitmap2Buffer(@NonNull Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        L.d("testa", "convertBitmap2Buffer bytecount:" + bitmap.getByteCount());
        L.d("testa", "convertBitmap2Buffer bufferArraySize:" + byteBuffer.array().length);
        byteBuffer.position(0);
        return byteBuffer;
    }

    /**
     * bitmap转换成jpg
     *
     * @param bitmap 原图片
     * @return jpg的byte[]
     */
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
            try {
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="bitmap存储相关">

    /**
     * 存储bitmap
     */
    public static void saveBitmap(@NonNull String filePath, @NonNull Bitmap bitmap) {
        File file = FileUtil.createFile(filePath);
        try {
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把bitmap图像保存到本地，和上面的方法基本一致，区别只在这个一个方法就可以了
     *
     * @param bitmap
     * @param path
     * @return true-代表保存成功，false-代表保存失败
     */
    public static boolean saveBitmapToSdcard(Bitmap bitmap, String path) {
        if (bitmap != null && !bitmap.isRecycled()) {
            File file = new File(path);
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (file.exists())
                file.delete();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    out.flush();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    //</editor-fold>
}
