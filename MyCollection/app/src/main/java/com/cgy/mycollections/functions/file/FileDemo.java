package com.cgy.mycollections.functions.file;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;
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
import io.reactivex.functions.Consumer;

/**
 * 文件操作 读取等
 */
public class FileDemo extends AppCompatActivity {
    @BindView(R.id.file_list)
    RecyclerView mFileListV;
    @BindView(R.id.operate_holder)
    View mOperateHolderV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();

    File mRootDir;
    File mCurrentDir;

    String mFileOperateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mFileOperateType = getIntent().getStringExtra(FileConstants.KEY_FILE_OPERATE);
        }

        mFileListV.setLayoutManager(new LinearLayoutManager(this));
        mFileAdapter = new FileListAdapter();
        if (FileConstants.OPERATE_TYPE_SELECT.equals(mFileOperateType)) {
            //选择文件模式
            mFileAdapter.setIsSelect(true);
            mOperateHolderV.setVisibility(View.VISIBLE);
        }

        mFileAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FileInfo fileInfo = mFileAdapter.getItem(position);

                if (fileInfo.file.isDirectory()) {
                    mCurrentDir = fileInfo.file;
                    mFileList = getSortedChildFiles(fileInfo.file, false, true);
                    L.e(mCurrentDir.getName() + "-->mFileList size:" + mFileList.size());
                    mFileAdapter.setData(mFileList);
                }
            }
        });
        mFileListV.setAdapter(mFileAdapter);
        mFileListV.addItemDecoration(new SpaceItemDecoration(3));

        PermissionManager.requestExternalPermission(FileDemo.this, "for test");

        mRootDir = Environment.getExternalStorageDirectory();
        mCurrentDir = mRootDir;

        mFileList = getSortedChildFiles(mRootDir, false, true);
        mFileAdapter.setData(mFileList);
    }


    /**
     * 获取按照名称排序的子文件夹列表
     *
     * @param parent
     * @param containHideFiles      是否显示隐藏文件
     * @param distinguishDirAndFile 是否区分文件夹和文件
     * @return
     */
    public List<FileInfo> getSortedChildFiles(File parent, boolean containHideFiles, boolean distinguishDirAndFile) {
        List<FileInfo> fileList = new ArrayList<>();
        if (parent != null && parent.exists() && parent.isDirectory()) {
            L.e("getSortedChildFiles:" + parent.getPath());

            List<FileInfo> dirList = new ArrayList<>();
            List<FileInfo> rawFileList = new ArrayList<>();
            File[] files = parent.listFiles();
            for (File file : files) {
                FileInfo fileInfo = new FileInfo(file);

                if (!TextUtils.isEmpty(fileInfo.fileName)) {
                    //显示隐藏文件或者 不显示隐藏文件且点头不是.就添加
                    if (containHideFiles || !fileInfo.fileName.startsWith(".")) {
                        if (distinguishDirAndFile) {
                            if (file.isDirectory()) {
                                dirList.add(fileInfo);
                            } else {
                                rawFileList.add(fileInfo);
                            }
                        } else {
                            fileList.add(fileInfo);
                        }
                    }
                }
            }
            if (distinguishDirAndFile) {
                fileList.addAll(sortByName(dirList));
                fileList.addAll(sortByName(rawFileList));
            } else {
                sortByName(fileList);
            }
        }
        return fileList;
    }

    public List<FileInfo> sortByName(List<FileInfo> fileList) {
        if (fileList != null) {
            Collections.sort(fileList, new Comparator<FileInfo>() {
                @Override
                public int compare(FileInfo o1, FileInfo o2) {
                    return o1.fileName.toUpperCase().compareTo(o2.fileName.toUpperCase());
                }
            });
        }
        return fileList;
    }

    @SuppressLint("CheckResult")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_file_path://获取本地文件路径
                break;
            case R.id.cancel://取消选择
                finish();
                break;
            case R.id.confirm://确定选择
                List<FileInfo> fileList = mFileAdapter.getSelectedFiles();
                if (!fileList.isEmpty()) {
                    DBOperator.getInstance().addProtectedFiles(CommonUtils.getUserId(this), fileList).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                } else {
                    finish();
                }
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
                    mFileList = getSortedChildFiles(mCurrentDir, false, true);
                    mFileAdapter.setData(mFileList);
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
