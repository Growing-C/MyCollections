package com.cgy.mycollections.functions.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.androiddesign.recyclerview.SpaceItemDecoration;
import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.Logger;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件操作 读取等
 */
public class FileDemo extends AppCompatActivity {
    @BindView(R.id.file_list)
    RecyclerView mFileListV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();

    File mRootDir;
    File mCurrentDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
        ButterKnife.bind(this);

        mFileListV.setLayoutManager(new LinearLayoutManager(this));
        mFileAdapter = new FileListAdapter();
        mFileAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FileInfo fileInfo = mFileAdapter.getItem(position);

                if (fileInfo.file.isDirectory()) {
                    mCurrentDir = fileInfo.file;
                    mFileList = getSortedChildFiles(fileInfo.file);
                    mFileAdapter.setData(mFileList);
                }
            }
        });
        mFileListV.setAdapter(mFileAdapter);
        mFileListV.addItemDecoration(new SpaceItemDecoration(1));

        PermissionManager.requestExternalPermission(FileDemo.this, "for test");

        mRootDir = Environment.getExternalStorageDirectory();
        mCurrentDir = mRootDir;

        mFileList = getSortedChildFiles(mRootDir);
        mFileAdapter.setData(mFileList);
    }


    /**
     * 获取按照名称排序的子文件夹列表
     *
     * @param parent
     * @return
     */
    public List<FileInfo> getSortedChildFiles(File parent) {
        List<FileInfo> fileList = new ArrayList<>();
        if (parent != null && parent.exists() && parent.isDirectory()) {
            L.e("getSortedChildFiles:" + parent.getPath());

            File[] files = parent.listFiles();
            for (File file : files) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.file = file;
                fileInfo.fileName = file.getName();
                if (!TextUtils.isEmpty(fileInfo.fileName) && !fileInfo.fileName.startsWith(".")) {
                    //文件名必须不为空且不是.开头
                    fileList.add(fileInfo);
                }
            }

            Collections.sort(fileList, new Comparator<FileInfo>() {
                @Override
                public int compare(FileInfo o1, FileInfo o2) {
                    return o1.fileName.toUpperCase().compareTo(o2.fileName.toUpperCase());
                }
            });
        }
        return fileList;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_file_path://获取本地文件路径
                break;
            default:
                break;
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("externalPermissionGranted");
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(FileDemo.this, "需要读取文件权限");
        mPermissionDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mRootDir != null && mCurrentDir != null) {
            if (!mRootDir.equals(mCurrentDir)) {
                mCurrentDir = mCurrentDir.getParentFile();
                if (mCurrentDir.isDirectory()) {
                    mFileList = getSortedChildFiles(mCurrentDir);
                    mFileAdapter.setData(mFileList);
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
