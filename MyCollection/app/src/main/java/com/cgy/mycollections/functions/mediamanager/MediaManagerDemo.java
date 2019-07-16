package com.cgy.mycollections.functions.mediamanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
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

/**
 * 媒体文件管理,用于管理安卓系统的 媒体存储功能
 * 隐藏图片，获取最近的媒体文件等功能
 * https://www.cnblogs.com/imouto/p/how-do-apps-interact-with-media-storage-service.html
 */
public class MediaManagerDemo extends AppCompatActivity {
    private final int REQUEST_FILE = 2;

    @BindView(R.id.log)
    TextView mLogV;

    String mUploadFilePath = null;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_manager_demo);
        ButterKnife.bind(this);

        registerReceiver(this);
        PermissionManager.requestExternalPermission(MediaManagerDemo.this, "for test");

        L.e("isMediaScannerScanning:" + MediaHelper.isMediaScannerScanning(this));
        L.e("MediaStore.getVersion:" + MediaStore.getVersion(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 注册媒体存储监听的广播
     *
     * @param context
     */
    private void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        filter.addDataScheme("file");
        context.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            L.e("Receiver", "BroadcastReceiver action = " + action + ", uri = " + uri);
            if (uri != null && "file".equals(uri.getScheme())) {
                if (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE.equals(action)) {
                    String filePath = uri.getPath();
                    // TODO: filePath 文件已改变，APP 刷新界面
                } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
                    // TODO: 整个磁盘扫描完成，APP 刷新界面
                }
            }
        }
    };

    @OnClick({R.id.get_recent_files, R.id.add_file})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_recent_files:
//                batchRename(new File("/storage/emulated/0/CLImages/"));
                List<String> imageList = MediaHelper.getMediaImages(this, "CLImages");
                L.e("getMediaImages 文件总数:" + imageList.size());
                for (int i = 0, len = imageList.size(); i < len; i++) {
                    L.e("getMediaImages:" + imageList.get(i));
                }
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
     *
     * @param directory
     */
    public void batchRename(File directory) {
        Preconditions.checkNotNull(directory);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            try {
                for (int i = 0, len = files.length; i < len; i++) {
                    File file = files[i];
                    if (!file.getName().startsWith(".")) {
                        File targetFile = new File(directory.getPath() + "/." + file.getName());
                        L.e(file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                        FileUtils.rename(file, targetFile);
                    } else {
                        L.e("已隐藏,跳过：" + file.getPath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("externalPermissionGranted");

    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(MediaManagerDemo.this, "需要读取文件权限");
        mPermissionDialog.show();
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
