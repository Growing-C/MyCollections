package com.cgy.mycollections.functions.file;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.Config;
import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.listeners.OnMyItemLongClickListener;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;
import com.cgy.mycollections.widgets.pickerview.utils.PickerViewAnimateUtil;
import com.growingc.mediaoperator.beans.FileInfo;
import com.growingc.mediaoperator.beans.ImageInfo;
import com.growingc.mediaoperator.beans.SortInfo;
import com.growingc.mediaoperator.imagebrowser.ShowImagesActivity;
import com.growingc.mediaoperator.utils.FileSortUtils;
import com.growingc.mediaoperator.utils.FileUtil;
import com.growingc.mediaoperator.utils.ImageHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import appframe.network.retrofit.callback.ApiCallback;
import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.L;
import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件操作 读取等
 */
public class FileDemo extends AppBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.file_list)
    RecyclerView mFileListV;
    @BindView(R.id.operate_holder)
    View mBottomMenuHolderV;
    @BindView(R.id.file_path)
    FilePathView mFilePathV;
    @BindView(R.id.no_file)
    View mNoFileV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();

    String mFileOperateType;

    FileInfo targetFile = null;
    SortInfo mSortInfo;//排序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
        ButterKnife.bind(this);
        L.e("onCreate");

        setSupportActionBar(toolbar);
        setUpActionBarBack(toolbar);

        mSortInfo = SortInfo.getDefault();

        if (getIntent() != null) {
            mFileOperateType = getIntent().getStringExtra(FileConstants.KEY_FILE_OPERATE);
            targetFile = (FileInfo) getIntent().getSerializableExtra(FileConstants.KEY_FILE_INFO);
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
//            mOperateHolderV.setVisibility(View.VISIBLE);
        }

        mFileAdapter.setOnItemClickListener(new OnMyItemLongClickListener<FileInfo>() {
            @Override
            public void onItemClick(int position, FileInfo fileInfo) {
                mFilePathV.navToFile(fileInfo.getRealFile());
                if (mFileAdapter.isSelectMode())//选择模式不响应点击
                    return;

                if (!fileInfo.isDirectory()) {
                    if (ImageHelper.isPicIgnoreDot(fileInfo.getFilePath())) {
                        Intent it = new Intent(FileDemo.this, ShowImagesActivity.class);
                        it.putExtra("imageInfo", ImageInfo.importFromFileInfo(fileInfo));
                        it.putExtra("sortInfo", mSortInfo);
                        startActivity(it);
                    } else {
                        try {
                            //TODO:FileUriExposedException
                            startActivity(FileUtil.openFileIncludingHiddenFile(FileDemo.this, fileInfo.getShowFile()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        FileInfoDialogFragment.newInstance(fileInfo)
//                                .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");
                    }
                }
            }

            @Override
            public void onItemLongClick(int position, FileInfo fileInfo) {
                if (mFileAdapter.isSelectMode()) {
                    mFileAdapter.setIsSelect(false);
                    hideBottomMenu();
                } else {
                    showBottomMenu();
                    mFileAdapter.setIsSelect(true);
                    mFileAdapter.select(fileInfo);
                }
            }

        });
        mFileListV.setAdapter(mFileAdapter);
        mFileListV.addItemDecoration(new SpaceItemDecoration(2));

        PermissionManager.requestExternalPermission(FileDemo.this, "for test");

//        mRootDir = Environment.getExternalStorageDirectory();
//        mCurrentDir = mRootDir;
        mBottomMenuHolderV.setVisibility(View.GONE);
    }

    /**
     * 获取文件必须先获取文件权限，不然查询某个文件夹下面所有文件返回的是空
     */
    private void getDataAfterPermissionGranted() {
        if (targetFile != null && targetFile.isDirectory()) {
            mFilePathV.setRootDir(targetFile.getRealFile());
            mFilePathV.navToFile(targetFile.getRealFile());
        } else
            refreshCurrentFileList(mFilePathV.getRootDir());
    }

    private void refreshCurrentFileList(File parent) {
        mFileList = getSortedChildFiles(parent, Config.isShowHiddenFiles(), true);
        mFileAdapter.clearSelectedFiles();//清空选中的文件
        mFileAdapter.setData(mFileList);
        L.e(parent.getName() + "-->mFileList size:" + mFileList.size());

        if (mFileList.isEmpty()) {
            mNoFileV.setVisibility(View.VISIBLE);
        } else {
            mNoFileV.setVisibility(View.GONE);
        }
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
            if (files != null) {
                for (File file : files) {
                    FileInfo fileInfo = new FileInfo(file);

                    if (!TextUtils.isEmpty(fileInfo.getFileName())) {
                        //显示隐藏文件或者 不显示隐藏文件且点头不是.就添加
                        if (containHideFiles || !fileInfo.getFileName().startsWith(".")) {
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
            }
            if (distinguishDirAndFile) {
                fileList.addAll(FileSortUtils.sortFileInfoListByName(dirList, mSortInfo.isAscending()));
                fileList.addAll(FileSortUtils.sortFileInfoListByName(rawFileList, mSortInfo.isAscending()));
            } else {
                FileSortUtils.sortFileInfoListByName(fileList, mSortInfo.isAscending());
            }
        }
        return fileList;
    }


    /**
     * 显示底部菜单
     */
    private void showBottomMenu() {
        int res = PickerViewAnimateUtil.getAnimationResource(Gravity.BOTTOM, true);
        Animation bottomInAnim = AnimationUtils.loadAnimation(this, res);
        mBottomMenuHolderV.setVisibility(View.VISIBLE);
        mBottomMenuHolderV.startAnimation(bottomInAnim);
    }

    /**
     * 隐藏底部菜单
     */
    private void hideBottomMenu() {
        if (mBottomMenuHolderV.getVisibility() != View.VISIBLE) {
            return;
        }

        int res = PickerViewAnimateUtil.getAnimationResource(Gravity.BOTTOM, false);
        Animation bottomOutAnim = AnimationUtils.loadAnimation(this, res);
        bottomOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomMenuHolderV.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBottomMenuHolderV.startAnimation(bottomOutAnim);
    }

    @SuppressLint("CheckResult")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail://详情
                FileInfoDialogFragment.newInstance(mFileAdapter.getSelectedFiles())
                        .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");
                break;
            case R.id.delete://删除
                showDeleteDialog(mFileAdapter.getSelectedFiles());
                break;
            default:
                break;
        }
    }

    private void showDeleteDialog(ArrayList<FileInfo> filesToDelete) {
        if (filesToDelete == null || filesToDelete.size() == 0)
            return;
        int len = filesToDelete.size();
        StringBuilder filePathList = new StringBuilder();
        for (int i = 0; i < len; i++) {
            filePathList.append(i);
            filePathList.append(".");
            filePathList.append(filesToDelete.get(i).getFilePath());
            filePathList.append("\n");
        }
        new AlertDialog.Builder(FileDemo.this)
                .setMessage("确认删除" + filesToDelete.size() + "个文件？ \n" + filePathList)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFiles(filesToDelete);
                    }
                }).create().show();
    }

    private void deleteFiles(ArrayList<FileInfo> filesToDelete) {
        if (filesToDelete == null || filesToDelete.size() == 0)
            return;
        int deleteFileCount = 0;
        for (FileInfo fileInfo :
                filesToDelete) {
            if (fileInfo.isDirectory()) {
                //TODO:文件夹删除不应该这么简单，应该要用户多确认
            } else {//TODO:隐藏文件的删除
                FileUtil.deleteFile(fileInfo.getFilePath());
                deleteFileCount++;
            }
        }
        new ToastCustom(FileDemo.this, "删除了 " + deleteFileCount + "个文件", Toast.LENGTH_SHORT).show();

//        hideBottomMenu();

        refreshCurrentFileList(mFilePathV.getCurrentDir());
    }

    private void deleteFile(FileInfo fileInfo) {
        if (fileInfo.isDirectory()) {
            //TODO:文件夹删除不应该这么简单，应该要用户多确认
        } else
            FileUtil.deleteFile(fileInfo.getFilePath());

        hideBottomMenu();

        refreshCurrentFileList(mFilePathV.getCurrentDir());
    }

    private void confirmSelectFile() {
        List<FileInfo> fileList = mFileAdapter.getSelectedFiles();
        if (!fileList.isEmpty()) {
            DBOperator.getInstance().addProtectedFiles(CommonUtils.getUserId(this), fileList).subscribe(new ApiCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean model) {
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {

                }

                @Override
                public void onFinish() {

                }
            });
        } else {
            finish();
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        L.i("externalPermissionGranted");
        getDataAfterPermissionGranted();

//        testFiles();

        //不知道为什么 以下api 都显示红的，即使点进去方法都有
        //android 11 管理所有文件需要权限，可以将targetSDk降低来绕过
//        https://developer.android.com/training/data-storage/manage-all-files
//        if (Environment.isExternalStorageManager()) {
//
//        }
//        Intent intent = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");

//        startActivity(intent);
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        L.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(FileDemo.this, "需要读取文件权限");
        mPermissionDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mBottomMenuHolderV.getVisibility() == View.VISIBLE) {
            hideBottomMenu();
            mFileAdapter.setIsSelect(false);
            return;
        }

        if (!mFilePathV.navUp())
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file, menu);
        MenuItem showOrHideFileMenu = menu.findItem(R.id.action_show_hidden_files);
        MenuItem confirmMenu = menu.findItem(R.id.action_confirm);
        if (showOrHideFileMenu != null) {
            showOrHideFileMenu.setChecked(Config.isShowHiddenFiles());
        }
        L.e("onCreateOptionsMenu");
        if (FileConstants.OPERATE_TYPE_SELECT.equals(mFileOperateType)) {
            confirmMenu.setVisible(true);
        } else {
            confirmMenu.setVisible(false);
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
            case R.id.action_show_hidden_files://隐藏 隐藏文件
                item.setChecked(!item.isChecked());
                Config.setShowHiddenFiles(item.isChecked());

                refreshCurrentFileList(mFilePathV.getCurrentDir());

                mFileAdapter.setShowHideFiles(item.isChecked());
                break;
            case R.id.action_confirm://确定选择文件
                confirmSelectFile();
                break;
            case R.id.action_reverse://顺序反转
                mSortInfo.setAscending(!mSortInfo.isAscending());
                if (mFileList != null && mFileList.size() > 0) {
                    Collections.reverse(mFileList);
                    mFileAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.action_filter://筛选

                break;
            default:
                break;
        }

        return true;
    }


    //--------------------测试-----------------------------
    private void testFiles() {
//    <!--    files-path	Context.getFilesDir() /data/user/0/com.cgy.mycollections/files-->
//    <!--    cache-path	Context.getCacheDir() /data/user/0/com.cgy.mycollections/cache-->
//    <!--    external-path	Environment.getExternalStorageDirectory() /storage/emulated/0-->
//    <!--    external-files-path	Context.getExternalFilesDir(null) /storage/emulated/0/Android/data/com.cgy.mycollections/files-->
//    <!--    external-cache-path	Context.getExternalCacheDir() /storage/emulated/0/Android/data/com.cgy.mycollections/cache-->

        File filesDir = getFilesDir();
        File cachesDir = getCacheDir();
        File externalDir = Environment.getExternalStorageDirectory();
        File externalFilesDir = getExternalFilesDir(null);
        File externalCacheDir = getExternalCacheDir();
        L.i("teta", filesDir.exists() + "---filesDir:  " + filesDir.getAbsolutePath());
        L.i("teta", cachesDir.exists() + "---cachesDir:  " + cachesDir.getAbsolutePath());
        L.i("teta", externalDir.exists() + "---externalDir:  " + externalDir.getAbsolutePath());
        L.i("teta", externalFilesDir.exists() + "---externalFilesDir:  " + externalFilesDir.getAbsolutePath());
        L.i("teta", externalCacheDir.exists() + "---externalCacheDir:  " + externalCacheDir.getAbsolutePath());
        File rootDir = Environment.getRootDirectory();
        L.i("teta", rootDir.exists() + "---rootDir:  " + rootDir.getAbsolutePath());
        L.i("teta", "---mounted:  " + (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())));
        getPermission(externalDir);
//        FileUtil.listFile(externalDir);
//        if (externalDir.isDirectory()) {
//            File[] childFiles = new File("/storage").listFiles();
//            for (File child :
//                    childFiles) {
//                FileUtil.listFile(child);
//            }
//        }
    }

    private void getPermission(File destFile) {
        //获取权限
        // android 11 external Sd卡上的其他文件都没有读写权限，所以listFiles 找不到，可以通过降低targetSdk版本来暂时解决，或者适配 MANAGE_EXTERNAL_STORAGE 权限
        // android 10 可以通过在manifest 中加上     android:requestLegacyExternalStorage="true" 来暂时禁用分区存储（注意compile sdk 和targetSdk都要是29或以下）
        L.i(destFile.getPath() + "  canRead:" + destFile.canRead());
        L.i(destFile.getPath() + "  canWrite:" + destFile.canWrite());
        if (!destFile.canRead() || !destFile.canWrite()) {
            try {
                /* Missing read/write permission, trying to chmod the file */
                Process su;
                su = Runtime.getRuntime().exec("su");
                String cmd = "chmod 666 /dev/video0\nexit\n";
                su.getOutputStream().write(cmd.getBytes());
//                if ((su.waitFor() != 0) || !destFile.canRead()
//                        || !destFile.canWrite()) {
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        try {
//            String command = "chmod 777 " + destFile.getAbsolutePath();
//            L.i("zyl", "command = " + command);
//            Runtime runtime = Runtime.getRuntime();
//
//            Process proc = runtime.exec(command);
//        } catch (IOException e) {
//            L.i("zyl", "chmod fail!!!!");
//            e.printStackTrace();
//        }
    }
}
