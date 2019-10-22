package com.cgy.mycollections.functions.mediamanager;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TabLayout;
//import androidx.core.view.ViewPager;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.AllImagesFragment;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.ImageFilesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.SectionsPagerAdapter;

public class MediaImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_images);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(getString(R.string.tab_text_all_images), AllImagesFragment.newInstance(0));
        sectionsPagerAdapter.addFragment(getString(R.string.tab_text_image_files), ImageFilesFragment.newInstance(1));

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}