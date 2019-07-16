package com.cgy.mycollections.functions.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.utils.L;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.Logger;

/**
 * 文件操作 读取等
 */
public class FileDemo extends AppCompatActivity {

    private final int REQUEST_FILE = 2;
    String mUploadFilePath = null;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_file_path://获取本地文件路径
                PermissionManager.requestExternalPermission(FileDemo.this, "for test");
                break;
            case R.id.android_anim:
                break;
            default:
                break;
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("externalPermissionGranted");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_FILE);
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(FileDemo.this, "需要读取文件权限");
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
                        //TODO:待研究
                        mUploadFilePath = MediaHelper.getRealPathFromURI(this, data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                content://com.android.providers.downloads.documents/document/427
//                content://com.android.fileexplorer.myprovider/external_files/Download/20170817_155841.jpg
                L.e("filePath:" + mUploadFilePath);
//                mHardwarePath.setText("固件路径:" + mUploadFilePath);//暂时不能用download中的 只能用external sd卡中的内容
            } else {//没有打开蓝牙
                Toast.makeText(this, "获取本地文件失败!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
