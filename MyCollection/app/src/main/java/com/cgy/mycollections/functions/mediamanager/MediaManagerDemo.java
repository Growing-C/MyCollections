package com.cgy.mycollections.functions.mediamanager;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.file.FileInfo;
import com.cgy.mycollections.functions.file.ProtectedFilesActivity;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.utils.L;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Preconditions;

import java.io.File;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.Logger;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;

/**
 * 媒体文件管理,用于管理安卓系统的 媒体存储功能
 * 隐藏图片，获取最近的媒体文件等功能
 * https://www.cnblogs.com/imouto/p/how-do-apps-interact-with-media-storage-service.html
 */
public class MediaManagerDemo extends BaseActivity {
    private final int REQUEST_FILE = 2;

    @BindView(R.id.log)
    TextView mLogV;
    @BindView(R.id.protect_amount)
    TextView mProtectAmountV;
    @BindView(R.id.image_amount)
    TextView mImageAmountV;

    String mUploadFilePath = null;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_manager_demo);
        ButterKnife.bind(this);

//        registerReceiver(this);
        PermissionManager.requestExternalPermission(MediaManagerDemo.this, "for test");

    }

    /**
     * 初始化内容，必须获取到存储权限
     */
    private void initContent() {
        L.e("isMediaScannerScanning:" + MediaHelper.isMediaScannerScanning(this));
        L.e("MediaStore.getVersion:" + MediaStore.getVersion(this));

        DBOperator.getInstance().getProtectedFiles(CommonUtils.getUserId(this)).subscribe(new DisposableObserver<List<FileInfo>>() {
            @Override
            public void onNext(List<FileInfo> files) {
                L.e("getProtectedFiles onNext size:" + files.size());
                mProtectAmountV.setText(String.valueOf(files.size()));
            }

            @Override
            public void onError(Throwable e) {
                showToast("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        int imageCount = MediaHelper.getMediaImages(this, null).size();
        mImageAmountV.setText(String.valueOf(imageCount));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mReceiver);
    }

//    /**
//     * 注册媒体存储监听的广播
//     *
//     * @param context
//     */
//    private void registerReceiver(Context context) {
//        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
//        filter.addDataScheme("file");
//        context.registerReceiver(mReceiver, filter);
//    }

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            Uri uri = intent.getData();
//            L.e("Receiver", "BroadcastReceiver action = " + action + ", uri = " + uri);
//            if (uri != null && "file".equals(uri.getScheme())) {
//                if (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE.equals(action)) {
//                    String filePath = uri.getPath();
//                    File file = new File(filePath);
//                    L.e("Receiver", "ACTION_MEDIA_SCANNER_SCAN_FILE： " + filePath + "--->exist?" + file.exists());
//                    // TODO: filePath 文件已改变，APP 刷新界面
//                } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
//                    // TODO: 整个磁盘扫描完成，APP 刷新界面
//                }
//            }
//        }
//    };

    @OnClick({R.id.get_recent_files, R.id.add_file, R.id.hide_files, R.id.show_files, R.id.file_protect_image, R.id.media_images})
    public void onClick(View v) {
        Intent it;
        switch (v.getId()) {
            case R.id.file_protect_image:
                it = new Intent(this, ProtectedFilesActivity.class);
                startActivity(it);
                break;
            case R.id.media_images:
                it = new Intent(this, MediaImagesActivity.class);
                startActivity(it);
                break;
            case R.id.get_recent_files:
                scanFiles("Twitter");
//                List<String> imageList = MediaHelper.getMediaImages(this, "xxx");
//                L.e("getMediaImages 文件总数:" + imageList.size());
//                for (int i = 0, len = imageList.size(); i < len; i++) {
//                    L.e("getMediaImages:" + imageList.get(i));
//
//                    File file = new File(imageList.get(i));
//
//                    Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                    it.setData(Uri.fromFile(file));
//                    sendBroadcast(it);
//                }
                break;
            case R.id.hide_files://隐藏文件
                batchHideImage(new File("/storage/emulated/0/xxx/"));
                batchHideImage(new File("/storage/emulated/0/Pictures/Twitter/"));
                break;
            case R.id.show_files://显示文件
                batchRecoverImage(new File("/storage/emulated/0/xxx/"));
                batchHideImage(new File("/storage/emulated/0/Pictures/Twitter/"));
                break;
            case R.id.add_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.setType("*/*");//无类型限制

//                intent.setDataAndType(Uri.fromFile(parentFlie), "*/*");
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_FILE);
                break;
            default:

                break;
        }
    }

    /**
     * 扫描某个目录下的文件
     *
     * @param dirName
     */
    //"xxx"
    public void scanFiles(String dirName) {
        List<String> imageList = MediaHelper.getMediaImages(this, dirName);
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
            MediaScannerConnection.scanFile(this, pathList,
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            L.e("onScanCompleted path:" + path + "    ---uri:" + uri);

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handlerGetFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                listFile(file);
                listFile(file.getParentFile());
            } else {
                L.e("文件不存在：" + filePath);
            }
        }
    }

    public void listFile(File file) {
        Preconditions.checkNotNull(file);

        if (file.isDirectory()) {
            L.e("listFile，这是文件夹，路径为：" + file.getPath());
            File[] files = file.listFiles();
            for (int i = 0, len = files.length; i < len; i++) {
                L.e("路径" + files[i].getPath());
            }
        } else {
            L.e("listFile，这是文件，路径为：" + file.getPath());
        }
    }

    /**
     * 批量重命名 前面加个.可以隐藏文件 ，但是小米手机依然会扫描出来
     * 所以把文件名中的.jpg等后缀 的. 改成 _+_
     * xxx/xxff.jpg -> xxx/.xxff_+_jpg
     *
     * @param directory
     */
    public void batchHideImage(File directory) {
        Preconditions.checkNotNull(directory);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            String[] pathList = new String[files.length];
            try {
                for (int i = 0, len = files.length; i < len; i++) {
                    File file = files[i];
                    if (!file.getName().startsWith(".")) {
                        File targetFile = new File(directory.getPath() + "/." + file.getName().replace(".", MediaHelper.REPLACE_DOT));
                        L.e("batchHideImage", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                        FileUtils.rename(file, targetFile);
                        pathList[i] = file.getPath();
                    } else {
                        L.e("batchHideImage", "已隐藏,跳过：" + file.getPath());
                    }
                }

                //直接全部扫描，用于把系统记录的图片记录全部干掉
                MediaScannerConnection.scanFile(this, pathList,
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                L.e("batchHideImage", "onScanCompleted path:" + path + "    ---uri:" + uri);

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量恢复图片  xxx/.xxff_+_jpg -> xxx/xxff.jpg
     * 并且添加到系统的最近图片中
     *
     * @param directory
     */
    public void batchRecoverImage(File directory) {
        Preconditions.checkNotNull(directory);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            String[] pathList = new String[files.length];
            try {
                for (int i = 0, len = files.length; i < len; i++) {
                    File file = files[i];
                    String fileName = file.getName();
                    if (fileName.startsWith(".") && fileName.length() > 1) {
                        File targetFile = new File(directory.getPath() + "/" + fileName.substring(1).replace(MediaHelper.REPLACE_DOT, "."));
                        L.e("batchRecoverImage", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                        FileUtils.rename(file, targetFile);
                        pathList[i] = targetFile.getPath();
                    } else {
                        L.e("batchRecoverImage", "不符合隐藏特征,跳过：" + file.getPath());
                    }
                }

                //直接全部扫描，用于将图片添加到系统的最近文件中
                MediaScannerConnection.scanFile(this, pathList,
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                L.e("batchRecoverImage", "onScanCompleted path:" + path + "    ---uri:" + uri);

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("externalPermissionGranted");
        initContent();
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(MediaManagerDemo.this, "需要读取文件权限");
        mPermissionDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE) {
            if (resultCode == Activity.RESULT_OK) { //获取到文件路径
                if (data != null && data.getData() != null) {
                    try {
                        Uri uri = data.getData();
                        L.e("uri:" + uri);
                        L.e("uri getPath:" + uri.getPath());
                        mUploadFilePath = MediaHelper.getRealPathFromURI(this, data.getData());
                        L.e("onActivityResult filePath:" + mUploadFilePath);

                        handlerGetFile(mUploadFilePath);
                        String log = mLogV.getText().toString() + mUploadFilePath;
                        mLogV.setText(log);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                content://com.android.providers.downloads.documents/document/427
//                content://com.android.fileexplorer.myprovider/external_files/Download/20170817_155841.jpg

            } else {
                Toast.makeText(this, "获取本地文件失败!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
