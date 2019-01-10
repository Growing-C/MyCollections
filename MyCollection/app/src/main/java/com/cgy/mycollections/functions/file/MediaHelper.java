package com.cgy.mycollections.functions.file;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MediaHelper {

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
