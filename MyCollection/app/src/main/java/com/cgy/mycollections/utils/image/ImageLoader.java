package com.cgy.mycollections.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.collection.LruCache;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.module.AppGlideModule;
import com.cgy.mycollections.functions.cache.disklrucache.DiskLruCache;
import appframe.utils.L;

import appframe.network.retrofit.callback.ApiCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description : 方便图片加载
 * 图片缓存分为内存缓存（LruCache）和硬盘缓存（DiskLruCache）
 * 防止多图OOM的核心解决思路就是使用LruCache技术,Glide默认使用了这两种缓存策略
 * DiskLruCache并没有限制数据的缓存位置，可以自由地进行设定，
 * 但是通常情况下多数应用程序都会将缓存的位置选择为 /sdcard/Android/data/<application package>/cache
 * <p>
 * 大图oom解决思路
 * https://blog.csdn.net/guolin_blog/article/details/9316683
 * <p>
 * Author :cgy
 * Date :2019/2/1
 */
public class ImageLoader {
    public static boolean isPic(String url) {
        if (TextUtils.isEmpty(url))
            return false;

        if (url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg") || url.contains(".gif"))
            return true;
        return false;
    }

    /**
     * 清除图片缓存
     *
     * @param context
     */
    public static void clearCache(final Context context) {
        applySchedulers(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                L.e("Glide start clear cache");
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                emitter.onNext(true);
                emitter.onComplete();
            }
        })).subscribe(new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean model) {

            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行(居然是必须在Ui线程)
                L.e("Glide end clear cache");
            }
        });
    }

    /**
     * Glide加载图片，先在线程中加载，然后在主线程中设置，解决 glide直接加载会卡住屏幕的问题
     *
     * @param url
     * @param width
     * @param height
     * @param imageView
     */
    public static void loadImage(final Context context, final String url, final int width, final int height, final BitmapTransformation transformation, final ImageView imageView) {
        imageView.setTag(url);
        if (TextUtils.isEmpty(url)) {
            L.e("loadImage but url is null!!!");
            return;
        }
        applySchedulers(Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
//                L.e("loadImage start glide");
                int targetW = 500;
                int targetH = 500;
                if (width > 0)
                    targetW = width;
                if (height > 0)
                    targetH = height;

                Bitmap myBitmap;
                if (transformation != null) {
//                    myBitmap = Glide.with(context)
//                            .load(url)
//                            .asBitmap()
//                            .transform(transformation)
//                            .into(targetW, targetH)
//                            .get();
                } else {
//                    myBitmap = Glide.with(context)
//                            .load(url)
//                            .asBitmap()
//                            .centerCrop()
//                            .into(targetW, targetH)
//                            .get();
                }
//                L.e("loadImage end glide");
//                if (myBitmap != null)
//                    emitter.onNext(myBitmap);
//                else
//                    emitter.onError(new NullPointerException("loadImage fail url:" + url));
                emitter.onComplete();
            }
        })).subscribe(new ApiCallback<Bitmap>() {
            protected void safeOnNext(Bitmap bitmap) {
                String tagUrl = (String) imageView.getTag();
//                L.e("----tagUrl:" + tagUrl);
//                L.e("----url   :" + url);
                if (!TextUtils.isEmpty(tagUrl) && tagUrl.equalsIgnoreCase(url))//和tag相同才加载
                    imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onSuccess(Bitmap model) {

            }

            @Override
            public void onFailure(int code, String msg) {
                L.e("loadImage onFailure url:" + url + "--" + msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * Glide加载图片，先在线程中加载，然后在主线程中设置，解决 glide直接加载会卡住屏幕的问题
     *
     * @param url
     * @param width
     * @param height
     * @param imageView
     */
    public static void loadImage(final Context context, final String url, final int width, final int height, final ImageView imageView) {
        loadImage(context, url, width, height, null, imageView);
    }

    public static void loadImageFitCenter(Context context, String url, ImageView imageView) {
//        Glide.with(context).load(url).into(imageView);
        GlideApp.with(context).load(url).fitCenter().into(imageView);
    }

    /**
     * 将本地资源图片大小缩放
     *
     * @param resId
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomImage(Context context, int resId, int w, int h) {
        Resources res = context.getResources();
        Bitmap oldBmp = BitmapFactory.decodeResource(res, resId);
        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, w, h, true);
        Drawable drawable = new BitmapDrawable(res, newBmp);
        return drawable;
    }

    private static <T> Observable<T> applySchedulers(Observable<T> var1) {
        return var1.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

//                    Glide.with(mContext).load(url).centerCrop().override(100, 50).placeholder(R.drawable.testpic).into(iv);
//                    Glide.with(mContext).load(url).asBitmap().fitCenter().placeholder(R.drawable.testpic).override(450, 190)
//                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                                    L.e("Bitmap width:" + resource.getWidth() + "   height:" + resource.getHeight());
////                                    L.e("Bitmap size:" + resource.getByteCount());
//                            iv.setImageBitmap(resource);
//                        }
//                    });