package com.cgy.mycollections.functions.file;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.MediaManagerDemo;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.SharePreUtil;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.Logger;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * 受保护的文件
 */
public class ProtectedFilesActivity extends BaseActivity {
    @BindView(R.id.protected_files)
    RecyclerView mProtectedFileListV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();
    String userId;

    boolean mIsProtect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected_files);
        ButterKnife.bind(this);

        mProtectedFileListV.setLayoutManager(new LinearLayoutManager(this));
        mFileAdapter = new FileListAdapter();
        mFileAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        mProtectedFileListV.setAdapter(mFileAdapter);
        mProtectedFileListV.addItemDecoration(new SpaceItemDecoration(2));

        userId = CommonUtils.getUserId(this);

        getProtectedFiles();
    }

    private void getProtectedFiles() {
        DBOperator.getInstance().getProtectedFiles(userId).subscribe(new DisposableObserver<List<FileInfo>>() {
            @Override
            public void onNext(List<FileInfo> files) {
                L.e("getProtectedFiles onNext size:" + files.size());
                mFileList = files;
                mFileAdapter.setData(mFileList);
            }

            @Override
            public void onError(Throwable e) {
                showToast("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @OnClick({R.id.add, R.id.protect_all, R.id.remove_protect})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent it = new Intent(this, FileDemo.class);
                it.putExtra(FileConstants.KEY_FILE_OPERATE, FileConstants.OPERATE_TYPE_SELECT);
                startActivityForResult(it, 1);
                break;
            case R.id.protect_all://保护全部
                mIsProtect = true;
                PermissionManager.requestExternalPermission(ProtectedFilesActivity.this, "for test");
                break;
            case R.id.remove_protect://移除保护
                mIsProtect = false;
                PermissionManager.requestExternalPermission(ProtectedFilesActivity.this, "for test");
                break;
            default:
                break;
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("externalPermissionGranted");
        for (int i = 0, len = mFileList.size(); i < len; i++) {
            FileInfo fileInfo = mFileList.get(i);
            if (mIsProtect) {//隐藏
                if (fileInfo.file.isDirectory()) {
                    FileUtil.hideFilesUnderDir(this, fileInfo.file);
                } else {
                    FileUtil.hideSingleFile(this, fileInfo.file);
                }
                fileInfo.protectState = FileConstants.STATE_PROTECTED;

            } else {//显示
                if (fileInfo.file.isDirectory()) {
                    FileUtil.showFilesUnderDir(this, fileInfo.file);
                } else {
                    FileUtil.showSingleFile(this, fileInfo.file);
                }
                fileInfo.protectState = FileConstants.STATE_UNPROTECTED;
            }
        }

        DBOperator.getInstance().addProtectedFiles(userId, mFileList).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean o) {
                showToast("操作完成");
                getProtectedFiles();
            }

            @Override
            public void onError(Throwable e) {
                showToast("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(ProtectedFilesActivity.this, "需要读取文件权限");
        mPermissionDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getProtectedFiles();
        } else {
            showToast("取消选择");
        }
    }
}
