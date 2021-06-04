package com.growingc.mediaoperator.filebrowser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growingc.mediaoperator.R;
import com.growingc.mediaoperator.beans.FileInfo;
import com.growingc.mediaoperator.db.DBOperator;
import com.growingc.mediaoperator.filebrowser.swipedrag.ItemTouchHelperAdapter;
import com.growingc.mediaoperator.filebrowser.swipedrag.SimpleItemTouchHelperCallback;
import com.growingc.mediaoperator.utils.AccountUtils;
import com.growingc.mediaoperator.utils.FileUtil;
import com.growingc.mediaoperator.widgets.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.L;
import io.reactivex.observers.DisposableObserver;

//import androidx.appcompat.widget.RecyclerView;

/**
 * 受保护的文件
 */
public class ProtectedFilesActivity extends FileOperateBaseActivity {
    SwipeRecyclerView mProtectedFileListV;

    FileListAdapter mFileAdapter;

    List<FileInfo> mFileList = new ArrayList<>();
    String userId;

    boolean mIsProtect = false;

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

            SwipeMenuItem itemDetail = new SwipeMenuItem(ProtectedFilesActivity.this);
            itemDetail.setText("详情");
            itemDetail.setTextColor(ContextCompat.getColor(ProtectedFilesActivity.this, android.R.color.white));
            itemDetail.setImage(R.drawable.ic_error_outline_24dp);
            itemDetail.setBackground(R.drawable.shape_rec);
            itemDetail.setWidth(200);
            itemDetail.setBackgroundColor(ContextCompat.getColor(ProtectedFilesActivity.this, R.color.colorPrimary));
            itemDetail.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(itemDetail); // 在Item右侧添加一个菜单。

            SwipeMenuItem removeProtectItem = new SwipeMenuItem(ProtectedFilesActivity.this);
            removeProtectItem.setText("保护");
            removeProtectItem.setTextColor(ContextCompat.getColor(ProtectedFilesActivity.this, android.R.color.white));
            removeProtectItem.setImage(R.drawable.ic_switch);
            removeProtectItem.setBackground(R.drawable.shape_rec);
            removeProtectItem.setWidth(200);
            removeProtectItem.setBackgroundColor(ContextCompat.getColor(ProtectedFilesActivity.this, R.color.colorPrimaryDark));
            removeProtectItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(removeProtectItem); // 在Item右侧添加一个菜单。


            SwipeMenuItem deleteItem = new SwipeMenuItem(ProtectedFilesActivity.this); // 各种文字和图标属性设置。
            deleteItem.setText("删除");
            deleteItem.setTextColor(ContextCompat.getColor(ProtectedFilesActivity.this, android.R.color.white));
            deleteItem.setImage(R.drawable.ic_delete);
            deleteItem.setBackground(R.drawable.shape_rec);
            deleteItem.setWidth(200);
            deleteItem.setBackgroundColor(ContextCompat.getColor(ProtectedFilesActivity.this, R.color.color_red));
            deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
        }
    };
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
            L.e("mItemMenuClickListener direction:" + direction + " menuPosition:" + menuPosition);
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                switch (menuPosition) {
                    case 0://详情
                        ArrayList<FileInfo> list = new ArrayList<>();
                        list.add(mFileList.get(position));
                        FileInfoDialogFragment.newInstance(list)
                                .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");
                        break;
                    case 1://保护 取消保护
                        switchProtectStatus(position);
                        break;
                    case 2://删除
                        showDeleteDialog(position);
                        break;
                    default:
                        break;
                }
            }
        }
    };
    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 此方法在Item拖拽交换位置时被调用。
            // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

            // 交换数据，并更新adapter。
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            Collections.swap(mFileAdapter.getData(), fromPosition, toPosition);
            mFileAdapter.notifyItemMoved(fromPosition, toPosition);

            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            // 此方法在Item在侧滑删除时被调用。

            // 从数据源移除该Item对应的数据，并刷新Adapter。
            int position = srcHolder.getAdapterPosition();
            mFileAdapter.getData().remove(position);
            mFileAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected_files);
        mProtectedFileListV = findViewById(R.id.protected_files);

        initSwipe();

        userId = AccountUtils.getUserId(this);

//        initSwipeAndDrag(mFileAdapter);
        getProtectedFiles();

    }

    private void initSwipe() {
        //此库也用了itemTouchHelper不过只有设置了.setItemViewSwipeEnabled(true)才会侧滑删除，但是此侧滑删除就和SwipeMenuCreator无关了
        mProtectedFileListV.setLayoutManager(new LinearLayoutManager(this));
        mFileAdapter = new FileListAdapter(true);
        mFileAdapter.setOnItemClickListener(new OnMyItemLongClickListener<FileInfo>() {
            @Override
            public void onItemClick(int position, FileInfo fileInfo) {
                L.e("mFileAdapter onItemClick:" + position);
                if (fileInfo.isDirectory()) {
                    //文件夹
                    Intent it = new Intent(ProtectedFilesActivity.this, FileBrowserActivity.class);
                    it.putExtra(FileConstants.KEY_FILE_INFO, fileInfo);
                    startActivity(it);
                } else {
//                    if (ImageHelper.isPicIgnoreDot(fileInfo.getFilePath())) {
//                        Intent it = new Intent(ProtectedFilesActivity.this, ShowImagesActivity.class);
//                        it.putExtra("imageInfo", ImageInfo.importFromFileInfo(fileInfo));
//                        startActivity(it);
//                    } else {
                    try {
                        Intent chooseOpenWayIntent = Intent.createChooser(
                                FileUtil.openFileIncludingHiddenFile(ProtectedFilesActivity.this, fileInfo.getShowFile()), "选择打开方式");
                        startActivity(chooseOpenWayIntent);
//                        startActivity(FileUtil.openFileIncludingHiddenFile(ProtectedFilesActivity.this, fileInfo.getShowFile()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    }
                }
//                FileInfoDialogFragment.newInstance(mFileList.get(position))
//                        .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");
            }

            @Override
            public void onItemLongClick(int position, FileInfo data) {
                ArrayList<FileInfo> fileList = new ArrayList<>();
                fileList.add(data);
                FileInfoDialogFragment.newInstance(fileList)
                        .show(getSupportFragmentManager(), "CheckInSelectRoomDialogFragment");

            }

        });
        mProtectedFileListV.addItemDecoration(new SpaceItemDecoration(2));

        mProtectedFileListV.setLongPressDragEnabled(true); // 拖拽排序，默认关闭。
//        mProtectedFileListV.setItemViewSwipeEnabled(true); // 侧滑删除，默认关闭。

        // 设置监听器。
        mProtectedFileListV.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        mProtectedFileListV.setOnItemMenuClickListener(mItemMenuClickListener);
        mProtectedFileListV.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。

        mProtectedFileListV.setAdapter(mFileAdapter);
    }

    private void initSwipeAndDrag(ItemTouchHelperAdapter itemAdapter) {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter, this);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mProtectedFileListV);
    }

    /**
     * 切换某个位置的文件保护状态
     *
     * @param position
     */
    private void switchProtectStatus(final int position) {
        FileInfo fileInfo = mFileList.get(position);

        if (fileInfo.protectState == FileConstants.STATE_PROTECTED) {
            //已保护就切换成未保护
            if (fileInfo.isDirectory()) {
                FileUtil.showFilesUnderDir(this, fileInfo.getShowFile());
            } else {
                FileUtil.showSingleFile(this, fileInfo.getShowFile());
            }
            fileInfo.protectState = FileConstants.STATE_UNPROTECTED;
        } else {
            if (fileInfo.isDirectory()) {
                FileUtil.hideFilesUnderDir(this, fileInfo.getShowFile());
            } else {
                FileUtil.hideSingleFile(this, fileInfo.getShowFile());
            }
            fileInfo.protectState = FileConstants.STATE_PROTECTED;
        }

        DBOperator.getInstance(this).addProtectedFile(userId, fileInfo).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean o) {
                showToast("操作完成");
                mFileAdapter.notifyItemChanged(position);
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

    private void showDeleteDialog(final int position) {
        FileInfo fileInfo = mFileList.get(position);
        new AlertDialog.Builder(ProtectedFilesActivity.this)
                .setMessage("确认移除文件保护？（不会删除源文件）：\n" + fileInfo.getFilePath())
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProtectedFile(position);
                    }
                }).create().show();
    }

    /**
     * 删除已保护的文件夹或者文件，仅仅是删除数据库，本地文件还在
     *
     * @param position
     */
    private void deleteProtectedFile(final int position) {
        FileInfo fileInfo = mFileList.get(position);

        if (fileInfo.protectState == FileConstants.STATE_PROTECTED) {
            //已保护就切换成未保护
            if (fileInfo.isDirectory()) {
                FileUtil.showFilesUnderDir(this, fileInfo.getShowFile());
            } else {
                FileUtil.showSingleFile(this, fileInfo.getShowFile());
            }
            fileInfo.protectState = FileConstants.STATE_UNPROTECTED;
        }

        DBOperator.getInstance(this).removeProtectedFile(userId, fileInfo).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean success) {
                if (success) {
                    showToast("删除成功");
                    mFileAdapter.getData().remove(position);
                    mFileAdapter.notifyItemRemoved(position);
                } else {
                    showToast("删除失败");
                }
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

    private void getProtectedFiles() {
        DBOperator.getInstance(this).getProtectedFiles(userId).subscribe(new DisposableObserver<List<FileInfo>>() {
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


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add) {
            Intent it = new Intent(this, FileBrowserActivity.class);
            it.putExtra(FileConstants.KEY_FILE_OPERATE, FileConstants.OPERATE_TYPE_SELECT);
            startActivityForResult(it, 1);
        } else if (id == R.id.protect_all) {//保护全部
            mIsProtect = true;
            PermissionManager.requestExternalPermission(ProtectedFilesActivity.this, "for test");
        } else if (id == R.id.remove_protect) {//移除保护
            mIsProtect = false;
            PermissionManager.requestExternalPermission(ProtectedFilesActivity.this, "for test");
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionGranted() {
        L.i("externalPermissionGranted");
        for (int i = 0, len = mFileList.size(); i < len; i++) {
            FileInfo fileInfo = mFileList.get(i);
            if (mIsProtect) {//隐藏
                if (fileInfo.isDirectory()) {
                    FileUtil.hideFilesUnderDir(this, fileInfo.getShowFile());
                } else {
                    FileUtil.hideSingleFile(this, fileInfo.getShowFile());
                }
                fileInfo.protectState = FileConstants.STATE_PROTECTED;

            } else {//显示
                if (fileInfo.isDirectory()) {
                    FileUtil.showFilesUnderDir(this, fileInfo.getShowFile());
                } else {
                    FileUtil.showSingleFile(this, fileInfo.getShowFile());
                }
                fileInfo.protectState = FileConstants.STATE_UNPROTECTED;
            }
        }

        DBOperator.getInstance(this).addProtectedFiles(userId, mFileList).subscribe(new DisposableObserver<Boolean>() {
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
        L.e("externalPermissionDenied");

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