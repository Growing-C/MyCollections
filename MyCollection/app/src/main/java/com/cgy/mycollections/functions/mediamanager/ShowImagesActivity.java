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
import android.view.WindowManager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseFullScreenActivity;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.RecyclerAdapter;
import com.cgy.mycollections.listeners.OnMyItemClickListener;
import com.cgy.mycollections.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
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
        setContentView(R.layout.activity_show_images);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        //        NOTICE:需要沉浸的view要搭配 android:fitsSystemWindows="true"来使用，不然  view的内容会占用statusBar的位置，导致重叠
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4-6.0的状态栏会是  白字，看不到字
//            以下功能也可以通过在appTheme中添加<item name="android:windowIsTranslucent">true</item> 来实现

            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //6.0以上的 亮色状态栏模式，可以把状态栏字体变成 黑字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initFullScreenToggleAction(mDelayHideButton);

        mSelectedImage = (ImageInfo) getIntent().getSerializableExtra("imageInfo");

        File imageDir = new File(mSelectedImage.imageFilePath).getParentFile();
        mImageFiles = FileUtil.listImageFile(imageDir, true);

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
}
