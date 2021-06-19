package com.growingc.mediaoperator.imagebrowser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.growingc.mediaoperator.R;
import com.growingc.mediaoperator.beans.ImageInfo;
import com.growingc.mediaoperator.beans.SortInfo;
import com.growingc.mediaoperator.listener.OnMyItemClickListener;
import com.growingc.mediaoperator.utils.FileSortUtils;
import com.growingc.mediaoperator.utils.FileUtil;
import com.witon.mylibrary.base.BaseFullScreenActivity;
import com.witon.mylibrary.widget.HeaderBar;

import java.io.File;
import java.util.List;

import appframe.utils.L;
import appframe.utils.ToastCustom;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowImagesActivity extends BaseFullScreenActivity {
    View mContentView;
    ViewPager2 mImagePager;
    View mView2Hide;
    View mDelayHideButton;
    HeaderBar mHeaderBar;

    ImagePagerAdapter mImagePagerAdapter;
    ImageInfo mSelectedImage;
    SortInfo mSortInfo;
    List<File> mImageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_show_images);

        mContentView = findViewById(R.id.fullscreen_content);
        mImagePager = findViewById(R.id.image_pager);
        mView2Hide = findViewById(R.id.fullscreen_content_controls);
        mDelayHideButton = findViewById(R.id.btn_delay_hide);

        mHeaderBar = new HeaderBar(this);
//        mToolbar.setTitleMarginStart(100);
//        mToolbar.setTitle("1/2");
//        mToolbar.setSubtitle("wc");//toolbar设置title什么的必须在 setSupportActionBar之前，否则无效，之后需要用setTitle 来设置
//        setSupportActionBar(mToolbar);

//        getSupportActionBar().hide();

        mHeaderBar.setDefaultBackIcon();

        initFullScreenToggleAction(mDelayHideButton);

        mSelectedImage = (ImageInfo) getIntent().getSerializableExtra("imageInfo");
        mSortInfo = (SortInfo) getIntent().getSerializableExtra("sortInfo");
        if (mSortInfo == null) {
            mSortInfo = SortInfo.getDefault();
        }

        mImagePager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mHeaderBar.setTitle(position + 1 + "/" + mImageFiles.size());
                mHeaderBar.setSubTitle(mImagePagerAdapter.getItem(position).getName());
            }
        });
        mImagePagerAdapter = new ImagePagerAdapter(this, new OnMyItemClickListener<File>() {
            @Override
            public void onItemClick(int position, File data) {
                toggle();
            }
        });
        mImagePager.setAdapter(mImagePagerAdapter);

        refreshImages(true);
    }

    private void refreshImages(boolean needShowImagePos) {
        File imageDir = new File(mSelectedImage.imageFilePath).getParentFile();
        mImageFiles = FileUtil.listImageFile(imageDir, true);
        mImageFiles = FileSortUtils.sortFileListByName(mImageFiles, mSortInfo.isAscending());

        mImagePagerAdapter.setData(mImageFiles);
        if (needShowImagePos) {
            int selectedPos = findFileIndexInList(mSelectedImage.imageFilePath, mImageFiles);
            if (selectedPos >= 0) {
                mImagePager.setCurrentItem(selectedPos, false);
            }
        }
    }

    @Override
    protected View getContentView() {
        return mContentView;
    }

    @Override
    protected View getView2Hide() {
        return mView2Hide;
    }

    private int findFileIndexInList(String path, List<File> list) {
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i).getPath().contains(path))
                return i;
        }
        return -1;
    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_delete) {
            File fileToDelete = mImagePagerAdapter.getItem(mImagePager.getCurrentItem());
            showDeleteDialog(fileToDelete);
        }
    }

    private void showDeleteDialog(final File fileToDelete) {
        if (fileToDelete == null)
            return;
        new AlertDialog.Builder(ShowImagesActivity.this)
                .setMessage("确认删除文件：" + fileToDelete.getName())
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFiles(fileToDelete);
                    }
                }).create().show();
    }

    private void deleteFiles(File fileToDelete) {
        if (fileToDelete == null)
            return;
        if (FileUtil.deleteFile(fileToDelete.getPath())) {
            new ToastCustom(this, "删除了文件：" + fileToDelete.getName(), Toast.LENGTH_SHORT).show();
            refreshImages(false);
        }
    }
}
