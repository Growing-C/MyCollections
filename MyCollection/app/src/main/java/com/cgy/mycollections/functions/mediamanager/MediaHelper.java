package com.cgy.mycollections.functions.mediamanager;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;


import androidx.annotation.NonNull;

import com.cgy.mycollections.functions.mediamanager.images.MediaInfo;
import com.cgy.mycollections.functions.mediamanager.images.ThumbnailInfo;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.RxUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Images表：主要存储images信息。可以看一下这个表的结构：
 * <p>
 * CREATE TABLE images (_id INTEGER PRIMARY KEY,_data TEXT,_size INTEGER,_display_name TEXT,mime_type TEXT,title
 * <p>
 * TEXT,date_added INTEGER,date_modified INTEGER,description TEXT,picasa_id TEXT,isprivate INTEGER,latitude DOUBL
 * <p>
 * E,longitude DOUBLE,datetaken INTEGER,orientation INTEGER,mini_thumb_magic INTEGER,bucket_id TEXT,bucket_displa
 * <p>
 * y_name TEXT);
 * ---------------------
 * Thumbnails表：这个表和images表是有直接关系的。主要存储图片的缩略图，Android为每一张保存进系统的图片文件都会自动生成一张缩略图文件。关于这一点还有一些特殊的技巧后面再讲。我们可以看一下这个表的结构：
 * <p>
 * CREATE TABLE thumbnails (_id INTEGER PRIMARY KEY,_data TEXT,image_id INTEGER,kind INTEGER,width INTEGER,height INTEGER);
 * <p>
 * 每一张image对应一条thumbnail记录。
 * ---------------------
 * 原文：https://blog.csdn.net/fengye810130/article/details/11522741
 * <p>
 * mediaStore的数据库中存储图片的columns
 * total:20--column name:->_id
 * total:20--column name:->_data   内容是文件路径 如：/storage/emulated/0/xxx/.20190619_070221.gif
 * total:20--column name:->_size
 * total:20--column name:->_display_name
 * total:20--column name:->mime_type
 * total:20--column name:->title
 * total:20--column name:->date_added
 * total:20--column name:->date_modified
 * total:20--column name:->description
 * total:20--column name:->picasa_id
 * total:20--column name:->isprivate
 * total:20--column name:->latitude
 * total:20--column name:->longitude
 * total:20--column name:->datetaken
 * total:20--column name:->orientation
 * total:20--column name:->mini_thumb_magic
 * total:20--column name:->bucket_id
 * total:20--column name:->bucket_display_name
 * total:20--column name:->width
 * total:20--column name:->height
 */
public class MediaHelper {


    public static final String REPLACE_DOT = "_+_";//用于替换文件扩展名中的. 用于图片隐藏

    /**
     * 媒体存储服务是否在扫描
     *
     * @param context
     * @return
     */
    public static boolean isMediaScannerScanning(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MediaStore.getMediaScannerUri(), new String[]{
                    MediaStore.MEDIA_SCANNER_VOLUME}, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return "external".equals(cursor.getString(0));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return false;
    }

    /**
     * 扫描某个目录下的文件，目标是让系统 图册中 刷新该文件夹中的图片
     *
     * @param dirName
     */
    public static void scanImageFiles(@NonNull Context context, String dirName) {
        List<String> imageList = getMediaImages(context, dirName);
        L.e("getMediaImages 文件夹名：" + dirName + "-->文件总数:" + imageList.size());
        if (imageList.isEmpty())
            return;

        String[] pathList = new String[imageList.size()];
        for (int i = 0, len = imageList.size(); i < len; i++) {
            L.d("getMediaImages:" + imageList.get(i));
            pathList[i] = imageList.get(i);
            //通过广播 也可以实现扫描 不过可能需要在BroadcastReceiver中接收扫描完成事件，不太方便
//            File file = new File(imageList.get(i));
//
//            Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            it.setData(Uri.fromFile(file));
//            sendBroadcast(it);
        }
        try {
            //直接全部扫描
            MediaScannerConnection.scanFile(context, pathList,
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            L.e("onScanCompleted path:" + path + "    ---uri:" + uri);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ThumbnailInfo> getThumbnailsList(final Context context) {
        List<ThumbnailInfo> thumbInfoList = new ArrayList<>();

        //获取cursor
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, // URI,可以有多种形式
                null,
                null,
                null,
                null);//按照修改时间降序排列
        if (cursor != null) {
            //图片路径所在列的索引

            int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int thumbPathIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
            int imageIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID);
            int kindIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.KIND);
            int widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.WIDTH);
            int heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.HEIGHT);
//
            L.e("getThumbnailsInfo 图片数目：" + cursor.getCount());
            while (cursor.moveToNext()) {
                ThumbnailInfo info = new ThumbnailInfo();
                //打印图片的路径
                info.id = cursor.getString(idIndex);
                info.data = cursor.getString(thumbPathIndex);
                info.imageId = cursor.getString(imageIdIndex);
                info.kind = cursor.getInt(kindIndex);
                info.width = cursor.getInt(widthIndex);
                info.height = cursor.getInt(heightIndex);
//                L.e(info.toString());
                thumbInfoList.add(info);
            }
            cursor.close();
        }
        return thumbInfoList;
    }

    public static Observable<List<ThumbnailInfo>> getThumbnailInfoList(final Context context) {
        return Observable.create(new ObservableOnSubscribe<List<ThumbnailInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ThumbnailInfo>> e) throws Exception {
                e.onNext(getThumbnailsList(context));
                e.onComplete();
            }
        }).compose(RxUtil.<List<ThumbnailInfo>>applySchedulersJobUI());
    }

    /**
     * 删除图片文件,并且通知mediaStore
     *
     * @param imageFile
     */
    public static void deleteMediaImage(Context context, File imageFile) {
        //Update media provider if necessary
        context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.DATA + "=?", new String[]{imageFile.getAbsolutePath()});

        Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        it.setData(Uri.fromFile(imageFile));
        context.sendBroadcast(it);
    }

    public static Observable<List<MediaInfo>> getMediaImageInfos(final Context context, final String targetFileDir) {
//          String id;
//          String data;//内容是文件路径 如：/storage/emulated/0/xxx/.20190619_070221.gif
//          String size;
//          String display_name;
//          String mime_type;
//          String title;
//          String date_added;//添加日期  单位s
//          String date_modified;//修改日期 单位s
//          String description;
//          String picasa_id;
//          String isprivate;
//          String latitude;
//          String longitude;
//          String datetaken;
//          String orientation;
//          String mini_thumb_magic;
//          String bucket_id;
//          String bucket_display_name;
//          String width;
//          String height;
        return Observable.create(new ObservableOnSubscribe<List<MediaInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MediaInfo>> e) throws Exception {
                List<MediaInfo> imageInfoList = new ArrayList<>();

                //获取cursor
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // URI,可以有多种形式
                        null,
                        null,
                        null,
                        MediaStore.Images.Media.DATE_MODIFIED + " DESC");//按照修改时间降序排列
                if (cursor != null) {
                    //图片路径所在列的索引
                    int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    int photoPathIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
                    int displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                    int titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                    int addDateIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
                    int modifyDateIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
                    int mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);

                    L.e("getMediaImages 图片数目：" + cursor.getCount());
                    while (cursor.moveToNext()) {
                        MediaInfo info = new MediaInfo();
                        //打印图片的路径
                        info.id = cursor.getString(idIndex);
                        info.data = cursor.getString(photoPathIndex);
                        info.size = cursor.getInt(sizeIndex);
                        info.display_name = cursor.getString(displayNameIndex);
                        info.title = cursor.getString(titleIndex);
                        info.date_added = cursor.getInt(addDateIndex);
                        info.date_modified = cursor.getInt(modifyDateIndex);
                        info.mime_type = cursor.getString(mimeTypeIndex);


                        if (!TextUtils.isEmpty(info.data)) {
                            if (!TextUtils.isEmpty(targetFileDir)) {
                                if (info.data.contains(targetFileDir)) {
                                    imageInfoList.add(info);
                                }
                            } else {
                                imageInfoList.add(info);
                            }
                        }
                    }
                    cursor.close();
                }

                //根据修改日期进行排序
                Collections.sort(imageInfoList, new Comparator<MediaInfo>() {
                    @Override
                    public int compare(MediaInfo mediaStoreData, MediaInfo mediaStoreData2) {
                        return Long.compare(mediaStoreData2.date_modified, mediaStoreData.date_modified);
                    }
                });
                e.onNext(imageInfoList);
                e.onComplete();
            }
        }).compose(RxUtil.<List<MediaInfo>>applySchedulersJobUI());
    }

    /**
     * 获取 目标文件夹下面的所有图片（目标文件夹为空则是手机中的所有图片）
     *
     * @param context
     * @param targetFileDir
     * @return
     */
    public static List<String> getMediaImages(Context context, String targetFileDir) {
        List<String> imageFilePathList = new ArrayList<>();

        //获取cursor
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // URI,可以有多种形式
                null,
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED + " DESC");//按照修改时间降序排列
        if (cursor != null) {
            //图片路径所在列的索引
            int indexPhotoPath = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int modifyDate = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
//            int columnCount = cursor.getColumnCount();
//            String[] columns = cursor.getColumnNames();
//            for (String column : columns) {
//                L.e("total:" + columnCount + "--column name:->" + column);
//            }
            L.e("getMediaImages 图片数目：" + cursor.getCount());
            while (cursor.moveToNext()) {
                //打印图片的路径
                String uri = cursor.getString(indexPhotoPath);
                long modifyTime = cursor.getInt(modifyDate);
//                L.e("uri", uri);
                if (modifyTime > 0) {
//                    L.e("modifyTime", modifyTime+"-modifyTime：" + TimeUtils.getTime(modifyTime * 1000));
                }
                if (!TextUtils.isEmpty(uri)) {
                    if (!TextUtils.isEmpty(targetFileDir)) {
                        if (uri.contains(targetFileDir))
                            imageFilePathList.add(uri);
                    } else {
                        imageFilePathList.add(uri);
                    }
                }
            }
            cursor.close();
        }
        return imageFilePathList;
    }

    //------------------------------------------------------------------------------------------------

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                result = result.replace("\n", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 4.4以后获取图片路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealPathFromURI(Activity context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapFromRemoteUrl(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}

//    /**
//     * 打开相机
//     *
//     * @param activity    mActivity
//     * @param bundle      传参数，需要传拒绝权限后显示在对话框中的内容
//     * @param requestCode 请求码
//     */
//    public static void openCamera(Activity activity, Bundle bundle, int requestCode) {
//        final String dinieDialogMsg = bundle.getString(Constants.DENIED_MSG);
//        if (PermissionUtil.checkPermissions(activity, 0, new PermissionUtil.PermissionDeniedMsg() {
//            @Override
//            public String setDenieDialog() {
//                /**
//                 * param 拒绝权限后的dialog显示的内容
//                 */
//                return dinieDialogMsg;
//            }
//        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
//            mCameraPath = PathUtils.getLocalImgPath();
//            if (TextUtils.isEmpty(mCameraPath)) {
//                ToastUtil.error(activity.getString(R.string.external_storage_not_exist), Toast.LENGTH_LONG);
//                return;
//            }
//
//            String status = Environment.getExternalStorageState();
//            //存储媒体已经挂载，并且挂载点可读/写。
//            if (status.equals(Environment.MEDIA_MOUNTED)) {
//
//                File dir = new File(mCameraPath);
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Uri u = null;
//                if (Build.VERSION.SDK_INT >= 24) {
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    u = FileProvider.getUriForFile(activity, SystemUtil.getPackageName(activity) + ".fileprovider", dir);
//                } else {
//                    u = Uri.fromFile(dir);
//                }
//
//                intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
//                activity.startActivityForResult(intent, requestCode);
//            }
//        }
//    }
//
//    /**
//     * 打开相册
//     *
//     * @param activity    mActivity
//     * @param bundle      传参数，需要传拒绝权限后显示在对话框中的内容
//     * @param requestCode 请求码
//     */
//    public static void openAlbum(Activity activity, Bundle bundle, int requestCode) {
//        final String dinieDialogMsg = bundle.getString(Constants.DENIED_MSG);
//        if (PermissionUtil.checkPermissions(activity, 0, new PermissionUtil.PermissionDeniedMsg() {
//            @Override
//            public String setDenieDialog() {
//                /**
//                 * param 拒绝权限后的dialog显示的内容
//                 */
//                return dinieDialogMsg;
//            }
//        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//            // 从本地相册选取图片作为头像
//            Intent intentFromGallery = new Intent();
//            // 设置文件类型
//            intentFromGallery.setType("image/*");
//            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//            activity.startActivityForResult(intentFromGallery, requestCode);
//        }
//    }


//    /**
//     * 打开裁剪
//     *
//     * @param activity    mActivity
//     * @param bundle      传参数
//     * @param requestCode 请求码
//     *                    bundle可传的数据如下，一般out的xy比例和aspect的xy比例一样
//     *                    cropPath 裁剪的图片保存的路径，用来通过onActivityResult拿到裁剪后的图片
//     *                    cropOutX 控制最终输出的图片的形状，X的大小
//     *                    cropOutY 控制最终输出的图片的形状，Y的大小
//     *                    cropAspectX 控制裁剪窗口的比例，X的比例
//     *                    cropAspectY 控制裁剪窗口的比例，Y的比例
//     */
//    public static void openCrop(Activity activity, Bundle bundle, int requestCode) {
//        final String cropPath = bundle.getString(Constants.CROP_PATH);
//        final int cropOutX = bundle.getInt(Constants.CROP_OUT_X, 240);
//        final int cropOutY = bundle.getInt(Constants.CROP_OUT_Y, 240);
//        final int cropAspectX = bundle.getInt(Constants.CROP_ASPECT_X, 1);
//        final int cropAspectY = bundle.getInt(Constants.CROP_ASPECT_Y, 1);
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            uri = FileProvider.getUriForFile(activity, SystemUtil.getPackageName(activity) + ".fileprovider", new File(cropPath));
//        } else {
//            uri = Uri.fromFile(new File(cropPath));
//        }
//        intent.setDataAndType(uri, "image/*");
//
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//
//        // aspectX , aspectY :宽高的比例
//        intent.putExtra("aspectX", cropAspectX);
//        intent.putExtra("aspectY", cropAspectY);
//
//        // outputX , outputY : 裁剪图片宽高
//        intent.putExtra("outputX", cropOutX);
//        intent.putExtra("outputY", cropOutY);
//        intent.putExtra("return-data", false);
//        intent.putExtra("scale", true);
//        intent.putExtra("scaleUpIfNeeded", true);
//        mCropPath = PathUtils.getTailorImgPath();
//        if (TextUtils.isEmpty(mCropPath)) {
//            return;
//        }
//        File file = new File(mCropPath);
////        Uri uri_output = CommonUtils.getFileUri(getContext(),file);
//        Uri uri_output = Uri.fromFile(file);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_output);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        intent.putExtra("noFaceDetection", true);
//        activity.startActivityForResult(intent, requestCode);
//    }