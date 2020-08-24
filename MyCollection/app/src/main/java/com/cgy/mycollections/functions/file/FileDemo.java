package com.cgy.mycollections.functions.file;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.cgy.mycollections.Config;
import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.functions.mediamanager.ShowImagesActivity;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.functions.sqlite.db.DBOperator;
import com.cgy.mycollections.functions.ui.wheel.WheelDemo;
import com.cgy.mycollections.listeners.OnMyItemLongClickListener;
import com.cgy.mycollections.utils.CommonUtils;
import com.cgy.mycollections.utils.FileUtil;
import com.cgy.mycollections.utils.image.ImageHelper;
import com.cgy.mycollections.utils.image.ImageLoader;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.widgets.pickerview.utils.PickerViewAnimateUtil;

import appframe.network.retrofit.callback.ApiCallback;
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
import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 文件操作 读取等
 */
public class FileDemo extends BaseActivity {
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

//    File mRootDir;
//    File mCurrentDir;

    String mFileOperateType;
//   List< FileInfo> mSelectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_demo);
        ButterKnife.bind(this);
        L.e("onCreate");

        setSupportActionBar(toolbar);
        setUpActionBarBack(toolbar);

        FileInfo targetFile = null;
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
                mFilePathV.navToFile(fileInfo.getFile());
                if (mFileAdapter.isSelectMode())//选择模式不响应点击
                    return;

                if (!fileInfo.getFile().isDirectory()) {
                    if (ImageHelper.isPicIgnoreDot(fileInfo.getFilePath())) {
                        Intent it = new Intent(FileDemo.this, ShowImagesActivity.class);
                        it.putExtra("imageInfo", ImageInfo.importFromFileInfo(fileInfo));
                        startActivity(it);
                    } else {
                        try {
                            //TODO:FileUriExposedException
                            startActivity(FileUtil.openFileIncludingHiddenFile(FileDemo.this, fileInfo.getFile()));
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

        if (targetFile != null && targetFile.getFile().isDirectory()) {
            mFilePathV.setRootDir(targetFile.getFile());
            mFilePathV.navToFile(targetFile.getFile());
        } else
            refreshCurrentFileList(mFilePathV.getRootDir());
    }

    private void refreshCurrentFileList(File parent) {
        mFileList = getSortedChildFiles(parent, Config.isShowHiddenFiles(), true);
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
                    return o1.getFileName().toUpperCase().compareTo(o2.getFileName().toUpperCase());
                }
            });
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

        StringBuilder filePathList = new StringBuilder();
        for (FileInfo fileInfo :
                filesToDelete) {
            filePathList.append(fileInfo.getFilePath());
            filePathList.append("\n");
        }
        new AlertDialog.Builder(FileDemo.this)
                .setMessage("确认删除文件？ \n" + filePathList)
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
            if (fileInfo.getFile().isDirectory()) {
                //TODO:文件夹删除不应该这么简单，应该要用户多确认
            } else {
                FileUtil.deleteFile(fileInfo.getFilePath());
                deleteFileCount++;
            }
        }
        new ToastCustom(FileDemo.this, "删除了 " + deleteFileCount + "个文件", Toast.LENGTH_SHORT).show();

        hideBottomMenu();

        refreshCurrentFileList(mFilePathV.getCurrentDir());
    }

    private void deleteFile(FileInfo fileInfo) {
        if (fileInfo.getFile().isDirectory()) {
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
                if (mFileList != null && mFileList.size() > 0) {
                    Collections.reverse(mFileList);
                    mFileAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }

        return true;
    }

}
