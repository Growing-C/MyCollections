package com.cgy.mycollections.functions.mediamanager;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseFullScreenActivity;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.utils.FileUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowImagesActivity extends BaseFullScreenActivity {
    @BindView(R.id.fullscreen_content)
    View mContentView;
    @BindView(R.id.image_pager)
    ViewPager2 mImagePager;
    @BindView(R.id.fullscreen_content_controls)
    View mView2Hide;
    @BindView(R.id.dummy_button)
    View mDelayHideButton;

    ImageInfo mSelectedImage;
    List<File> mImageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        ButterKnife.bind(this);

//        initFullScreenToggleAction(mDelayHideButton);

        mSelectedImage = (ImageInfo) getIntent().getSerializableExtra("imageInfo");

        File imageDir = new File(mSelectedImage.imageFilePath).getParentFile();
        mImageFiles = FileUtil.listImageFile(imageDir);

        ImagePagerAdapter adapter = new ImagePagerAdapter(this);
        mImagePager.setAdapter(adapter);
        adapter.setData(mImageFiles);
        int selectedPos = findFileIndexInList(mSelectedImage.imageFilePath, mImageFiles);
//        if (selectedPos >= 0)
//            mImagePager.setCurrentItem(selectedPos);
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
}
