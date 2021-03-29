package com.growingc.mediaoperator.imagebrowser;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.viewpager2.widget.ViewPager2;

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

    ImageInfo mSelectedImage;
    SortInfo mSortInfo;
    List<File> mImageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_show_images);

        mContentView = findViewById(R.id.fullscreen_content);
        mImagePager = findViewById(R.id.image_pager);
        mView2Hide = findViewById(R.id.fullscreen_content_controls);
        mDelayHideButton = findViewById(R.id.dummy_button);

        mHeaderBar = new HeaderBar(this);
//        mToolbar.setTitleMarginStart(100);
//        mToolbar.setTitle("1/2");
//        mToolbar.setSubtitle("wc");//toolbar设置title什么的必须在 setSupportActionBar之前，否则无效，之后需要用setTitle 来设置
//        setSupportActionBar(mToolbar);

//        getSupportActionBar().hide();

        mHeaderBar.setDefaultBackIcon();
        findViewById(R.id.test).setVisibility(View.GONE);

        initFullScreenToggleAction(mDelayHideButton);

        mSelectedImage = (ImageInfo) getIntent().getSerializableExtra("imageInfo");
        mSortInfo = (SortInfo) getIntent().getSerializableExtra("sortInfo");
        if (mSortInfo == null) {
            mSortInfo = SortInfo.getDefault();
        }

        File imageDir = new File(mSelectedImage.imageFilePath).getParentFile();
        mImageFiles = FileUtil.listImageFile(imageDir, true);
        mImageFiles = FileSortUtils.sortFileListByName(mImageFiles, mSortInfo.isAscending());

        mImagePager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mHeaderBar.setTitle(position + 1 + "/" + mImageFiles.size());
            }
        });
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new OnMyItemClickListener<File>() {
            @Override
            public void onItemClick(int position, File data) {
                toggle();
            }
        });
        mImagePager.setAdapter(adapter);
        adapter.setData(mImageFiles);
        int selectedPos = findFileIndexInList(mSelectedImage.imageFilePath, mImageFiles);
        if (selectedPos >= 0)
            mImagePager.setCurrentItem(selectedPos, false);

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
        if (id == R.id.dummy_button) {
        } else if (id == R.id.blur) {
        } else if (id == R.id.hide) {//                SystemUiUtils.hideSystemUi(mContentView);
            setNavVisibility(false);
        } else if (id == R.id.show) {//                SystemUiUtils.showSystemUi(mContentView);
            setNavVisibility(true);
        }
    }

    Handler handler = new Handler();
    Runnable mNavHider = new Runnable() {
        @Override
        public void run() {
            setNavVisibility(false);
        }
    };

    void setNavVisibility(boolean visible) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        if (!visible) {
            newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        // If we are now visible, schedule a timer for us to go invisible.
        if (visible) {
            Handler h = handler;
            if (h != null) {
                h.removeCallbacks(mNavHider);
                // If the menus are open or play is paused, we will not auto-hide.
                h.postDelayed(mNavHider, 3000);
            }
        }
        // Set the new desired visibility.
        mContentView.setSystemUiVisibility(newVis);
//        mTitleView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        mPlayButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        mSeekView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}
