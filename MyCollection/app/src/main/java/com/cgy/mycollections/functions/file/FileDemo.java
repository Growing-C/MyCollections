package com.cgy.mycollections.functions.file;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cgy.mycollections.Config;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.ShowImagesActivity;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.utils.FileUtil;
import com.cgy.mycollections.utils.image.ImageLoader;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;
import com.cgy.mycollections.listeners.OnItemClickListener;

import appframe.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 文件操作 读取等
 */
public class FileDemo extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.file_list)
    RecyclerView mFileListV;
    @BindView(R.id.operate_holder)
    View mOperateHolderV;
    @BindView(R.id.file_path)
    FilePathView mFilePathV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();

//    File mRootDir;
//    File mCurrentDir;

    String mFileOperateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            mFileOperateType = getIntent().getStringExtra(FileConstants.KEY_FILE_OPERATE);
        }

        //文件选中
        mFilePathV.setPathSelectListener(new FilePathView.IPathSelectListener() {
            @Override
            public void onDirPathSelected(File file) {
                refreshCurrentFileList(file);

                mFileListV.scrollToPosition(0);
            }
        });

        mFileListV.setLayoutManager(new LinearLayoutManager(this));
        mFileAdapter = new FileListAdapter(Config.isShowHiddenFiles());
        if (FileConstants.OPERATE_TYPE_SELECT.equals(mFileOperateType)) {
            //选择文件模式
            mFileAdapter.setIsSelect(true);
            mOperateHolderV.setVisibility(View.VISIBLE);
        }

        mFileAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FileInfo fileInfo = mFileAdapter.getItem(position);
                mFilePathV.navToFile(fileInfo.file);
                if (!fileInfo.file.isDirectory()) {
                    if (ImageLoader.isPic(fileInfo.filePath)) {
                        Intent it = new Intent(FileDemo.this, ShowImagesActivity.class);
                        it.putExtra("imageInfo", ImageInfo.importFromFileInfo(fileInfo));
                        startActivity(it);
                    } else {
                        try {
                            //TODO:FileUriExposedException
                            startActivity(FileUtil.openFile(FileDemo.this, fileInfo.filePath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        FileInfoDialogFragment.newInstance(fileInfo)
//                                .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");
                    }
                }
            }
        });
        mFileListV.setAdapter(mFileAdapter);
        mFileListV.addItemDecoration(new SpaceItemDecoration(3));

        PermissionManager.requestExternalPermission(FileDemo.this, "for test");

//        mRootDir = Environment.getExternalStorageDirectory();
//        mCurrentDir = mRootDir;

        refreshCurrentFileList(mFilePathV.getRootDir());
    }

    private void refreshCurrentFileList(File parent) {
        mFileList = getSortedChildFiles(parent, Config.isShowHiddenFiles(), true);
        mFileAdapter.setData(mFileList);
        L.e(parent.getName() + "-->mFileList size:" + mFileList.size());
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
//            case R.id.get_file_path://获取本地文件路径
//                break;
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
        L.i("externalPermissionGranted");
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        L.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(FileDemo.this, "需要读取文件权限");
        mPermissionDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!mFilePathV.navUp())
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file, menu);
        MenuItem showOrHideFileMenu = menu.findItem(R.id.action_show_hidden_files);
        if (showOrHideFileMenu != null) {
            showOrHideFileMenu.setChecked(Config.isShowHiddenFiles());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_show_hidden_files:
                item.setChecked(!item.isChecked());
                Config.setShowHiddenFiles(item.isChecked());

                refreshCurrentFileList(mFilePathV.getCurrentDir());

                mFileAdapter.setShowHideFiles(item.isChecked());
                break;
            default:
                break;
        }

        return true;
    }

}
