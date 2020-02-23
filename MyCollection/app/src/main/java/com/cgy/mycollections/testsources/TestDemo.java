package com.cgy.mycollections.testsources;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.SystemUiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDemo extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);//butterKnife和dataBinding并不冲突

        setSupportActionBar(toolbar);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fullscreen:
                SystemUiUtils.showSystemUi(mContentView);
                break;
            case R.id.quit_full:
                SystemUiUtils.blurSystemUi(mContentView);
                break;
            default:
                break;
        }
    }

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
        // Set the new desired visibility.
        mContentView.setSystemUiVisibility(newVis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
