package com.cgy.mycollections.functions.jetpack.camerax;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.functions.jetpack.navigation.NavigationFragment;
import com.cgy.mycollections.functions.jetpack.room.RoomFragment;
import com.cgy.mycollections.functions.jetpack.workmanager.WorkManagerFragment;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.SectionsPagerAdapter;
import appframe.utils.L;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 自定义相机
 * CameraX - 满足相机应用需求。
 */
public class CameraXDemo extends BaseActivity {

    public static final String KEY_EVENT_ACTION = "key_event_action";
    public static final String KEY_EVENT_EXTRA = "key_event_extra";

    @BindView(R.id.fragment_container)
    FrameLayout container;

    /**
     * Combination of all flags required to put activity into immersive mode
     */
    int FLAGS_FULLSCREEN = View.SYSTEM_UI_FLAG_LOW_PROFILE |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    private long IMMERSIVE_FLAG_TIMEOUT = 500L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerax_demo);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
        // be trying to set app to immersive mode before it's ready and the flags do not stick
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                container.setSystemUiVisibility(FLAGS_FULLSCREEN);
            }
        }, IMMERSIVE_FLAG_TIMEOUT);
    }

    /**
     * Use external media if it is available, our app's file directory otherwise
     */
    public static File getOutputDirectory(Context context) {
        File outputDir = null;

        Context appContext = context.getApplicationContext();
        File[] mediaDirs = context.getExternalMediaDirs();
        if (mediaDirs != null && mediaDirs.length > 0) {
            outputDir = new File(mediaDirs[0], context.getString(R.string.app_name));
            outputDir.mkdirs();
            L.e("getOutputDirectory:" + outputDir);
        }
        return outputDir != null && outputDir.exists() ? outputDir : appContext.getFilesDir();
    }
}
