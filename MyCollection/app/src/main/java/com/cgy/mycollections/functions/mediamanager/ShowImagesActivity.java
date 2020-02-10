package com.cgy.mycollections.functions.mediamanager;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseFullScreenActivity;
import com.cgy.mycollections.functions.file.FileInfo;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.RecyclerAdapter;
import com.cgy.mycollections.listeners.OnMyItemClickListener;
import com.cgy.mycollections.utils.FileUtil;
import com.cgy.mycollections.utils.SystemUiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ImageInfo mSelectedImage;
    List<File> mImageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_show_images);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().hide();

        SystemUiUtils.setWindowTranslucentStatusAndNavigation(getWindow());

        SystemUiUtils.setStatusLight(mContentView);
        findViewById(R.id.test).setVisibility(View.GONE);

        SystemUiUtils.hideSystemUi(mContentView);

//        initFullScreenToggleAction(mDelayHideButton);//TODO:待搞清楚

        mSelectedImage = (ImageInfo) getIntent().getSerializableExtra("imageInfo");

        File imageDir = new File(mSelectedImage.imageFilePath).getParentFile();
        mImageFiles = FileUtil.listImageFile(imageDir, true);
        mImageFiles = sortByName(mImageFiles);

        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new OnMyItemClickListener<File>() {
            @Override
            public void onItemClick(int position, File data) {
//                toggle();
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

    public List<File> sortByName(List<File> fileList) {
        if (fileList != null) {
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
                }
            });
        }
        return fileList;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dummy_button:
                SystemUiUtils.showSystemUi(mContentView);
                break;
            case R.id.blur:
                SystemUiUtils.blurSystemUi(mContentView);
                break;
            case R.id.hide:
//                SystemUiUtils.hideSystemUi(mContentView);
                setNavVisibility(false);
                break;
            case R.id.show:
//                SystemUiUtils.showSystemUi(mContentView);
                setNavVisibility(true);
                break;
            default:
                break;
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
